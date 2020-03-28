package service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Part;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import domain.Document;
import exception.ConfigException;
import util.cesar.Debugger;

/**
 * Class Description:
 *
 * @author Tristen Kreutz, Cesar Guzman
 *
 */
public final class PDFService {

	private static boolean init;
	
	private static String authReqPDFFilePath;
	
	//fillable pdf field names
	private final static String FIELD_SIN = "sin";
	private final static String FIELD_FNAME = "fName";
	private final static String FIELD_LNAME = "lName";
	private final static String FIELD_FULLNAME = "fullName";
	private final static String FIELD_SIGNATURE = "signature";
	private final static String FIELD_DATE = "date";

	

	/**
	 * @param sin
	 * @param fName
	 * @param lName
	 * @return
	 * @throws ConfigException
	 * @throws IOException
	 */
	public static Document createAuthorizationRequest(String sin, String fName, String lName) throws ConfigException, IOException {
		init();
		//TODO
		PDDocument authReqPDF;
		File authReqFile = new File(authReqPDFFilePath);
		
		authReqPDF = PDDocument.load(authReqFile);
		
		List<PDField> list = authReqPDF.getDocumentCatalog().getAcroForm().getFields();
		int count = 0;
		for(PDField field : list) {	
			if(field.getPartialName().equals(FIELD_SIN)) {
				field.setValue(sin);
			}
			if(field.getPartialName().equals(FIELD_FNAME)) {
				field.setValue(fName);
			}
			if(field.getPartialName().equals(FIELD_LNAME)) {
				field.setValue(lName);
			}
			if(field.getPartialName().equals(FIELD_FULLNAME)) {
				field.setValue(fName + " " + lName);
			}
			count++;
		}
		
		PDStream stream = new PDStream(authReqPDF);
		InputStream inStream = stream.createInputStream();
		
		Document document = null;
		try {
			document = EncryptionService.encryptDocument(inStream, fName + "_" + lName + "_AuthRequest.pdf");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		} catch (ConfigException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return document;
	}

	public static void signAuthForm(Collection<Part> parts) throws ConfigException {
		//TODO
		init();
		String fileName;
		Part signature;
		Part authReqPDF;
		for (Part part : parts) {
			fileName = part.getSubmittedFileName();
			Debugger.log(fileName);
			if (fileName != null && !fileName.equals("null")) {
				if(fileName.equals("signature.png")) {
					signature = part;
				}
				if(fileName.equals("AuthReq.pdf")) {
					authReqPDF = part;
				}
			}
		}
	}
	
	private static void init() throws ConfigException {
		if (!init) {
			authReqPDFFilePath = ConfigService.fetchFromConfig("AUTH_REQ_FILEPATH:");
			init = true;
		}
	}
}