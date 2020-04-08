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
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Returns the filename.
	 * @return filename
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Returns the file size.
	 * @return file size
	 */
	public long getFileSize() {
		return fileSize;
	}
}
