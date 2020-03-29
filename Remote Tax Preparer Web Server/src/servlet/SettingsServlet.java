package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ConfigException;
import manager.LogEntryManager;
import manager.UserManager;

/**
 * Servlet implementation class SettingsServlet
 * 
 * @author Jesse Goerzen
 */
@WebServlet(description = "Edits user's settings.", urlPatterns = { "/settings" })
public class SettingsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3482363788811200914L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SettingsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email; 
			if (request.getAttribute("userEmail")!=null) {
				email = (String) request.getAttribute("userEmail");
			} else {
				email = UserManager.getEmailFromSession(request);
			}
			UserManager.getAccountInfo(request,email);
		} catch (ConfigException e) {
			request.setAttribute("errorMessage",
					"There was an error accessing your data." + request.getAttribute("errorMessage"));
			LogEntryManager.logError(null, e, request.getRemoteAddr());
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/WEB-INF/user/edit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String delete = request.getParameter("delete");

		if (delete == null) {

			try {
				String email; 
				if (request.getAttribute("userEmail")!=null || request.getAttribute("userEmail").equals("")) {
					email = (String) request.getAttribute("userEmail");
				} else {
					email = UserManager.getEmailFromSession(request);
				}
				UserManager.setAccountInfo(request, email);
			} catch (ConfigException e) {
				request.setAttribute("errorMessage",
						"There was an error saving your changes." + request.getAttribute("errorMessage"));
				LogEntryManager.logError(null, e, request.getRemoteAddr());
				e.printStackTrace();
			}

			try {
				String email; 
				if (request.getAttribute("userEmail")!=null || request.getAttribute("userEmail").equals("")) {
					email = (String) request.getAttribute("userEmail");
				} else {
					email = UserManager.getEmailFromSession(request);
				}
				UserManager.getAccountInfo(request, email);
			} catch (ConfigException e) {
				request.setAttribute("errorMessage",
						"There was an error accessing your data." + request.getAttribute("errorMessage"));
				LogEntryManager.logError(null, e, request.getRemoteAddr());
				e.printStackTrace();
			}

			getServletContext().getRequestDispatcher("/WEB-INF/user/edit.jsp").forward(request, response);
		} else {
			if(UserManager.deleteAccount(request.getSession().getId(), request.getRemoteAddr()))
				response.sendRedirect("/login?action=logout");
			else {
				request.setAttribute("errorMessage", "Failed to delete your account.");
				getServletContext().getRequestDispatcher("/WEB-INF/user/edit.jsp").forward(request, response);
			}
		}
	}
}
