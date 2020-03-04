package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import domain.Document;
import exception.ConfigException;
import manager.ParcelManager;
import manager.SessionManager;
import service.ConfigService;
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

	String uploadPath;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateParcelServlet() {
		super();
		try {
			uploadPath = ConfigService.fetchFromConfig("SERVER_STORAGE_PATH:");
		} catch (ConfigException e) {
			System.out.println("Failed to initialize from config");
			e.printStackTrace();
		}
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

		Debugger.log(uploadPath);
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();

		String fileName;

		ArrayList<Document> documents = new ArrayList<>();

		try {
			//grab tax year
			String taxYearString = request.getParameter("taxYear");
			int taxYear = -1;
			taxYear = Integer.parseInt(taxYearString);

			//grab message
			String message = request.getParameter("message");
			//grab subject
			String subject = request.getParameter("subject");
			//grab sender
			String sender = email;
			//grab receiver
			String receiver = request.getParameter("receiver");
			//grab attachments
			for (Part part : request.getParts()) {
				fileName = part.getSubmittedFileName();
				String writePath = uploadPath + File.separator + fileName;
				part.write(writePath);
				documents.add(new Document(uploadPath, isSigned, requiresSignature, fileName, part.getSize()));
			}

			if (!ParcelManager.createParcel(documents, subject, message, sender, receiver, new Date(), null, taxYear)) {
				request.setAttribute("errorMessage", "Couldn't send message");
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			} else {
				request.setAttribute("successMessage", "Message Sent");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "Error in year format, this shouln't happen");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			e.printStackTrace();
		} catch (ConfigException e) {
			request.setAttribute("errorMessage", "Error, please try again");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			e.printStackTrace();
		}

		//Display NewMessage? page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);

	}
}
