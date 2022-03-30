package reductionstrategies;


public class Aggregation extends Reduction {

	public Aggregation(Double aggregationFactor) {
		super(false);
		this.setCompletenessReduction(aggregationFactor);
		this.setDiffusionReduction(1.0);
		this.setLatencyReduction(1.0);
		this.name ="Aggregation";
	}

}
