package model;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import reductionstrategies.Reduction;

/**
 * @author Antonio Castronuovo
 *
 */
public class Metadata {
	private String id;
	private String size;
	private ZonedDateTime insertionDate;
	private ZonedDateTime lastUsage;
	private Integer retentionLimit;
	private Map<String, Integer> copiesMap;
	private Node node;
	private Boolean copy;
	private List<Metadata> substitutable;
	private Boolean defective = false;
	private Boolean disposable = false;
	private Boolean offLimits = false;
	private LinkedHashMap<ZonedDateTime,Reduction> reductionList;
	
	private Value value;
	private LinkedHashMap <Reduction,Value> admissedReductions;
	private Integer disuseThreshold;
	private Double agingDecayFactor;
	private Boolean disused;
	private Boolean aged;


	/**
	 * Standard constructor
	 */
	public Metadata() {
		super();
		this.substitutable = new ArrayList<>();
		this.disused =false;
		this.aged = false;
		this.admissedReductions = new LinkedHashMap <Reduction,Value>();
		this.reductionList = new LinkedHashMap<>();
	}

	
	/**
	 * @param reduction is the reduction to be simulated
	 * @param day is the day instant to which simulate the reduction
	 * @return the value corresponding to the reduction application
	 */
	public Value simulateReduction(Reduction reduction, Integer day) {

		//Save temporarily the actual value dimensions quantities
		Double oldCompleteness = this.getValue().getCompleteness();
		Double oldDiffusion = this.getValue().getDiffusion();
		Double oldLatency = this.getValue().getLatency();
		Double oldInterest = this.getValue().getInterest();
		
		Value simulatedValue = new Value();
		//Multiply the old value quantities with the factors of the new reduction
		simulatedValue.setCompleteness(oldCompleteness * reduction.getCompletenessReduction());
		simulatedValue.setDiffusion( oldDiffusion * reduction.getDiffusionReduction());
		simulatedValue.setLatency( oldLatency * reduction.getLatencyReduction());
		simulatedValue.setInterest(oldInterest * Value.valueInterestAt(this.value.getTotalValue(),disuseThreshold,agingDecayFactor,day));
		
		return simulatedValue;
	}


	public Double getAgingDecayFactor() {
		return agingDecayFactor;
	}


	public void setAgingDecayFactor(Double agingDecayFactor) {
		this.agingDecayFactor = agingDecayFactor;
	}

	public Integer getDisuseThreshold() {
		return disuseThreshold;
	}

	public void setDisuseThreshold(Integer disuseThreshold) {
		this.disuseThreshold = disuseThreshold;
	}

	public  String getId() {
		return id;
	}


	public  void setId(String id) {
		this.id = id;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public ZonedDateTime getInsertionDate() {
		return insertionDate;
	}


	public void setInsertionDate(ZonedDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}


	public ZonedDateTime getLastUsage() {
		return lastUsage;
	}


	public void setLastUsage(ZonedDateTime lastUsage) {
		this.lastUsage = lastUsage;
	}


	public Integer getRetentionLimit() {
		return retentionLimit;
	}


	public void setRetentionLimit(Integer retentionLimit) {
		this.retentionLimit = retentionLimit;
	}


	public Map<String, Integer> getCopiesMap() {
		return copiesMap;
	}


	public void setCopiesMap(Map<String, Integer> copiesMap) {
		this.copiesMap = copiesMap;
	}


	public Node getNode() {
		return node;
	}


	public void setNode(Node node) {
		this.node = node;
	}


	public Boolean getCopy() {
		return copy;
	}


	public void setCopy(Boolean copy) {
		this.copy = copy;
	}


	public List<Metadata> getSubstitutable() {
		return substitutable;
	}


	public void setSubstitutable(List<Metadata> substitutable) {
		this.substitutable = substitutable;
	}


	public Boolean getDefective() {
		return defective;
	}


	public void setDefective(Boolean defective) {
		this.defective = defective;
	}


	public Boolean getDisposable() {
		return disposable;
	}


	public void setDisposable(Boolean disposable) {
		this.disposable = disposable;
	}


	public Boolean getOffLimits() {
		return offLimits;
	}


	public void setOffLimits(Boolean offLimits) {
		this.offLimits = offLimits;
	}


	public LinkedHashMap<ZonedDateTime,Reduction> getReductionList() {
		return reductionList;
	}


	public void setReductionList(LinkedHashMap<ZonedDateTime,Reduction> reductionList) {
		this.reductionList = reductionList;
	}


	public Value getValue() {
		return value;
	}


	public void setValue(Value value) {
		this.value = value;
	}


	public LinkedHashMap <Reduction,Value> getAdmissedReductions() {
		return admissedReductions;
	}


	public void setAdmissedReductions(LinkedHashMap <Reduction,Value> admissedReductions) {
		this.admissedReductions = admissedReductions;
	}


	public Boolean getDisused() {
		return disused;
	}


	public void setDisused(Boolean disused) {
		this.disused = disused;
	}


	public Boolean getAged() {
		return aged;
	}


	public void setAged(Boolean aged) {
		this.aged = aged;
	}


}
