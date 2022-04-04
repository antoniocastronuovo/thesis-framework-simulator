package utilities;

import java.util.List;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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


/**
 * @author Antonio Castronuovo
 *
 */
public class JsonImporter {

	/**
	 * Stub classic constructor
	 */
	public JsonImporter() {
		super();
	}

	/**
	 * @param path is the path of the JSON file containing the data catalog
	 * @param network is the object containing the info regarding the architecture of the considered system (nodes)
	 * @return a list of Metadata regarding all the data sets in the data catalog
	 * @throws FileNotFoundException
	 */
	public List <Metadata> parseDataCatalog(String path, Network network) throws FileNotFoundException{

		//Initialize the Gson library
		Gson gson = new Gson();

		//Instantiate a new file reader from the JSON path
		Reader reader = new FileReader(path);
		JsonArray jsonArray = new JsonArray();
		ArrayList<Metadata> datasets = new ArrayList<>();
		
		//Get a new JSON array from the JSON file containing the data catalog
		jsonArray = gson.fromJson(reader, JsonArray.class);
		
		//for each element of the JSON array (data sets)
		jsonArray.forEach((e) -> {
			
			//create a new metadata
			Metadata dataset = new Metadata();
			
			//Set all the major metadata fields of the considered data set getting them from the JSON file
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
			dataset.setId(e.getAsJsonObject().get("Id").getAsString());
			
			//Set the admissed reductions for the considered data set getting them from the JSON file,
			//createReductionList performs this operation in details
			dataset.setAdmissedReductions(createReductionList(e.getAsJsonObject().get("Admissed Reductions").getAsJsonObject(),dataset));

			//Add the constructed data set to the list 
			datasets.add(dataset);
		});
		return datasets;
	}

	/**
	 * @param reduction is the JSON object containing the list of the admissed reductions
	 * @param dataset is the data set to which the list of admissed reduction refers to
	 * @return the list of admissed reductions
	 */
	private LinkedHashMap<Reduction,Value> createReductionList(JsonObject reduction, Metadata dataset) {

		//Introduce a new LinkedHashMap containing couples (Reduction, Value) for the considered reductions
		LinkedHashMap<Reduction,Value> reductions = new LinkedHashMap<>();
		
		//Actual day of reduction
		ZonedDateTime today = ZonedDateTime.of(2022, 03, 20, 0, 0, 1, 0, ZoneId.systemDefault());
		
		// Calculate the number of days between the day of reduction and the data set last usage (to simulate the data value reduction)
		Integer day = (int) Duration.between(dataset.getLastUsage(),today).toDays();
		
		//Add to the admissible reductions all the possible Aggregations
		if (reduction.has("Aggregation")) {
			reduction.get("Aggregation").getAsJsonObject().entrySet().forEach((a) -> {
				Aggregation aggregation =new Aggregation(a.getValue().getAsDouble());
				reductions.put(aggregation,dataset.simulateReduction(aggregation, day)) ;
			});
		}
		
		//Add to the admissible reductions all the possible Dimension Removing
		if (reduction.has("Dimension Removing")) {
			DimensionRemoving dimensionRemoving = new DimensionRemoving(reduction.get("Dimension Removing").getAsJsonArray().get(0).getAsInt(),reduction.get("Dimension Removing").getAsJsonArray().get(1).getAsInt());
			reductions.put(dimensionRemoving,dataset.simulateReduction(dimensionRemoving, day));
		}
		
		//Add to the admissible reductions all the possible Multimedia Compressions
		if (reduction.has("Multimedia Compression")) {
			SamplingMultimediaCompression samplingMultimediaCompression = new SamplingMultimediaCompression(reduction.get("Multimedia Compression").getAsDouble()) ;
			reductions.put(samplingMultimediaCompression,dataset.simulateReduction(samplingMultimediaCompression, day));
		}
		
		//Add to the admissible reductions all the possible Lossless Compressions
		if (reduction.has("Lossless Compression")) {
			reduction.get("Lossless Compression").getAsJsonObject().entrySet().forEach((a) -> {
				LosslessCompression losslessCompression =new LosslessCompression(a.getValue().getAsDouble());
				reductions.put(losslessCompression,dataset.simulateReduction(losslessCompression, day)) ;
			});

		}

		return reductions;
	}
	
	/**
	 * @param path is the local path of JSON file containing the system nodes 
	 * @return a list of the actual nodes (not filled with the contained data sets)
	 * @throws FileNotFoundException
	 */
	public List<Node> parseNetwork(String path) throws FileNotFoundException{

		Gson gson = new Gson();
		Reader reader = new FileReader(path);
		JsonArray jsonArray = new JsonArray();
		ArrayList<Node> nodes = new ArrayList<>();
		jsonArray = gson.fromJson(reader, JsonArray.class);
		
		//For each node description in the JSON file
		jsonArray.forEach((e) -> {
			//Fill the internal model describing the architecture of the system getting nodes info from a JSON file
			Node node = new Node(e.getAsJsonObject().get("Id").getAsString());
			node.setLastClean(ZonedDateTime.parse(e.getAsJsonObject().get("Last Clean").getAsString()));
			node.setCleaningFrequency(e.getAsJsonObject().get("Cleaning Frequency").getAsInt());
			node.setTotalCapacity(e.getAsJsonObject().get("Total Capacity").getAsInt());
			node.setFilledCapacity(e.getAsJsonObject().get("Filled Capacity").getAsInt());
			
			// add the node to the system list of nodes
			nodes.add(node);
		} );
		return nodes;
	}
}
