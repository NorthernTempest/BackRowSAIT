package servlet;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;
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
import service.EncryptionService;
import util.cesar.Debugger;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman
 */
@WebServlet("/parcel/view")
public final class ViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807630350799183535L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("ViewServlet.doGet");

		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		//get parcel ID
		String parcelID = null;
		try {
			parcelID = request.getParameter("parcelID");
		} catch (NumberFormatException e) {
			Debugger.log("caught number format exception, is this not a number?: ");
			Debugger.log(request.getAttribute("parcelID"));
		}
		
		//check that its a real parcel that the user can view
		try {
			if(ParcelManager.isVisibleToUser(email, parcelID)) {
				//push the parcel to jsp
				Parcel parcel = ParcelManager.get(parcelID);
				ArrayList<String> documentPaths = new ArrayList<>();
				for(Document document : parcel.getDocuments()) {
					documentPaths.add(EncryptionService.decryptDocument(document));
				}
				session.setAttribute("parcel", parcel);
				session.setAttribute("documentPaths", documentPaths);
			} else {
				//not authorized to view parcel
				request.setAttribute("errorMessage", "Error viewing message");
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			}
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "Error viewing message");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "Error viewing message");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "Error viewing message");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
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
		}
		
		//Display View page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("ViewServlet.doPost");

		//Display View page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/view.jsp").forward(request, response);

	}
}
