package servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import exception.ConfigException;
import manager.LogEntryManager;
import manager.SessionManager;
import service.ConfigService;

/**
 * Servlet for processing backup requests.
 * 
 * @author Jesse Goerzen
 */
@WebServlet("/backup")
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 1024 * 1024 * 100, maxRequestSize = 1024 * 1024 * 100)
public class BackupServlet extends HttpServlet {
	private static final long serialVersionUID = 5267507870191408077L;

	/**
	 * file name for backup file.
	 */
	private static String backupFileName;

	/**
	 * true if Servlet has run the myInit() method, false otherwise.
	 */
	private static boolean init = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BackupServlet() {
		super();
	}

	/**
	 * Initializes variables based on config file contents, should only run once.
	 * 
	 * @throws ConfigException
	 */
	private static void myInit() throws ConfigException {
		if (!init) {
			backupFileName = ConfigService.fetchFromConfig("backupfilename:");
			init = true;
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("/admin");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		// if the request is multipart, try to recover the system.
		if (request.getContentType() != null && request.getContentType().contains("multipart")) {
			Collection<Part> parts = request.getParts();

			String sessionID = request.getSession().getId();

			String ip = request.getRemoteAddr();

			boolean hasRecovered = LogEntryManager.recover(parts, sessionID, ip);

			if (hasRecovered)
				request.setAttribute("successMessage", "System restore complete!");
			else
				request.setAttribute("errorMessage", "System restore failed!");
			getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
		}
		// if action is null, send the user back to /admin
		else if (action == null || action.equals("")) {
			response.sendRedirect("/admin");
		}
		// if action is backup, send the user the backup
		else if (action.equals("backup")) {
			try {
				myInit();
				response.setContentType("application/zip");
				response.setHeader("Content-disposition", "attachment; filename=" + backupFileName
						+ new Date().toString().replaceAll("\\s{1,}", "_").replaceAll(":", "-") + ".zip");

				LogEntryManager.sendBackup(response.getOutputStream(), request.getSession().getId(),
						request.getRemoteAddr());
			} catch (ConfigException e) {
				LogEntryManager.logError(SessionManager.getEmail(request.getSession().getId()), e,
						request.getRemoteAddr());
				e.printStackTrace();

				response.sendRedirect("/admin?errorMessage=Failed to create file");
			}
		} else {
			response.sendRedirect("/admin");
		}
	}
}
