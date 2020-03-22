package servlet;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;
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
import service.EncryptionService;
import util.cesar.Debugger;

/**
 * Servlet for creating and sending a message.
 * 
 * @author Cesar Guzman
 */
@WebServlet("/parcel/create")
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 1024 * 1024 * 25, maxRequestSize = 1024 * 1024 * 25 * 10) //0mb, 25mb, 10x 25mb
public final class CreateParcelServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807630350769183535L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateParcelServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("NewMessageServlet.doGet");

		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		//Display NewMessage page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("NewMessageServlet.doPost");

		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		String fileName;

		ArrayList<Document> documents = new ArrayList<>();

		try {
			//grab tax year
			String taxYearString = request.getParameter("taxYear");
			int taxYear = -1;
			if( taxYearString != null )
				taxYear = Integer.parseInt(taxYearString);

			//grab message
			String message = request.getParameter("message");
			//grab subject
			String subject = request.getParameter("subject");
			//grab sender
			String sender = email;
			//grab receiver
			String receiver = request.getParameter("receiver");
			//grab requiresSignature
			String requiresSignatureString = request.getParameter("requiresSignature");
			boolean requiresSignature = false;
			if (requiresSignatureString != null) {
				requiresSignature = true;
			}
			//grab attachments
			for (Part part : request.getParts()) {
				fileName = part.getSubmittedFileName();
				Debugger.log(fileName);
				if (fileName != null && !fileName.equals("null")) {
					documents.add(EncryptionService.encryptDocument(part.getInputStream(), fileName));
				}
			}

			Debugger.log("documents: " + documents + "\nSubject: " + subject + "\nMessage: " + message + "\nSender: "
					+ sender + "\nReceiver: " + receiver + "\nTax Year: " + taxYear + "\nRequires Signature: "
					+ requiresSignatureString);

			if (!ParcelManager.createParcel(documents, subject, message, sender, receiver, new Date(), null, taxYear,
					requiresSignature)) {
				request.setAttribute("errorMessage", "Couldn't send message");
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			} else {
				request.setAttribute("successMessage", "Message Sent");
			}

			//Display NewMessage? page
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "Error in year format, this shouln't happen");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			e.printStackTrace();
		} catch (ConfigException e) {
			request.setAttribute("errorMessage", "Error, please try again");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			e.printStackTrace();
		} catch (IllegalStateException e) {
			request.setAttribute("errorMessage", "Your files exceed the maximum file size limit (25 MB)");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
}
