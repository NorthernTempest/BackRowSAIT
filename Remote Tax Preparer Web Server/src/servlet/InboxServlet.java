package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Document;
import domain.Parcel;
import domain.TaxReturn;
import exception.ConfigException;
import manager.ParcelManager;
import manager.SessionManager;
import manager.TaxReturnManager;
import util.cesar.Debugger;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman
 */
@WebServlet("/inbox")
public final class InboxServlet extends HttpServlet {
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

		request.setAttribute("successMessage", request.getParameter("successMessage"));

		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		//give the jsp the user's messages
		try {
			ArrayList<Parcel> parcels = ParcelManager.getParcels(null, null, email, null, -1);
			parcels.addAll(ParcelManager.getParcels(null, email, null, null, -1));
			request.setAttribute("parcels", parcels);
			request.setAttribute("user", email);
			for (Parcel parcel : parcels) {
				Debugger.log("Parcel get: " + parcel.getSubject());
				Debugger.log("Parcel sig: " + parcel.isRequiresSignature());
				for(Document document : parcel.getDocuments()) {
					Debugger.log("Inbox document debug: " + document.getFileName());
				}
			}

			ArrayList<TaxReturn> taxReturns = TaxReturnManager.getByEmail(email);
			request.setAttribute("returns", taxReturns);
		} catch (ConfigException e) {
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
		doGet(request, response);
	}
}
