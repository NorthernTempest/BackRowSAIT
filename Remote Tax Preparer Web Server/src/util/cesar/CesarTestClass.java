package util.cesar;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import exception.ConfigException;
import service.PDFService;

public class CesarTestClass {

	public static void main(String[] args) throws ConfigException, IOException {
		doStuff();
		
	}

	private static void doStuff() throws ConfigException, IOException {
		String testTarget = "C:/Capstone/TestFiles/testWithFilledForm.pdf";
		File file = new File(testTarget);
		PDDocument doc = PDFService.createAuthorizationRequest("123123123", "testman", "lastname");
		
		doc.save(file);
		doc.close();
	}

}
