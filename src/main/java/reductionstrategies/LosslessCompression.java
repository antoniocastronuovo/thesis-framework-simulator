package reductionstrategies;

/**
 * @author Antonio Castronuovo
 *
 */
public class LosslessCompression extends Reduction{

	public LosslessCompression(double newAccessTime) {
		super(true);

		this.setCompletenessReduction(1.0);
		this.setDiffusionReduction(1.0);
		this.setLatencyReduction(1/(newAccessTime));
		this.name = "Lossless Compression";
	}

}
