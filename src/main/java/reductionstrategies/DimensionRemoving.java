package reductionstrategies;

public class DimensionRemoving extends Reduction {
	
	public DimensionRemoving(Integer removedDimensions, Integer totDimensions) {
		super(false);
		this.setCompletenessReduction(1-Double.valueOf(removedDimensions)/Double.valueOf(totDimensions));
		this.setDiffusionReduction(1.0);
		this.setLatencyReduction(1.0);
		this.name = "Dimension Removing";
	}

}
