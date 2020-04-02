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
import exception.ConfigException;
import manager.ParcelManager;
import manager.SessionManager;
import service.PDFService;
import util.cesar.Debugger;

/**
 * Servlet implementation class SignDocumentServlet
 */
@WebServlet("/parcel/signDoc")
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 1) //0mb, 1mb, 1x 1mb
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
		Collection<Part> parts = null;
		try {
			parts = request.getParts();
		} catch (IllegalStateException e) {
			request.setAttribute("errorMessage", "Error gettin signature, try again");
			Debugger.log("caught IllegalStateException");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			return;
		}

		//get parcel ID
		String parcelID = request.getParameter("parcelID");

		//Give parcelID and signature to PDFService to sign the document
		Document signedPDF = null;
		try {
			signedPDF = PDFService.signForm(parts, parcelID);
			Debugger.log("we tryna sign the form");
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//make a parcel with preset message to taxPreparer saying its a signed document
		if (signedPDF != null) {
			try {
				ParcelManager.createSignedDocParcel(signedPDF, email, ParcelManager.get(parcelID).getTaxReturnYear());
				Debugger.log("we made it to here hopefully we got a parcel?");
			} catch (ConfigException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//bad bad no bad awful nightmare bad no
			//TODO
		}
		
		

		//let user know their signature has been confirmed
		//resend the parcel that had document to be signed attached, but this time without the req signature tag
		//delete the one with req signature tag

		//don't redirect
	}

}
