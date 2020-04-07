package service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import domain.Document;
import exception.ConfigException;
import manager.ParcelManager;
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

	//x & y positions for signtures
	private final static int T183_X_POS = 0;
	private final static int T183_Y_POS = 0;
	private final static int AUTH_X_POS = 0;
	private final static int AUTH_Y_POS = 0;

	/**
	 * @param sin
	 * @param fName
	 * @param lName
	 * @return
	 * @throws ConfigException
	 * @throws IOException
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
			inStream.close();
			authReqPDF.close();
			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			inStream.close();
			authReqPDF.close();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			inStream.close();
			authReqPDF.close();
			return null;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			inStream.close();
			authReqPDF.close();
			return null;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			inStream.close();
			authReqPDF.close();
			return null;
		} catch (ConfigException e) {
			e.printStackTrace();
			inStream.close();
			authReqPDF.close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			inStream.close();
			authReqPDF.close();
			return null;
		}
		
		inStream.close();
		authReqPDF.close();
		
		return document;
	}

	/**TODO only works with the 2 forms we need to sign 
	 * @param parts
	 * @param parcelID
	 * @return
	 * @throws ConfigException
	 */
	public static Document signForm(String signatureDataURL, String parcelID) throws ConfigException {
		//TODO
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
			}
			
			//THIS LINE DOESNT BREAK IT
			contentStream.drawImage(pdImage, sigX, sigY);
			contentStream.close();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			pdfToSign.save(out);
			pdfToSign.close();
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			
			Document signedDoc = EncryptionService.encryptDocument(in, fileName);
			
			pdfToSignFile.delete(); //is this right? TODO
			
			return signedDoc;
			
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static void init() throws ConfigException {
		if (!init) {
			authReqPDFFilePath = ConfigService.fetchFromConfig("AUTH_REQ_FILEPATH:");
			init = true;
		}
	}
}