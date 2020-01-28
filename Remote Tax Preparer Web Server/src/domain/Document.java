package domain;

/**
 * 
 * Class Description: Class that encrypts or decrypts documents attached to a parcel object.
 *
 * @author Tristen Kreutz
 *
 */
public abstract class Document {

	private String filePath;
	
	private boolean isSigned;
	
	private boolean requiresSignature;
	
	private int parcelID;

	/**
	 * Returns the filePath.
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Returns the isSigned.
	 * @return the isSigned
	 */
	public boolean isSigned() {
		return isSigned;
	}

	/**
	 * Returns the requiresSignature.
	 * @return the requiresSignature
	 */
	public boolean isRequiresSignature() {
		return requiresSignature;
	}

	/**
	 * Returns the parcelID.
	 * @return the parcelID
	 */
	public int getParcelID() {
		return parcelID;
	}

	/**
	 * Sets the value of filePath.
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Sets the value of isSigned.
	 * @param isSigned the isSigned to set
	 */
	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}

	/**
	 * Sets the value of requiresSignature.
	 * @param requiresSignature the requiresSignature to set
	 */
	public void setRequiresSignature(boolean requiresSignature) {
		this.requiresSignature = requiresSignature;
	}

	/**
	 * Sets the value of parcelID.
	 * @param parcelID the parcelID to set
	 */
	public void setParcelID(int parcelID) {
		this.parcelID = parcelID;
	}
}

