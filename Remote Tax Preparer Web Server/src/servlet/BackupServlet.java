package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ConfigException;
import manager.LogEntryManager;
import manager.SessionManager;
import service.ConfigService;

/**
 * Servlet implementation class BackupServlet
 */
@WebServlet("/backup")
public class BackupServlet extends HttpServlet {
	private static final long serialVersionUID = 5267507870191408077L;
	private static String backupFileName;
	private static boolean init = false;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BackupServlet() {
		super();
	}
	
	private static void myInit() throws ConfigException {
		if(!init) {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		// if action is null, send the user back to /admin
		if( action == null || action.equals("") ) {
			response.sendRedirect("/admin");
		}
		// if action is backup, send the user the backup
		else if ( action.equals("backup") ) {
			try {
				myInit();
				response.setContentType("text/plain");
		        response.setHeader("Content-disposition", "attachment; filename=" + backupFileName + new Date().toString().replaceAll("\\s{1,}", "_").replaceAll(":", "-"));
		        
				LogEntryManager.sendBackup(response.getOutputStream(), request.getSession().getId(), request.getRemoteAddr());
			} catch (ConfigException e) {
				LogEntryManager.logError(SessionManager.getEmail(request.getSession().getId()), e, request.getRemoteAddr());
				e.printStackTrace();
				
				response.sendRedirect("/admin?errorMessage=Failed to create file");
			}
		} else if (action.equals("recover")) {
			if (LogEntryManager.recover(request.getInputStream(), request.getSession().getId(), request.getRemoteAddr()))
				request.setAttribute("successMessage", "System restore complete!");
			else
				request.setAttribute("errorMessage", "System restore complete!");
			getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp");
		} else {
			response.sendRedirect("/admin");
		}
	}
}
