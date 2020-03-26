package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.SessionManager;
import util.cesar.Debugger;

/**
 * Servlet implementation class CreateReturnServlet
 */
@WebServlet("/CreateReturnServlet")
public class CreateReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 2607698730769064715L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateReturnServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("CreateReturnServlet.doGet");

		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		//Display createReturn page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//check if they already have a return for selected year
		//if yes then let them know and backfill form
		//if no then keep going

		//get all info from form
		//make sure info isn't bad
			// dateOfBirth (Date?)
			// title (Mr, Miss, Mrs, Ms)
			// fName, initial(opt), lName
			// gender (M/F)
			// maritalStatus (Married, Common Law, Widowed, Divorced, Separated, Single)
			// prevMaritalStatus (opt / same options)
			// lastNameChange (Y/N)
			// citizen (Y/N)
			// address (Street, apartment, po box, po box location, RR#, City, Privince/territory, Postal code, SOME ARE OPTIONAL)
			// email (we have this)
			// phone
			// if married | commonLaw
				/* all for partner
				SIN
				DOB
				
				Mr, Miss, Mrs, Ms
				
				First Name, Initial, Last Name
				
				Male/Female
				
				canadian citizen Y/N
				
				Address - Street, apartment, po box, po box location, RR#, City, Privince/territory, Postal code
				
				Email address
				
				Mobile phone
				 */
			// electionsCanada (Y/N)
			// foreignProp (Y/N)
			// sellHome (Y/N)
			// firstTimeFiler (Y/N)
			/*
			 * How do you want to recieve your Notice of Assessment?
				Mail (Canada Post)
					AND/OR
					Register with Canada Revenue agency for online mail
					already registered
			 */
			// additionalInfo (String)
		
		// if all info ok, show them filled "Authorization/Cancellation request – signature page" with signature pad thing
		// confirm signature
		// create a parcel with all info nice and pretty
		
		//let them know they did it

		//Display createReturn page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
	}

}
