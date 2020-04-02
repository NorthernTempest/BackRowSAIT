package servlet;

import java.io.IOException;
import java.util.Collection;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import domain.Document;
import domain.Parcel;
import exception.ConfigException;
import manager.ParcelManager;
import manager.SessionManager;
import service.PDFService;
import util.cesar.Debugger;

/**
 * Servlet implementation class SignDocumentServlet
 */
@WebServlet("/parcel/signDoc")
public final class SignDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignDocumentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parcelID = request.getParameter("parcelID");
		Debugger.log("parcelID: " + parcelID);

		try {
			request.setAttribute("parcel", ParcelManager.get(parcelID));
		} catch (ConfigException e) {
			request.setAttribute("errorMessage", "error, please try again");
			e.printStackTrace();
		}

		getServletContext().getRequestDispatcher("/WEB-INF/parcel/signDocument.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		//get signature
		String signatureDataURL = request.getParameter("signature");
		//get parcel ID
		String parcelID = request.getParameter("parcelID");

		Debugger.log("signatureDataURL: " + signatureDataURL);
		Debugger.log("parcelID: " + parcelID);
		
		//Give parcelID and signature to PDFService to sign the document
		Document signedPDF = null;
		try {
			Debugger.log("we tryna sign the form");
			signedPDF = PDFService.signForm(signatureDataURL, parcelID);
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//make a parcel with preset message to taxPreparer saying its a signed document
		if (signedPDF != null) {
			try {
				Parcel parcel = ParcelManager.get(parcelID);
				ParcelManager.createSignedDocParcel(signedPDF, email, parcel.getTaxReturnYear());
				Debugger.log("we made it to here hopefully we got a parcel?");
				//let user know their signature has been confirmed
				request.setAttribute("successMessage", "Your document was successfully signed.");
				//resend the parcel that had document to be signed attached, but this time without the req signature tag
				ParcelManager.createParcel(parcel.getDocuments(), parcel.getSubject(), parcel.getMessage(),
						parcel.getSender(), parcel.getReceiver(), parcel.getDateSent(), parcel.getExpirationDate(),
						parcel.getTaxReturnYear(), false);
				//delete the one with req signature tag
				ParcelManager.delete(parcelID);
			} catch (ConfigException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			//bad bad no bad awful nightmare bad no
			//this is when we dont sign a doc because of some error woops
			//TODO
		}
		//don't redirect
	}
}
