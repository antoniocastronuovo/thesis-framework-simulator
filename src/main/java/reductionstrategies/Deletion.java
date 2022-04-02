package reductionstrategies;

public class Deletion extends Reduction{

	public Deletion() {
		super(false);
		// TODO Auto-generated constructor stub
		this.setDiffusionReduction(0.0);
		this.setLatencyReduction(0.0);
		this.setCompletenessReduction(0.0);
		this.name = "Deletion";
	}
	
}
