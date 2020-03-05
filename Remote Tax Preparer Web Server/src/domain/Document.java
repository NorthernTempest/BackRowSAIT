package domain;

/**
 * 
 * Class Description: Class that encrypts or decrypts documents attached to a
 * parcel object.
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public final class Document {

	private transient String filePath;

	private String fileName;
	
	private long fileSize;

	public Document(String filePath, String fileName, long fileSize) {
		this.filePath = filePath;
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
