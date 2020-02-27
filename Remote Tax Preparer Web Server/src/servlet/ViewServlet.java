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
import manager.SessionManager;
import util.cesar.Debugger;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman
 */
@WebServlet("/parcel/view")
public final class ViewServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807630350799183535L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("ViewServlet.doGet");

		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		//get parcel ID
		int parcelID = -1;
		try {
			parcelID = Integer.parseInt(request.getParameter("parcelID"));
		} catch (NumberFormatException e) {
			Debugger.log("caught number format exception, is this not a number?: ");
			Debugger.log(request.getAttribute("parcelID"));
		}
		
		//check that its a real parcel that the user can view
		try {
			if(ParcelManager.isVisibleToUser(email, parcelID)) {
				//push the parcel to jsp
				session.setAttribute("parcel", ParcelManager.get(parcelID));
			} else {
				//not authorized to view parcel
				request.setAttribute("errorMessage", "Error viewing message");
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			}
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "Error viewing message");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "Error viewing message");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errorMessage", "Error viewing message");
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
			e.printStackTrace();
		}
		
		//Display View page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("ViewServlet.doPost");

		//Display View page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/view.jsp").forward(request, response);

	}
}
