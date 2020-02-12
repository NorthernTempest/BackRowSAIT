package servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.ParcelManager;
import util.cesar.Debugger;

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

		//show messages with receiver user, tax return most recent
		int lastYear = Calendar.YEAR -1;
		Debugger.log(lastYear);
		request.setAttribute("inbox", ParcelManager.getByYear(email, lastYear));

		//Display Inbox page
		getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
		//ass
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
