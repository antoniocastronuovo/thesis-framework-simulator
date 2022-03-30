package reductionstrategies;

public class SamplingMultimediaCompression extends Reduction{

	public SamplingMultimediaCompression(double reductionRatio) {
		super(false);
		this.setCompletenessReduction(reductionRatio);
		this.setDiffusionReduction(1.0);
		this.setLatencyReduction(1.0);
		this.name = "Sampling Multimedia Compression";
	}

}
