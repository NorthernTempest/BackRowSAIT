package service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import exception.ConfigException;

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
	private final static String SIN = "sin";
	private final static String FNAME = "fName";
	private final static String LNAME = "lName";
	private final static String FULLNAME = "fullName";
	private final static String SIGNATURE = "signature";
	private final static String DATE = "date";

	
	/**
	 * Does some sort of PDF thing.
	 * @param sin sin
	 * @param taxYear taxYear
	 * @param fName fName
	 * @param lName lName
	 * @throws ConfigException 
	 * @throws IOException 
	 */
	public static PDDocument createAuthorizationRequest(String sin, String fName, String lName) throws ConfigException, IOException {
		init();
		PDDocument authReqPDF;
		File authReqFile = new File(authReqPDFFilePath);
		
		authReqPDF = PDDocument.load(authReqFile);
		
		List<PDField> list = authReqPDF.getDocumentCatalog().getAcroForm().getFields();
		int count = 0;
		for(PDField field : list) {	
			if(field.getPartialName().equals(SIN)) {
				field.setValue(sin);
			}
			if(field.getPartialName().equals(FNAME)) {
				field.setValue(fName);
			}
			if(field.getPartialName().equals(LNAME)) {
				field.setValue(lName);
			}
			if(field.getPartialName().equals(FULLNAME)) {
				field.setValue(fName + " " + lName);
			}
			count++;
		}
		
		return authReqPDF;
	}

	private static void init() throws ConfigException {
		if (!init) {
			authReqPDFFilePath = ConfigService.fetchFromConfig("AUTH_REQ_FILEPATH:");
			init = true;
		}
	}
	
	public static void signDoc() {
		PDImageXObject signature;
	}
}
