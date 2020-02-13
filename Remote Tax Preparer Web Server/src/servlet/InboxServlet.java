package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Parcel;
import exception.ConfigException;
import manager.ParcelManager;
import util.cesar.Debugger;

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
		Debugger.log("InboxServlet.doGet");

		//get session email
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		Debugger.log("Email: " + email);

		//give the jsp the user's messages
		try {
			request.setAttribute("parcels", ParcelManager.getParcels(-1, null, email, null, -1));
			Debugger.log("Parcels: " + ParcelManager.getParcels(-1, null, email, null, -1));
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			Debugger.log("CONFIG EXCEPTION");
			e.printStackTrace();
		}

		//Display Inbox page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("InboxServlet.doPost");

		//Display Inbox page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);

	}
}
