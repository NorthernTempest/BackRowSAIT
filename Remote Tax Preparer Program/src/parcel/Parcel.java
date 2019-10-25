package parcel;

import java.util.ArrayList;
import java.util.Date;

public class Parcel {
	
	private ArrayList<Document> documents;
	private String message;
	private String sender;
	private String receiver;
	private boolean signatureRequired;
	private Date date;
	private Date expirationDate;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public boolean requiresSignature() {
		return signatureRequired;
	}
	
	public void setSignatureRequired(boolean signatureRequired) {
		this.signatureRequired = signatureRequired;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void addDocument(Document d) {
		
	}
	
	public void removeDocument(int i) {
		
	}
}
