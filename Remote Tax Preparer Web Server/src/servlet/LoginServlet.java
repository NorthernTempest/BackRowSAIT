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
	 * Maximum number of login attempts within LOGIN_ATTEMPT_TIMELIMIT
	 */
	private static final int MAX_LOGIN_ATTEMPTS = 5;
	/**
	 * Time in minutes to check for login attempts
	 */
	private static final int LOGIN_ATTEMPT_TIMELIMIT = 10;

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
		
		//Check logs for attempts from email, if too many attempts don't let them in.
		if(tooManyAttempts(email)) {
			request.setAttribute("errorMessage", "Too many log in attempts, try again later");
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		//Grab user password
		String password = request.getParameter("password");
		
		//validate user info, forward.
		String logMessage;
		if (!UserManager.authenticate(email, password)) {
			//tell user
			request.setAttribute("errorMessage", "Incorrect email or password");

			//set log message
			logMessage = "Failed login attempt";
			
			//forward to login
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			
		} else {
			//get session id
			HttpSession session = request.getSession();
			String sessionID = session.getId();
			
			//add session to database
			SessionManager.createSession(email, sessionID);
			
			//set log message
			logMessage = "Successful login attempt";
			
			//forward to home
			getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
		}
		
		//write to log
		LogEntryManager.createLogEntry(email, logMessage, LogEntry.LOGIN_ATTEMPT, request.getRemoteAddr());

	}
	
	/* ************************************************************************************************************************************************
	 * *****************************************************************HELPER METHODS*****************************************************************
	 * ************************************************************************************************************************************************
	 */

	/**
	 * Checks logs for login attempts from email, if too many attempts return true
	 * 
	 * @param email the email to check login attempts for
	 * @return true if too many log in attempts, false if not
	 */
	private boolean tooManyAttempts(String email) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date endDate = cal.getTime();
		cal.add(Calendar.MINUTE, LOGIN_ATTEMPT_TIMELIMIT);
		Date startDate = cal.getTime();
		ArrayList<LogEntry> logs = LogEntryManager.getLog(email, LogEntry.LOGIN_ATTEMPT, startDate, endDate, null);
		
		if(logs.size() > MAX_LOGIN_ATTEMPTS) {
			return true;
		}
		return false;
	}

}
