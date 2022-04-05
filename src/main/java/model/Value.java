package model;

/**
 * @author Antonio Castronuovo
 *
 */
public class Value {
	
	private Double completeness;
	private Double latency;
	private Double diffusion;
	private Double interest;
	
	public Value() {
		//Standard values used for the value dimensions (they can also change) 
		this.completeness = 0.3;
		this.latency = 0.18;
		this.diffusion = 0.12;
		this.interest = 0.4;
	}
	
	
	/**
	 * @param totalValue is the total value of the considered data set
	 * @param disuseThreshold is the disuse threshold of the considered data set
	 * @param agingDecayFactor is the value of the aging decay factor
	 * @param day is the day of evaluation
	 * @return the value of the interest at the inserted day 
	 */
	public static Double valueInterestAt(Double totalValue, Integer disuseThreshold, Double agingDecayFactor, Integer day){
		//if we are before the disuse threshold
		if(day<disuseThreshold) {
			//return the result in the aging phase
			return ((agingDecayFactor-1)/disuseThreshold*day +1);
		}//otherwise return the result in the disuse phase
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
