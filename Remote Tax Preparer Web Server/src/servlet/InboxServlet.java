package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.ConfigException;
import manager.ParcelManager;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman
 */
@WebServlet("/inbox")
public final class InboxServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807630350799183535L;

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

		//give the jsp the user's messages
		try {
			request.setAttribute("inbox", ParcelManager.getParcels(-1, null, email, null, -1));
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Display Inbox page
		getServletContext().getRequestDispatcher("/WEB-INF/inbox.jsp").forward(request, response);
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
