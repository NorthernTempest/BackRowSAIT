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
 * Servlet implementation class NewAccount
 */
@WebServlet("/newAccount")
public class NewAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAccount() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/newaccount.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		int role = Integer.parseInt(request.getParameter("role"));
		String password = request.getParameter("password1");
		String confirmPassword = request.getParameter("password2");
		String title = request.getParameter("title");
		String fName = request.getParameter("fName");
		String lName = request.getParameter("lName");
		String phone = request.getParameter("phone");
		String language = request.getParameter("language");
		
		if (UserManager.userExists(email)) {
			setRegisterAttributes(request);
			request.setAttribute("errorMessageEmail", "Email already in use");
			getServletContext().getRequestDispatcher("/WEB-INF/newaccount.jsp").forward(request, response);
			return;
		}
		
		Boolean created = false;
		try {
			created = UserManager.createSpecialUser(role, email, password, confirmPassword, title, fName, lName, phone, language);
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
			getServletContext().getRequestDispatcher("/WEB-INF/newaccount.jsp").forward(request, response);
			return;
		}

		String message;
		if (created) {
			message = "Account created";
		} else {
			message = "Error occurred during account creation";
		}
		request.setAttribute("successMessage", message);
		getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	}

	/**
	 * Method to set the attributes for the request to repopulate the form in the
	 * case of an error
	 * 
	 * @param request The request having attributes set to it.
	 */
	private void setRegisterAttributes(HttpServletRequest request) {

		request.setAttribute("role", request.getAttribute("role"));
		request.setAttribute("email", request.getAttribute("email"));
		request.setAttribute("password1", request.getParameter("password1"));
		request.setAttribute("password2", request.getParameter("password2"));
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("fName", request.getParameter("fName"));
		request.setAttribute("lName", request.getParameter("lName"));
		request.setAttribute("phone", request.getParameter("phone"));
		request.setAttribute("language", request.getParameter("language"));

	}
}
