package model;

public class Value {
	
	private Double completeness;
	private Double latency;
	private Double diffusion;
	private Double interest;
	
	public Value() {
		this.completeness = 0.3;
		this.latency = 0.18;
		this.diffusion = 0.12;
		this.interest = 0.4;
	}
	
	public static Double valueInterestAt(Double totalValue, Integer disuseThreshold, Double agingDecayFactor, Integer day){
		if(day<disuseThreshold) {
			
			return ((agingDecayFactor-1)/disuseThreshold *day +1);
		}
		else {
			return agingDecayFactor*disuseThreshold/day ;
		}
	}

	public Double getCompleteness() {
		return completeness;
	}
	
	public void setCompleteness(Double completeness) {
		this.completeness = completeness;
	}
	
	public Double getLatency() {
		return latency;
	}
	
	public void setLatency(Double latency) {
		this.latency = latency;
	}
	
	public Double getDiffusion() {
		return diffusion;
	}
	
	public void setDiffusion(Double diffusion) {
		this.diffusion = diffusion;
	}
	
	public Double getInterest() {
		return interest;
	}
	
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	
	public Double getTotalValue() {
		return this.completeness+this.diffusion+this.interest+this.latency;
	}
}
