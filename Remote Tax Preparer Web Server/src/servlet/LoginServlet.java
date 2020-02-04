package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.SessionManager;

/**
 * Servlet for logging into the site.
 * 
 * @author Cesar Guzman, Taylor Hanlon
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
		/* 
		 * display login page
		 */
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* TODO
		 * 
		 * grab email
		 * grab password
		 * 
		 * if inputs !validate()
		 * 		tell the user
		 * 		write to log
		 * 		check logs for attempts
		 * 		if attempts > threshold
		 * 				put login attempts from ip on timer
		 * 		go to get
		 * else
		 * 		get session from request
		 * 		put sessionID in database
		 * 		forward to home
		 */

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (!validate(email, password)) {
			request.setAttribute("errorMessage", "Incorrect email or password");
			/*
			 * write to log
			 * check logs for attempts
			 * if attempts > threshold
			 * 		put login attempts from ip on timer
			 * 	go to get
			 */
			
		} else {
			//get session id
			HttpSession session = request.getSession();
			String sessionID = session.getId();
			
			//add session to database
			SessionManager.createSession(email, sessionID);
			
			//forward to home
			getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
		}

	}

	/**
	 * Returns true if login credentials match entry in database, false if they do
	 * not.
	 * 
	 * @param email the email to validate
	 * @param password the password to validate
	 * @return true if login credentials match database, false if they do not
	 */
	private boolean validate(String email, String password) {
		/* TODO
		 * 
		 * check email vs database
		 * if no match
		 * 		return false
		 * else
		 * 		hash password
		 * 		check hashedPass vs database
		 * 		if match
		 * 			return true
		 * 		else
		 * 			return false
		 */
		return false;
	}
}
