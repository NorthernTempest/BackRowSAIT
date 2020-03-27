package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;

import exception.ConfigException;
import manager.SessionManager;
import manager.TaxReturnManager;
import service.PDFService;
import util.cesar.Debugger;

/**
 * Servlet implementation class CreateReturnServlet
 * 
 * @author Cesar Guzman
 */
@WebServlet("/createReturn")
public class CreateReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 2607698730769064715L;
	
	//Form fields
	private String dateOfBirth, sin, title, fName, initial, lName, gender, maritalStatus, prevMaritalStatus; //TODO

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateReturnServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Debugger.log("CreateReturnServlet.doGet");

		//Display createReturn page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);
		
		String action = request.getParameter("action");
		
		// is this a form post or a signature post? 
		// if form:
		if (action.equals("form")) {
			//get tax year
			String taxYearString = request.getParameter("taxYear");
			int taxYear = -1;
			
			if(taxYearString == null) {
				request.setAttribute("errorMessage", "Tax Year Invalid");
				Debugger.log("taxYearString == null");
				backfillForm();
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
				return;
			}
			
			try {
				taxYear = Integer.parseInt(taxYearString);
			} catch(NumberFormatException e) {
				request.setAttribute("errorMessage", "Tax Year Invalid");
				Debugger.log("taxYearString could not parse");
				backfillForm();
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
				return;
			}
			
			//check if they already have a return for selected year
			try {
				//if yes 
				if(TaxReturnManager.hasReturnForYear(email, taxYear)) {
					// then let them know and backfill form
					// go to create return page
					request.setAttribute("errorMessage", "You aready have an existing tax return for this year.");
					backfillForm();
					getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
					return;
				}
			} catch (ConfigException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", "Error, please try again.");
				backfillForm();
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
				return;
			}
			//otherwise keep going	
			
			// if all info ok
			if(validateForm(request)) {
				// show them filled "Authorization/Cancellation request – signature page" with signature pad thing
				try {
					request.setAttribute("authReqPDF", PDFService.createAuthorizationRequest(sin, fName, lName));
				} catch (ConfigException e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", "Error, please try again.");
					backfillForm();
					getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
				} catch (IOException e) {
					e.printStackTrace();
					request.setAttribute("errorMessage", "Error, please try again.");
					backfillForm();
					getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
				}
				
			} else {
				// let them know something is wrong (error message set in validateForm())
				backfillForm();
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
				return;
			}
			
		} else if(action.equals("signature")) {
			// if signature post
				
				// confirm signature
				// if form is signed
					// put the signature on the pdf
					// create a parcel with all info nice and pretty
					// let them know they did it
					// Display createReturn page
				// else 
					// let them know they need to sign the page for us to begin doing their taxes
					// backfill form
					// go to create return page
		}
	}

	private boolean validateForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
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
		dateOfBirth = request.getParameter("dateOfBirth"); //TODO
		if(dateOfBirth == null) {
			request.setAttribute("errorMessage", "Date Of Birth Invalid");
			Debugger.log("dateOfBirth == null");
			backfillForm();
			return false;
		}
		
		return true;
	}

	private void backfillForm() {
		// TODO Auto-generated method stub
		// guess what we do here
	}
}
