package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import exception.ConfigException;
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

		if (verify == null)
			getServletContext().getRequestDispatcher("/WEB-INF/recovery/newpass.jsp").forward(request, response);
		else {
			boolean verifyIsValid = false;
			String errorMessage = "";

			try {
				User u = UserManager.verification(verify);

				verifyIsValid = u != null;
			} catch (ConfigException e) {
				errorMessage += e.getMessage();
			}

			if (verifyIsValid) {
				request.setAttribute("verify", verify);
			}

			request.setAttribute("errorMessage", errorMessage);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean emailSent = false;
		try {
			emailSent = UserManager.recover(request.getParameter("email"), request.getRemoteAddr());
		} catch (ConfigException e) {

		}

		if (emailSent) {

		} else {

		}
	}

}
