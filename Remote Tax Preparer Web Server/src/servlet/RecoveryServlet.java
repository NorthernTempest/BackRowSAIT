package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import exception.ConfigException;
import manager.LogEntryManager;
import manager.UserManager;

/**
 * Servlet implementation class RecoveryServlet
 */
@WebServlet("/recover")
public final class RecoveryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6312378895160822989L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecoveryServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String verify = request.getParameter("verify");

		if (verify == null || verify.equals(""))
			getServletContext().getRequestDispatcher("/WEB-INF/recovery/recovery.jsp").forward(request, response);
		else {
			boolean verifyIsValid = false;
			String errorMessage = "";

			try {
				verifyIsValid = UserManager.verification(verify, request.getRemoteAddr(), User.VERIFY_TYPE_PASS_RESET);
			} catch (ConfigException e) {
				errorMessage += e.getMessage();
			}

			request.setAttribute("errorMessage", errorMessage);

			if (verifyIsValid) {
				request.setAttribute("verify", verify);
				
				getServletContext().getRequestDispatcher("/WEB-INF/recovery/newpass.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			UserManager.recover(request.getParameter("email"), request.getRemoteAddr());
		} catch (ConfigException e) {
			e.printStackTrace();
			LogEntryManager.logError(request.getParameter("email"), e, request.getRemoteAddr());
		}
		
		request.setAttribute("message", "If the email you gave was associated with an account, we sent an email to it.");
		getServletContext().getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
	}

}
