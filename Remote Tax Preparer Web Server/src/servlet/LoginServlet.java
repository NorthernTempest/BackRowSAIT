package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.SessionManager;
import manager.UserManager;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman, Taylor Hanlon
 */
@WebServlet("/login")
public final class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		//Display Login page
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Grab user email
		String email = request.getParameter("email");
		//Grab user password
		String password = request.getParameter("password");
		//Grab user ip
		String ip = request.getRemoteAddr();
		
		//try to login user, forward as appropriate.
		if (UserManager.tooManyAttempts(email)) {
			//tell user
			request.setAttribute("errorMessage", "Too many attemots, try again later");
			//forward to login
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			
		} else if(!UserManager.login(email, password, ip)) {
			//tell user
			request.setAttribute("errorMessage", "Incorrect email or password");
			//forward to login
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			
		} else {
			//get session id
			HttpSession session = request.getSession();
			String sessionID = session.getId();
			
			//create a session
			SessionManager.createSession(email, sessionID);
			
			//forward to home
			getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
		}
	}
}
