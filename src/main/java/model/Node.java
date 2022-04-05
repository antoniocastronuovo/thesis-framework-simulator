package model;


import java.util.ArrayList;
import java.util.List;
import java.time.ZonedDateTime;


/**
 * @author Antonio Castronuovo
 *
 */
public class Node {
	private String id;
	private List <Metadata> datasets;
	private ZonedDateTime lastClean;
	private Boolean thinProvisionable;
	private Integer cleaningFrequency;
	private Integer totalCapacity;
	private Integer filledCapacity;
	
	
	public Node(String id) {
		super();
		this.id = id;
		this.datasets = new ArrayList<>();
	}
	
	
	public String getId() {
		return id;
	}

	public List<Metadata> getDatasets() {
		return datasets;
	}
	
	public void setDatasets(List<Metadata> datasets) {
		this.datasets = datasets;
	}
	
	public void addDataset(Metadata metadata) {
		this.datasets.add(metadata);
	}
	
	
	public ZonedDateTime getLastClean() {
		return lastClean;
	}
	public void setLastClean(ZonedDateTime lastClean) {
		this.lastClean = lastClean;
	}
	public Boolean getThinProvisionable() {
		return thinProvisionable;
	}
	public void setThinProvisionable(Boolean thinProvisionable) {
		this.thinProvisionable = thinProvisionable;
	}
	public Integer getCleaningFrequency() {
		return cleaningFrequency;
	}
	public void setCleaningFrequency(Integer cleaningFrequency) {
		this.cleaningFrequency = cleaningFrequency;
	}

	public Integer getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(Integer totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public Integer getFilledCapacity() {
		return filledCapacity;
	}

	public void setFilledCapacity(Integer filledCapacity) {
		this.filledCapacity = filledCapacity;
	}
	
}
