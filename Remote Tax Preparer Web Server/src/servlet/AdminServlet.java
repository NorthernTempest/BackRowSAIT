package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ConfigException;
import manager.UserManager;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String email;
		
		if (action.equals("restore")) {
			email = request.getParameter("restoreEmail");
			
			if (email != null && UserManager.userExists(email)) {
				UserManager.adminRestoreAccount(email, request.getSession().getId());
				request.setAttribute("successMessage", "Account restored!");
			}
			
			else {
				request.setAttribute("errorMessage", "Error processing request. Ensure the email is entered correctly.");
			}
		}
		
		else if (action.equals("deactivate")) {
			email = request.getParameter("deactivateEmail");
			
			if (email != null && UserManager.userExists(email)) {
				UserManager.adminDeleteAccount(email, request.getSession().getId());
				request.setAttribute("successMessage", "Account deactivated.");
			}
			
			else {
				request.setAttribute("errorMessage", "Error processing request. Ensure the email is entered correctly.");
			}
		} else if (action.equals("edit")) {
			email=request.getParameter("editEmail");
			
			if (email != null && UserManager.userExists(email)) {
				request.setAttribute("userEmail", email);
				try {
					UserManager.getAccountInfo(request, email);
				} catch (ConfigException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getServletContext().getRequestDispatcher("/WEB-INF/user/edit.jsp").forward(request, response);
			}
		}
		
		doGet(request, response);
	}

}
