package utilities;

import java.util.List;

import org.apache.commons.compress.harmony.unpack200.bytecode.forms.MultiANewArrayForm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import model.Metadata;
import model.Network;
import model.Node;
import model.Value;
import reductionstrategies.Aggregation;
import reductionstrategies.DimensionRemoving;
import reductionstrategies.LosslessCompression;
import reductionstrategies.Reduction;
import reductionstrategies.SamplingMultimediaCompression;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class JsonImporter {

	public JsonImporter() {
		super();
	}

	public List <Metadata> parseDataCatalog(String path, Network network) throws FileNotFoundException{

		Gson gson = new Gson();
		Reader reader = new FileReader(path);
		JsonArray jsonArray = new JsonArray();
		ArrayList<Metadata> datasets = new ArrayList<>();
		jsonArray = gson.fromJson(reader, JsonArray.class);
		jsonArray.forEach((e) -> {
			Metadata dataset = new Metadata();
			dataset.setRetentionLimit(e.getAsJsonObject().get("Retention Limit").getAsInt());
			dataset.setDefective(e.getAsJsonObject().get("Defective").getAsBoolean());
			dataset.setDisposable(e.getAsJsonObject().get("Disposable").getAsBoolean());
			dataset.setOffLimits(e.getAsJsonObject().get("Off-Limits").getAsBoolean());
			dataset.setCopy(e.getAsJsonObject().get("Copy").getAsBoolean());
			dataset.setValue(new Value());
			dataset.setNode(network.getNodeById(e.getAsJsonObject().get("Node").getAsString()));
			dataset.setDisuseThreshold(e.getAsJsonObject().get("Disuse Threshold").getAsInt());
			dataset.setAgingDecayFactor(e.getAsJsonObject().get("Aging Decay Factor").getAsDouble());
			dataset.setLastUsage(ZonedDateTime.parse(e.getAsJsonObject().get("Last Usage").getAsString()));
			network.getNodeById(dataset.getNode().getId()).addDataset(dataset);
			dataset.setAdmissedReductions(createReductionList(e.getAsJsonObject().get("Admissed Reductions").getAsJsonObject(),dataset));
			dataset.setId(e.getAsJsonObject().get("Id").getAsString());
			datasets.add(dataset);
		});
		return datasets;
	}

	private LinkedHashMap<Reduction,Value> createReductionList(JsonObject reduction, Metadata dataset) {

		LinkedHashMap<Reduction,Value> reductions = new LinkedHashMap<Reduction,Value>();
		
		ZonedDateTime today = ZonedDateTime.of(2022, 03, 20, 0, 0, 1, 0, ZoneId.systemDefault());
		Integer day = (int) Duration.between(dataset.getLastUsage(),today).toDays();
		
		if (reduction.has("Aggregation")) {
			reduction.get("Aggregation").getAsJsonObject().entrySet().forEach((a) -> {
				Aggregation aggregation =new Aggregation(a.getValue().getAsDouble());
				reductions.put(aggregation,dataset.simulateReduction(aggregation, day)) ;
			});
		}

		if (reduction.has("Dimension Removing")) {
			DimensionRemoving dimensionRemoving = new DimensionRemoving(reduction.get("Dimension Removing").getAsJsonArray().get(0).getAsInt(),reduction.get("Dimension Removing").getAsJsonArray().get(1).getAsInt());
			reductions.put(dimensionRemoving,dataset.simulateReduction(dimensionRemoving, day));
		}
		
		if (reduction.has("Multimedia Compression")) {
			SamplingMultimediaCompression samplingMultimediaCompression = new SamplingMultimediaCompression(reduction.get("Multimedia Compression").getAsDouble()) ;
			reductions.put(samplingMultimediaCompression,dataset.simulateReduction(samplingMultimediaCompression, day));
		}

		if (reduction.has("Lossless Compression")) {

			reduction.get("Lossless Compression").getAsJsonObject().entrySet().forEach((a) -> {
				LosslessCompression losslessCompression =new LosslessCompression(a.getValue().getAsDouble());
				reductions.put(losslessCompression,dataset.simulateReduction(losslessCompression, day)) ;
			});

		}

		return reductions;
	}
	
	public List<Node> parseNetwork(String path) throws FileNotFoundException{

		Gson gson = new Gson();
		Reader reader = new FileReader(path);
		JsonArray jsonArray = new JsonArray();
		ArrayList<Node> nodes = new ArrayList<>();
		jsonArray = gson.fromJson(reader, JsonArray.class);
		jsonArray.forEach((e) -> {
			Node node = new Node(e.getAsJsonObject().get("Id").getAsString());
			node.setLastClean(ZonedDateTime.parse(e.getAsJsonObject().get("Last Clean").getAsString()));
			node.setCleaningFrequency(e.getAsJsonObject().get("Cleaning Frequency").getAsInt());
			node.setTotalCapacity(e.getAsJsonObject().get("Total Capacity").getAsInt());
			node.setFilledCapacity(e.getAsJsonObject().get("Filled Capacity").getAsInt());
			nodes.add(node);
		} );
		return nodes;
	}
}
