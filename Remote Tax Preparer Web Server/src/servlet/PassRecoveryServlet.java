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
 * Servlet for handling password recovery requests.
 * 
 * @author Jesse Goerzen
 */
@WebServlet("/recover")
public final class PassRecoveryServlet extends HttpServlet {

	/**
	 * The Serial Unique ID. Was computer generated.
	 */
	private static final long serialVersionUID = 6312378895160822989L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PassRecoveryServlet() {
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
				verifyIsValid = UserManager.verification(verify, User.VERIFY_TYPE_PASS_RESET);
			} catch (ConfigException e) {
				errorMessage += e.getMessage();
			}

			if (errorMessage != null && !errorMessage.equals(""))
				request.setAttribute("errorMessage", errorMessage);

			if (verifyIsValid) {
				request.setAttribute("verify", verify);

				getServletContext().getRequestDispatcher("/WEB-INF/recovery/newpass.jsp").forward(request, response);
			} else {
				request.setAttribute("message",
						"Sorry! You waited too long. Please follow <a href=\"/recover\">this link</a> to request another email.");
				getServletContext().getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String verify = request.getParameter("verify");

		if (verify != null) {
			String message;

			try {
				boolean passChanged = UserManager.recoveryChangePass(request.getParameter("newPass1"),
						request.getParameter("newPass2"), verify, request.getRemoteAddr());

				if (passChanged) {
					message = "Success! You've changed your password. Please <a href=\"/login\">log in</a> to continue.";
				} else {
					message = "Failure! We couldn't set your new password. Please follow the instructions in your email again.";
				}
			} catch (Exception e) {
				message = e.getMessage() + " Please follow the instructions in your email again.";
			}

			request.setAttribute("message", message);

			getServletContext().getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
		} else {
			Thread t = new Thread( new Runnable() {

				@Override
				public void run() {
					try {
						UserManager.recover(request.getParameter("email"), request.getRemoteAddr());
					} catch (ConfigException e) {
						e.printStackTrace();
						LogEntryManager.logError(request.getParameter("email"), e, request.getRemoteAddr());
					}
				}
			
			} );
			
			t.start();
			
			response.sendRedirect("/login?recovered");
		}
	}
}
