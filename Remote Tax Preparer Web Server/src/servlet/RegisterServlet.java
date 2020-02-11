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
public final class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Display Register page
		getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Grab user ip
		String ip = request.getRemoteAddr();
		//Grab user email
		String email = request.getParameter("email");
		//Grab user password
		String password = request.getParameter("password1");
		//Grab user passConf
		String passConf = request.getParameter("password2");
		//Grab user title
		String title = request.getParameter("title");
		//Grab user fName
		String fName = request.getParameter("f-name");
		//Grab user mName
		String mName = request.getParameter("m-name");
		//Grab user lName
		String lName = request.getParameter("l-name");
		//Grab user phone
		String phone = request.getParameter("phone");
		//Grab user fax
		String fax = request.getParameter("fax");
		//Grab user language
		String language = request.getParameter("language");
		
		

		//hey usermanager make a user in the db
		//did you? 
		//dope. tell the user all is good
		//tell the user a verification email has been sent
		//go to login once user is like K
		//did you not?
		//uh oh. tell the user is not good
		//to to register page once user is like K

		//Show a page, probably login
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}
}
