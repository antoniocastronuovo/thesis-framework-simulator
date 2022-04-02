package reductionstrategies;

public abstract class Reduction {
	private Boolean isReversible;
	private Double completenessReduction;
	private Double diffusionReduction;
	private Double latencyReduction;
	protected String name;

	public Reduction(Boolean isReversible) {
		super();
		this.isReversible = isReversible;
	}

	public Boolean getIsReversible() {
		return isReversible;
	}

	public String getName() {
		return name;
	}

	public void setIsReversible(Boolean isReversible) {
		this.isReversible = isReversible;
	}

	public Double getCompletenessReduction() {
		return completenessReduction;
	}

	public void setCompletenessReduction(Double completenessReduction) {
		this.completenessReduction = completenessReduction;
	}

	public Double getDiffusionReduction() {
		return diffusionReduction;
	}

	public void setDiffusionReduction(Double diffusionReduction) {
		this.diffusionReduction = diffusionReduction;
	}

	public Double getLatencyReduction() {
		return latencyReduction;
	}

	public void setLatencyReduction(Double latencyReduction) {
		this.latencyReduction = latencyReduction;
	}

}
