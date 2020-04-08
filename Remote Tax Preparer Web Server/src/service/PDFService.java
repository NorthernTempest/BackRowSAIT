package service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import domain.Document;
import exception.ConfigException;
import manager.LogEntryManager;
import manager.ParcelManager;

/**
 * Handles the creation and signing of forms.
 *
 * @author Tristen Kreutz, Cesar Guzman
 */
public final class PDFService {
	
	/**
	 * Whether the class's static variable has been initialized.
	 */
	private static boolean init;
	/**
	 * The file path to an authorization request pdf.
	 */
	private static String authReqPDFFilePath;

	//fillable pdf field names
	/**
	 * The field that contains the user's sin number.
	 */
	private final static String FIELD_SIN = "sin";
	/**
	 * The field that contains the user's first name.
	 */
	private final static String FIELD_FNAME = "fName";
	/**
	 * The field that contains the user's last name.
	 */
	private final static String FIELD_LNAME = "lName";
	/**
	 * The field that contains the user's full name.
	 */
	private final static String FIELD_FULLNAME = "fullName";

	//x & y positions for signtures
	/**
	 * The x position for the signature required for the T183 form.
	 */
	private final static int T183_X_POS = 35;
	/**
	 * The y position for the signature required for the T183 form.
	 */
	private final static int T183_Y_POS = 37;
	/**
	 * The x position for the signature required for the authorization request form.
	 */
	private final static int AUTH_X_POS = 50;
	/**
	 * The y position for the signature required for the authorization request form.
	 */
	private final static int AUTH_Y_POS = 155;

	/**
	 * Creates an authorization request form filled in with the user's name and sin number.
	 * 
	 * @param sin String The social insurance number of the user.
	 * @param fName String The first name of the user.
	 * @param lName String The last name of the user.
	 * @return Document The record of the encrypted authorization request pdf document that was created.
	 * @throws ConfigException If the config file was not found.
	 * @throws IOException If the authorization request file cannot be found.
	 */
	public static Document createAuthorizationRequest(String sin, String fName, String lName)
			throws ConfigException, IOException {
		init();
		//TODO
		PDDocument authReqPDF;
		File authReqFile = new File(authReqPDFFilePath);

		authReqPDF = PDDocument.load(authReqFile);

		List<PDField> list = authReqPDF.getDocumentCatalog().getAcroForm().getFields();
		for (PDField field : list) {
			if (field.getPartialName().equals(FIELD_SIN)) {
				field.setValue(sin);
			}
			if (field.getPartialName().equals(FIELD_FNAME)) {
				field.setValue(fName);
			}
			if (field.getPartialName().equals(FIELD_LNAME)) {
				field.setValue(lName);
			}
			if (field.getPartialName().equals(FIELD_FULLNAME)) {
				field.setValue(fName + " " + lName);
			}
		}
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		authReqPDF.save(outStream);
		
		ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());
		outStream.close();
		
		Document document = null;
		
		try {
			document = EncryptionService.encryptDocument(inStream, fName + "_" + lName + "_AuthRequest.pdf");
		} catch (NumberFormatException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | ConfigException | IOException e) {
			e.printStackTrace();
			LogEntryManager.logError(null, e, null);
			inStream.close();
			authReqPDF.close();
			return null;
		}
		
		inStream.close();
		authReqPDF.close();
		
		return document;
	}

	/**
	 * Creates a signed form from a signature and a form.
	 * 
	 * @param signatureDataURL String The signature in the form of a string of characters.
	 * @param parcelID String The id number for the parcel containing the form.
	 * @return Document The record of the encrypted signed pdf form document that was created.
	 * @throws ConfigException If the config file is missing.
	 */
	public static Document signForm(String signatureDataURL, String parcelID) throws ConfigException {
		init();
		String fileName = null;
		//get signature bytes from signatureDataURL
		byte[] imagedata = DatatypeConverter.parseBase64Binary(signatureDataURL.substring(signatureDataURL.indexOf(",") + 1));

		try {

			//get Document to sign from parcel (get parcel from parcel ID)
			Document pdfToSignDoc = null;
			pdfToSignDoc = ParcelManager.get(parcelID).getDocuments().get(0);
			fileName = ParcelManager.get(parcelID).getDocuments().get(0).getFileName() + "_SIGNED.pdf";
			String filepath = EncryptionService.decryptDocument(pdfToSignDoc);
			File pdfToSignFile = new File(filepath);
			PDDocument pdfToSign = PDDocument.load(pdfToSignFile);
			pdfToSign.setAllSecurityToBeRemoved(true);
			
			//FINE UP TILL NOW

			PDPage page = pdfToSign.getPage(0);

			PDImageXObject pdImage = PDImageXObject.createFromByteArray(pdfToSign, imagedata, "signature.png");

			PDPageContentStream contentStream = new PDPageContentStream(pdfToSign, page, AppendMode.APPEND, true);
			
			//CHECK WHICH DOC WE USING CHANGE COORDS BASED ON THAT
			int sigX = T183_X_POS;
			int sigY = T183_Y_POS;
			List<PDField> list = pdfToSign.getDocumentCatalog().getAcroForm().getFields();
			if (list.get(0).getPartialName().equals(FIELD_SIN)) {
				sigX = AUTH_X_POS;
				sigY = AUTH_Y_POS;
				contentStream.drawImage(pdImage, sigX, sigY, 216, 56); //scale for auth form
			} else {
				contentStream.drawImage(pdImage, sigX, sigY, 160, 42); //scale for T183
			}
			
			//THIS LINE DOESNT BREAK IT
			
			contentStream.close();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			pdfToSign.save(out);
			pdfToSign.close();
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			
			Document signedDoc = EncryptionService.encryptDocument(in, fileName);
			
			pdfToSignFile.delete(); //is this right?
			
			return signedDoc;
			
		} catch (ConfigException | NumberFormatException | IOException | InvalidAlgorithmParameterException | InvalidKeySpecException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
			LogEntryManager.logError(null, e, null);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Initializes the config-derived semi-constants in this class.
	 * 
	 * @throws ConfigException if the config file is missing.
	 */
	private static void init() throws ConfigException {
		if (!init) {
			authReqPDFFilePath = ConfigService.fetchFromConfig("AUTH_REQ_FILEPATH:");
			init = true;
		}
	}
}