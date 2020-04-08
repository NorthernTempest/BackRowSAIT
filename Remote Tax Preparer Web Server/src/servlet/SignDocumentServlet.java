package servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Document;
import domain.Parcel;
import exception.ConfigException;
import manager.ParcelManager;
import manager.SessionManager;
import service.PDFService;
import util.cesar.Debugger;

/**
 * Servlet handling sign document requests
 */
@WebServlet("/parcel/signDoc")
public final class SignDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = -374026427988052768L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignDocumentServlet() {
		super();
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
			request.setAttribute("errorMessage", "Error: Your document was not signed.");
			getServletContext().getRequestDispatcher("/WEB-INF/inbox.jsp").forward(request, response);
			e.printStackTrace();
			return;
		}

		//make a parcel with preset message to taxPreparer saying its a signed document
		if (signedPDF != null) {
			try {
				Parcel parcel = ParcelManager.get(parcelID);
				ParcelManager.createSignedDocParcel(signedPDF, email, parcel.getTaxReturnYear());
				Debugger.log("we made it to here hopefully we got a parcel?");
				//let user know their signature has been confirmed
				request.setAttribute("successMessage", "Your document was successfully signed.");
				//delete the one with req signature tag
				boolean didDelete = ParcelManager.delete(parcelID);
				Debugger.log(didDelete +": we just tried to delete parcelID: " + parcelID);
				//resend the parcel that had document to be signed attached, but this time without the req signature tag
				boolean didCreate = ParcelManager.createParcel(parcel.getDocuments(), parcel.getSubject(), parcel.getMessage(),
						parcel.getSender(), parcel.getReceiver(), parcel.getDateSent(), parcel.getExpirationDate(),
						parcel.getTaxReturnYear(), false);
				Debugger.log(didCreate +": we just tried to create");
				
				
			} catch (ConfigException e) {
				request.setAttribute("errorMessage", "Error: Your document was not signed.");
				e.printStackTrace();
			}

		} else {
			//bad bad no bad awful nightmare bad no
			request.setAttribute("errorMessage", "Error: Your document was not signed.");
		}
		
		request.setAttribute("redirectTimer", 4000);
		request.setAttribute("redirectLocation", "/inbox");
		getServletContext().getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}
}
