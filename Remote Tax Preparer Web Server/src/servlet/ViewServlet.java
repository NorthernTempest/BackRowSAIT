package servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
import manager.DocumentManager;
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
		parcelID = request.getParameter("parcelID");
		

		//check that its a real parcel that the user can view
		try {
			if (ParcelManager.isVisibleToUser(email, parcelID)) {
				//push the parcel to jsp
				Parcel parcel = ParcelManager.get(parcelID);
				session.setAttribute("parcel", parcel);
				session.setAttribute("documents", parcel.getDocuments());
			} else {
				//not authorized to view parcel
				request.setAttribute("errorMessage", "Error viewing message");
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			}
		} catch (ConfigException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

		//get selected file name
		Document file = DocumentManager.get(request.getParameter("filePath"));
		
		response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=" + file.getFileName());
        
		Debugger.log("FILEPATH: " + file.getFilePath());
		
		try {
			EncryptionService.decryptDocument(file, response.getOutputStream());
		} catch (NumberFormatException e) {
			//TODO
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			//TODO
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			//TODO
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			//TODO
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			//TODO
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			//TODO
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			//TODO
			e.printStackTrace();
		} catch (ConfigException e) {
			//TODO
			e.printStackTrace();
		} catch (IOException e) {
			//TODO
			e.printStackTrace();
		}
	}
}
