package reductionstrategies;

public class GlacierInsertion extends Reduction{

	public GlacierInsertion(Double newAccessTime) {
		super(true);
		this.setCompletenessReduction(1.0);
		this.setDiffusionReduction(1.0);
		this.setLatencyReduction(1/newAccessTime);
		this.name = "Glacier";
	}

}
