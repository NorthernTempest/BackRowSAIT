package servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import databaseAccess.TaxReturnDB;
import databaseAccess.UserDB;
import domain.TaxReturn;
import domain.User;
import exception.ConfigException;
import manager.LogEntryManager;
import manager.SessionManager;
import manager.TaxReturnManager;
import manager.UserManager;
import service.ConfigService;

/**
 * Servlet to display information about the customer's tax return and allow tax preparers to edit the status of a customer's tax return.
 * 
 * @author Jesse Goerzen
 */
@WebServlet("/return")
public class TaxReturnEditServlet extends HttpServlet {
	private static final long serialVersionUID = -8488700539015034763L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public TaxReturnEditServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Calendar c = Calendar.getInstance();
		
		int role = -1;
		String email = request.getParameter("email");
		String year = request.getParameter("year");
		User u = null;
		TaxReturn tr = null;
		int yr = c.get(Calendar.YEAR) - 1;
		double amount = 0;

		try {
			role = UserManager.getRole(request.getSession().getId());
		} catch (ConfigException e) {
			LogEntryManager.logError(SessionManager.getEmail(request.getSession().getId()), e, request.getRemoteAddr());
			e.printStackTrace();
		}
		
		if(role <= User.USER) {
			email = SessionManager.getEmail(request.getSession().getId());
			request.setAttribute("locked", "t");
		}
		
		try {
			amount = Double.parseDouble(ConfigService.fetchFromConfig("standardcost:"));
		} catch (NumberFormatException | ConfigException e) {
			LogEntryManager.logError(SessionManager.getEmail(request.getSession().getId()), e, request.getRemoteAddr());
			e.printStackTrace();
		}
		
		if(email != null && !email.equals("")) {
			u = UserDB.get(email);
			
			if(year != null && !year.equals(""))
				try {
					yr = Integer.parseInt(year);
				} catch(NumberFormatException e) {
					LogEntryManager.logError(SessionManager.getEmail(request.getSession().getId()), e, request.getRemoteAddr());
					e.printStackTrace();
				}
				
			tr = TaxReturnDB.get(email, yr);
			
			if(tr != null) {
				amount = tr.getCost();
			}
		}
		
		request.setAttribute("year", yr);
		request.setAttribute("lastYear", c.get(Calendar.YEAR) - 1);
		request.setAttribute("email", u == null ? "" : u.getEmail());
		request.setAttribute("status", tr == null ? "" : tr.getStatus());
		request.setAttribute("amount", amount);
		
		getServletContext().getRequestDispatcher("/WEB-INF/updatereturn.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int role = -1;
		
		try {
			role = UserManager.getRole(request.getSession().getId());
		} catch (ConfigException e) {
			LogEntryManager.logError(SessionManager.getEmail(request.getSession().getId()), e, request.getRemoteAddr());
			e.printStackTrace();
		}
		
		if(role >= User.TAX_PREPARER) {
			String email = request.getParameter("email");
			String status = request.getParameter("status");
			int year = Integer.parseInt(request.getParameter("year"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			
			if(TaxReturnManager.updateReturn(email, year, status, amount, request.getSession().getId()))
				request.setAttribute("successMessage", "User updated!");
			else
				request.setAttribute("errorMessage", "Failed to update user.");
		}
		
		doGet(request, response);
	}

}
