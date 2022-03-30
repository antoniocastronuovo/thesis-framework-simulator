package reductionstrategies;

import model.Metadata;


public class Deduplication extends Reduction{
 
	
	public Deduplication(Metadata dataset) {
		super(true);
		
		if (!dataset.getCopiesMap().isEmpty()) {
			this.setDiffusionReduction(Double.valueOf(dataset.getCopiesMap().size()/(dataset.getCopiesMap().size()-1)));
		}
		else {
			this.setDiffusionReduction(1.0);
			System.out.println("The considered dataset has no copies");
		}
		this.setCompletenessReduction(1.0);
		this.setLatencyReduction(1.0);
		this.name = "Deduplication";
	}
	
}
