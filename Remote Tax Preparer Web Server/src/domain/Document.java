package domain;

/**
 * 
 * Class Description: Class that encrypts or decrypts documents attached to a
 * parcel object.
 *
 * @author Tristen Kreutz
 *
 */
public final class Document {

	private transient String filePath;

	private boolean isSigned;

	private boolean requiresSignature;

	private String fileName;
	
	private long fileSize;

	public Document(String filePath, boolean isSigned, boolean requiresSignature, String fileName, long fileSize) {
		this.filePath = filePath;
		this.isSigned = isSigned;
		this.requiresSignature = requiresSignature;
		this.fileName = fileName;
		this.fileSize = fileSize;
	}

	/**
	 * Returns the filePath.
	 * 
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Returns the isSigned.
	 * 
	 * @return the isSigned
	 */
	public boolean isSigned() {
		return isSigned;
	}

	/**
	 * Returns the requiresSignature.
	 * 
	 * @return the requiresSignature
	 */
	public boolean isRequiresSignature() {
		return requiresSignature;
	}

	/**
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	
	public long getFileSize() {
		return fileSize;
	}
}
