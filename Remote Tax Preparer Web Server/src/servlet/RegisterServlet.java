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
@WebServlet("/register")
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
		//validate and get all inputs
		//Grab user email
		String email = request.getParameter("email");
		//Grab user password
		String password = request.getParameter("password1");
		//Grab user password confirmation
		String passwordConf = request.getParameter("password2");
		//Grab user title
		String title = request.getParameter("title");
		//Grab user first name
		String fName = request.getParameter("f-name");
		//Grab user middle name
		String mName = request.getParameter("m-name");
		//Grab user last name
		String lName = request.getParameter("l-name");
		//Grab user phone number
		String phone = request.getParameter("phone");
		//Grab user fax
		String fax = request.getParameter("fax");
		//Grab user language
		String language = request.getParameter("language");
		//Grab user address 1
		String streetAddress = request.getParameter("address1");
		//Grab user address 2
		String streetAddress2 = request.getParameter("address2");
		//Grab user city
		String city = request.getParameter("addressCity");
		//Grab user province
		String province = request.getParameter("addressRegion");
		//Grab user country
		String country = request.getParameter("addressCountry");
		//Grab user postalCode
		String postalCode = request.getParameter("addressPostal");
				
		//Basic user input validation
		Boolean valid = UserManager.registerValidate(email, password, passwordConf, title, fName, mName, lName, phone, fax, language, streetAddress, streetAddress2, city, province, country, postalCode);
		Boolean created=false;
		if (valid) {
		//hey usermanager make a user in the db
			created=UserManager.createUser(email, password, passwordConf, title, fName, mName, lName, phone, fax, language, streetAddress, streetAddress2, city, province, country, postalCode);
		}
		
		String message;
		if (created) {
			message = "Registration successful, please click the verification link sent to your email to continue";
		} else {
			message = "Something went wrong with registration, please check your enterred information and try again. If problem persists, please contact customer support";
		}
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
