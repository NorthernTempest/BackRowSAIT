package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.LogEntry;
import manager.LogEntryManager;
import manager.SessionManager;

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
		 * check logs for attempts from ip
		 * if attempts > threshold
		 * 		tell the user
		 * 		return
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
		
		//Grab user email
		String email = request.getParameter("email");
		
		//Check logs for attempts from ip
		String ip = request.getRemoteAddr();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = cal.getTime();
		
		ArrayList<LogEntry> logs = LogEntryManager.getLog(null, email, LogEntry.LOGIN_ATTEMPT, new Date(), new Date(), null);

		//Grab user password
		String password = request.getParameter("password");
		//validate user info
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
