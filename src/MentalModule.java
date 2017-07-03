/**
 * The basic Mental Image module to be used. Module is responsible for the
 * creation and manipulation of mental Images. The mental module will also be
 * restricted by different memory capabilities. Primarily model will only create
 * replicas of perceived image
 *
 * TODO: - allow for the multiple discrete level to be used in the perceived
 * image - apply the image manipulations outlined in maithilee's block design
 * proposal
 *
 * @author Mohamed El Banani
 * @date 25 July 2015
 * @version 1.0
 * @since 1.0 
 */

public class MentalModule {

	MentalImage mentalImage;
	int memorySize;

	/**
	 * Constructs a Mental Module with a blank image
	 *
	 * @param memorySize
	 *            the size of memory in blocks
	 */
	public MentalModule(int memorySize) {
		this.memorySize = memorySize;
		mentalImage = null;
	}

	/**
	 * Stores the perceived image as a mental image Dependent on PerceivedImage
	 * representation
	 *
	 * @param perception
	 *            the current perceived image of the table
	 */
	public void storeImage(PerceivedImage perception) {
		mentalImage = new MentalImage(perception, memorySize);
	}

	// TODO no manipulation operators are currently applied
}