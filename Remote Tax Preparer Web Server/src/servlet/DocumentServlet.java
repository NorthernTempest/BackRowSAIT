package servlet;

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
 * Servlet implementation class DocumentServlet
 */
@WebServlet("/parcel/document")
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DocumentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Get document from parcel
		String parcelID = request.getParameter("parcelID");
		Parcel parcel = null;
		
		try {
			parcel = ParcelManager.get(parcelID);

			HttpSession session = request.getSession();
			String email = SessionManager.getEmail(session.getId());
			Debugger.log("Email: " + email);

			if (ParcelManager.isVisibleToUser(email, parcelID)) {
				Document file = parcel.getDocuments().get(0);

				response.setContentType("application/pdf");

				EncryptionService.decryptDocument(file, response.getOutputStream());
			} else {
				request.setAttribute("errorMessage", "error, please try again");
			}
		} catch (ConfigException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "error, please try again");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "error, please try again");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "error, please try again");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "error, please try again");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "error, please try again");
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "error, please try again");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
