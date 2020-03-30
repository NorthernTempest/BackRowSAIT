package servlet;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		if (request.getParameter("action")!=null&&request.getParameter("action").equals("resend")) {
			try {
				UserManager.resendVerificationEmail(request.getParameter("email"));
				String message = "Registration successful, please click the verification link sent to your email to continue. Remember to check your spam folder. If no email is sent, please click <a href=\"/register?action=resend&email="+request.getParameter("email")+"\">here</a>. If problem persists, please contact us directly.";
				request.setAttribute("successMessage", message);
				getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				return;
			} catch (MessagingException e) {
				setRegisterAttributes(request);
				request.setAttribute("message",
						e.getMessage());
				getServletContext().getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
				return;
			}
			
		}
		if (request.getParameter("verify")!=null) {
			String registrationID = request.getParameter("verify");
			try {
				UserManager.verifyEmail(registrationID);
				response.sendRedirect("/login?registered");
				return;
			} catch (Exception e) {
				setRegisterAttributes(request);
				request.setAttribute("message",
						e.getMessage());
				getServletContext().getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
				return;
			}
		}
		// Display Register page
		getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// validate and get all inputs
		// Grab user email
		String email = request.getParameter("email");
		// Grab user password
		String password = request.getParameter("password1");
		// Grab user password confirmation
		String passwordConf = request.getParameter("password2");
		// Grab user title
		String title = request.getParameter("title");
		// Grab user first name
		String fName = request.getParameter("f_name");
		// Grab user middle name
		String mName = request.getParameter("m_name");
		// Grab user last name
		String lName = request.getParameter("l_name");
		// Grab user phone number
		String phone = request.getParameter("phone");
		// Grab user fax
		String fax = request.getParameter("fax");
		// Grab user language
		String language = request.getParameter("language");
		// Grab user address 1
		String streetAddress = request.getParameter("address1");
		// Grab user address 2
		String streetAddress2 = request.getParameter("address2");
		// Grab user city
		String city = request.getParameter("addressCity");
		// Grab user province
		String province = request.getParameter("addressRegion");
		// Grab user country
		String country = request.getParameter("addressCountry");
		// Grab user postalCode
		String postalCode = request.getParameter("addressPostal");
		// Check if user already exists
		Boolean alreadyExists = UserManager.userExists(email);

		if (alreadyExists) {
			setRegisterAttributes(request);
			request.setAttribute("errorMessageEmail", "Email already in use");
			getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
			return;
		}

		Boolean created = false;
		try {
			created = UserManager.createUser(email, password, passwordConf, title, fName, mName, lName, phone, fax,
					language, streetAddress, streetAddress2, city, province, country, postalCode);
		} catch (MessagingException e) {
			setRegisterAttributes(request);
			request.setAttribute("message",
					e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
			//TODO
			e.printStackTrace();
			return;
		}catch (Exception e) {
			setRegisterAttributes(request);
			request.setAttribute("errorMessage",
					e.getMessage());
			//TODO
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
			return;
		}

		String message;
		if (created) {
			message = "Registration successful, please click the verification link sent to your email to continue. Remember to check your spam folder. If no email is sent, please click <a href=\"/register?action=resend&email="+email+"\">here</a>. If problem persists, please contact us directly.";
		} else {
			message = "Something went wrong with registration, please check your entered information and try again. If problem persists, please contact us directly";
		}
		request.setAttribute("successMessage", message);
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * Method to set the attributes for the request to repopulate the form in the
	 * case of an error
	 * 
	 * @param request The request having attributes set to it.
	 */
	private void setRegisterAttributes(HttpServletRequest request) {

		request.setAttribute("email", request.getAttribute("email"));
		request.setAttribute("password1", request.getParameter("password1"));
		request.setAttribute("password2", request.getParameter("password2"));
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("f_name", request.getParameter("f_name"));
		request.setAttribute("m_name", request.getParameter("m_name"));
		request.setAttribute("l_name", request.getParameter("l_name"));
		request.setAttribute("phone", request.getParameter("phone"));
		request.setAttribute("fax", request.getParameter("fax"));
		request.setAttribute("language", request.getParameter("language"));
		request.setAttribute("address1", request.getParameter("address1"));
		request.setAttribute("address2", request.getParameter("address2"));
		request.setAttribute("addressCity", request.getParameter("addressCity"));
		request.setAttribute("addressRegion", request.getParameter("addressRegion"));
		request.setAttribute("addressCountry", request.getParameter("addressCountry"));
		request.setAttribute("addressPostal", request.getParameter("addressPostal"));

	}
}
