package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.TaxReturn;
import manager.ParcelManager;
import manager.SessionManager;
import manager.TaxReturnManager;
import manager.UserManager;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman, Taylor Hanlon
 */
@WebServlet("/inbox")
public final class InboxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InboxServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//get session email
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");

		//get user's most recent tax return id
		int mostRecentTaxReturnID = TaxReturnManager.getMostRecentID(email);

		//show messages with receiver user, tax return most recent
		ParcelManager.getByParameters(0, null, email, null, mostRecentTaxReturnID);

		//Display Inbox page
		getServletContext().getRequestDispatcher("/WEB-INF/inbox.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Display Inbox page
		getServletContext().getRequestDispatcher("/WEB-INF/inbox.jsp").forward(request, response);

	}
}
