package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import domain.Document;
import exception.ConfigException;
import manager.ParcelManager;
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
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 1024 * 1024 * 1, maxRequestSize = 1024 * 1024 * 1 * 2) //0mb, 25mb, 10x 25mb
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

		//get tax year
		String taxYearString = request.getParameter("taxYear");
		int taxYear = -1;

		if (taxYearString == null) {
			request.setAttribute("errorMessage", "Tax Year Invalid");
			Debugger.log("taxYearString == null");
			backfillForm();
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
			return;
		}

		try {
			taxYear = Integer.parseInt(taxYearString);
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "Tax Year Invalid");
			Debugger.log("taxYearString could not parse");
			backfillForm();
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
			return;
		}

		//check if they already have a return for selected year
		try {
			//if yes 
			if (TaxReturnManager.hasReturnForYear(email, taxYear)) {
				// then let them know, backfill form, and go to create return page
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
		if (validateForm(request)) {
			// send the user a parcel with the pdf for signing
			try {
				ArrayList<Document> documents = new ArrayList<Document>();
				documents.add(PDFService.createAuthorizationRequest(sin, fName, lName));

				ParcelManager.createNewReturnParcel(documents, fName, lName, email, taxYear);
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
			// info not valid, let them know something is wrong (error message set in validateForm())
			backfillForm();
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
			return;
		}
	}

	private boolean validateForm(HttpServletRequest request) {
		// TODO Auto-generated method stub

		//get all info from form
		String taxYear = request.getParameter("taxYear");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String SIN = request.getParameter("sin");
		String title = request.getParameter("title");
		String fName = request.getParameter("fname");
		String middleInitial = request.getParameter("initial");
		String lName = request.getParameter("lname");
		String gender = request.getParameter("gender");
		boolean nameChange = false;
		if (request.getParameter("nameChange")!=null) {
			nameChange = true;
		}
		String maritalStatus = request.getParameter("maritalStatus");
		boolean maritalChange = false;
		if (request.getParameter("maritalChange")!=null) {
			maritalChange = true;
		}
		String prevMaritalStatus="";
		if (maritalChange) {
			prevMaritalStatus = request.getParameter("prevMaritalStatus");
		}
		boolean canadianCitizen = false;
		if (request.getParameter("canadianCitizen")!=null) {
			canadianCitizen = true;
		}
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String poBox = request.getParameter("poBox");
		String poBoxLocation = request.getParameter("poBoxLocation");
		String rrNum = request.getParameter("rr#");
		String city = request.getParameter("city");
		String region = request.getParameter("region");
		String postalCode = request.getParameter("postalCode");
		
		//TODO emailAddress/MobilePhone? Do we get SIN for user elsewhere for the user
		
		String partnerSIN="";
		String partnerDOB="";
		String partnerTitle="";
		String partnerFName="";
		String partnerInitial="";
		String partnerLName="";
		String partnerGender="";
		boolean partnerCanadianCitizen=false;
		String partnerStreetNum="";
		String partnerApartmentNum="";
		String partnerPoBox="";
		String partnerPoBoxLocation="";
		String partnerRRNum="";
		String partnerCity="";
		String partnerRegion="";
		String partnerPostalCode="";
		String partnerEmailAddress="";
		String partnerMobilePhone="";
		if (maritalStatus.equals("MARRIED")||maritalStatus.equals("COMMON LAW")) {
			partnerSIN = request.getParameter("partnerSIN");
			partnerDOB = request.getParameter("partnerDOB");
			partnerTitle = request.getParameter("partnerTitle");
			partnerFName = request.getParameter("partnerFName");
			partnerInitial = request.getParameter("partnerInitial");
			partnerLName = request.getParameter("partnerLName");
			partnerGender = request.getParameter("partnerGender");
			if (request.getParameter("partnerCanadianCitizen")!=null) {
				partnerCanadianCitizen=true;
			}
			partnerStreetNum = request.getParameter("partnerStreetNum");
			partnerApartmentNum = request.getParameter("partnerApartmentNum");
			partnerPoBox = request.getParameter("partnerPoBox");
			partnerPoBoxLocation = request.getParameter("partnerPoBoxLocation");
			partnerRRNum = request.getParameter("partnerRRNum");
			partnerCity = request.getParameter("partnerCity");
			partnerRegion = request.getParameter("partnerRegion");
			partnerPostalCode = request.getParameter("partnerPostalCode");
			partnerEmailAddress = request.getParameter("partnerEmailAddress");
			partnerMobilePhone = request.getParameter("partnerMobilePhone");
		}
		boolean electionsCanada = false;
		if (request.getParameter("electionsCanada")!=null) {
			electionsCanada = true;
		}
		boolean foriegnProperty = false;
		if (request.getParameter("foreignProperty")!=null) {
			foriegnProperty = true;
		}
		boolean soldHome = false;
		if (request.getParameter("soldHome")!=null) {
			soldHome = true;
		}
		boolean firstTime = false;
		if (request.getParameter("firstTime")!=null) {
			firstTime = true;
		}
		//Notice of Assessment
		boolean snailMail = false;
		if (request.getParameter("snailMail")!=null) {
			snailMail = true;
		}
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
		if (dateOfBirth == null) {
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
