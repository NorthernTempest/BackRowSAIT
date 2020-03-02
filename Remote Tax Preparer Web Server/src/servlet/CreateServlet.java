package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import exception.ConfigException;
import manager.ParcelManager;
import manager.SessionManager;
import util.cesar.Debugger;

/**
 * Servlet for creating and sending a message.
 * 
 * @author Cesar Guzman
 */
@WebServlet("/parcel/create")
public final class CreateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807630350769183535L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateServlet() {
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

		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		Debugger.log("file dir: " + filesDir.getAbsolutePath());
		fileFactory.setRepository(filesDir);
		ServletFileUpload uploader = new ServletFileUpload(fileFactory);

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
			//grab attachments
			List<FileItem> fileItemsList = null;

			fileItemsList = uploader.parseRequest((RequestContext) request);

			if(!ParcelManager.createParcel(fileItemsList, subject, message, sender, null, new Date(), null, taxYear)) {
				request.setAttribute("errorMessage", "Couldn't send message");
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			} else {
				request.setAttribute("successMessage", "Message Sent");
			}
			
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "Error in year format, this shouln't happen");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/create.jsp").forward(request, response);
			e.printStackTrace();
		} catch (FileUploadException e) {
			request.setAttribute("errorMessage", "Error uploading");
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
