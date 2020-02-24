package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.ConfigException;
import manager.SessionManager;
import manager.UserManager;
import util.cesar.Debugger;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman, Taylor Hanlon
 */
@WebServlet("/login")
public final class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9068496584070534140L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("LoginServlet.doGet");
		String action = request.getParameter("action");
		
		if( action != null && action.equals("logout") )
		{
			SessionManager.invalidate( request.getSession().getId() );
			request.getSession().invalidate();
			response.sendRedirect("login");
		}
		else
			//Display Login page
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("LoginServlet.doPost");
		
		//Grab user email
		String email = request.getParameter("email");
		//Grab user password
		String password = request.getParameter("password");
		//Grab user ip
		String ip = request.getRemoteAddr();
		
		//try to login user, forward as appropriate.
		try {
			if (UserManager.tooManyAttempts(email)) {
				//tell user
				request.setAttribute("errorMessage", "Too many attempts, try again later");
				//forward to login
				getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				
			} else if(!UserManager.login(email, password, request.getSession().getId(), ip)) {
				//tell user
				request.setAttribute("errorMessage", "Incorrect email or password");
				//forward to login
				getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				
			} else {
				HttpSession session = request.getSession();
				
				//forward to home
				response.sendRedirect("inbox");
			}
		} catch (ConfigException e) {
			e.printStackTrace();
		}
	}
}
