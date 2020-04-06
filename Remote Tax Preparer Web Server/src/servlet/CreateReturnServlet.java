package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
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
 * @author Cesar Guzman, Taylor Hanlon
 */
@WebServlet("/createReturn")
public class CreateReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 2607698730769064715L;
	private static final Calendar DOBCutOff = new GregorianCalendar(2013, 0, 31);

	// Form fields
	private String dateOfBirth, sin, title, fName, initial, lName, gender, maritalStatus, prevMaritalStatus; // TODO

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

		// Display createReturn page
		getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get session email
		HttpSession session = request.getSession();
		String email = SessionManager.getEmail(session.getId());
		Debugger.log("Email: " + email);

		// get tax year
		String taxYearString = request.getParameter("taxYear");
		int taxYear = -1;

		if (taxYearString == null) {
			request.setAttribute("errorMessage", "Tax Year Invalid");
			Debugger.log("taxYearString == null");
			backfillForm(request);
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
			return;
		}

		try {
			taxYear = Integer.parseInt(taxYearString);
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "Tax Year Invalid");
			Debugger.log("taxYearString could not parse");
			backfillForm(request);
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
			return;
		}

		// check if they already have a return for selected year
		try {
			// if yes
			if (TaxReturnManager.hasReturnForYear(email, taxYear)) {
				// then let them know, backfill form, and go to create return page
				request.setAttribute("errorMessage", "You aready have an existing tax return for this year.");
				backfillForm(request);
				getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
				return;
			}
		} catch (ConfigException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error, please try again.");
			backfillForm(request);
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
			return;
		}
		// otherwise keep going

		// if all info ok
		try {
			validateForm(request);
		} catch (Exception e1) {
			e1.printStackTrace();
			request.setAttribute("errorMessage", e1.getMessage());
			backfillForm(request);
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
		}
		// send the user a parcel with the pdf for signing
		try {
			ArrayList<Document> documents = new ArrayList<Document>();
			documents.add(PDFService.createAuthorizationRequest(sin, fName, lName));

			ParcelManager.createNewReturnParcel(documents, fName, lName, email, taxYear);
		} catch (ConfigException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error, please try again.");
			backfillForm(request);
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error, please try again.");
			backfillForm(request);
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
		}

	}

	private boolean validateForm(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub

		// get all info from form
		String taxYear = request.getParameter("taxYear");
		String dateOfBirth = request.getParameter("dateOfBirth"); // returned in yyyy-mm-dd format from the jsp
		String SIN = request.getParameter("sin");
		String title = request.getParameter("title");
		String fName = request.getParameter("fname");
		String middleInitial = request.getParameter("initial");
		String lName = request.getParameter("lname");
		String gender = request.getParameter("gender");
		boolean nameChange = false;
		if (request.getParameter("nameChange") != null) {
			nameChange = true;
		}
		String maritalStatus = request.getParameter("maritalStatus");
		boolean maritalChange = false;
		if (request.getParameter("maritalChange") != null) {
			maritalChange = true;
		}
		String prevMaritalStatus = "";
		if (maritalChange) {
			prevMaritalStatus = request.getParameter("prevMaritalStatus");
		}
		boolean canadianCitizen = false;
		if (request.getParameter("canadianCitizen") != null) {
			canadianCitizen = true;
		}
		String address = request.getParameter("address");
		String apartment = request.getParameter("apartment");
		String poBox = request.getParameter("po");
		String poBoxLocation = request.getParameter("poLocation");
		String rrNum = request.getParameter("rr");
		String city = request.getParameter("addressCity");
		String region = request.getParameter("addressRegion");
		String country = request.getParameter("adressCountry");
		String postalCode = request.getParameter("addressPostal");

		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		String partnerDOB = "";
		String partnerSIN = "";
		String partnerTitle = "";
		String partnerFName = "";
		String partnerInitial = "";
		String partnerLName = "";
		String partnerGender = "";
		boolean partnerCanadianCitizen = false;
		String partnerAddress = "";
		String partnerApartment = "";
		String partnerPoBox = "";
		String partnerPoBoxLocation = "";
		String partnerRRNum = "";
		String partnerCity = "";
		String partnerCountry = "";
		String partnerRegion = "";
		String partnerPostalCode = "";
		String partnerEmailAddress = "";
		String partnerMobilePhone = "";
		if (maritalStatus.equals("Married") || maritalStatus.equals("Common Law")) {
			partnerDOB = request.getParameter("partnerDateOfBirth");
			partnerSIN = request.getParameter("partnerSin");
			partnerTitle = request.getParameter("partnerTitle");
			partnerFName = request.getParameter("partnerFname");
			partnerInitial = request.getParameter("partnerInitial");
			partnerLName = request.getParameter("partnerLname");
			partnerGender = request.getParameter("partnerGender");
			if (request.getParameter("partnerCanadianCitizen") != null) {
				partnerCanadianCitizen = true;
			}
			partnerAddress = request.getParameter("partnerAddress");
			partnerApartment = request.getParameter("partnerApartment");
			partnerPoBox = request.getParameter("partnerPo");
			partnerPoBoxLocation = request.getParameter("partnerPoLocation");
			partnerRRNum = request.getParameter("partnerrr");
			partnerCity = request.getParameter("partnerAddressCity");
			partnerCountry = request.getParameter("partnerAddressCountry");
			partnerRegion = request.getParameter("partnerAddressRegion");
			partnerPostalCode = request.getParameter("partnerAddressPostal");
			partnerEmailAddress = request.getParameter("partnerEmail");
			partnerMobilePhone = request.getParameter("partnerPhone");
		}
		boolean electionsCanada = false;
		if (request.getParameter("electionsCanada") != null) {
			electionsCanada = true;
		}
		boolean foriegnProperty = false;
		if (request.getParameter("foreignProperty") != null) {
			foriegnProperty = true;
		}
		boolean soldHome = false;
		if (request.getParameter("sellHome") != null) {
			soldHome = true;
		}
		boolean firstTime = false;
		if (request.getParameter("firstTime") != null) {
			firstTime = true;
		}
		// Notice of Assessment
		boolean canadaPost = false;
		if (request.getParameter("mailAssess") != null) {
			canadaPost = true;
		}
		boolean CRAOnline = false;
		if (request.getParameter("craAssess") != null) {
			CRAOnline = true;
		}
		boolean alreadyRegistered = false;
		if (request.getParameter("alreadyRegistered") != null) {
			alreadyRegistered = true;
		}

		// make sure info isn't bad
		// dateOfBirth (Date?)

		try {
			Date newDOB = new SimpleDateFormat("yyyy-mm-dd").parse(dateOfBirth);
			Calendar DOBCal = Calendar.getInstance();
			DOBCal.setTime(newDOB);
			if (DOBCal.before(DOBCutOff) || DOBCal.after(Calendar.getInstance())) {
				// TODO
				Debugger.log("Date range if statement");
				throw new Exception("Invalid date of birth, please try again");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Debugger.log("Date parse validation error");
			e.printStackTrace();
			throw new Exception("Invalid date of birth, please try again");
		}

		// SIN
		if (SIN == null || SIN.length() > 12 || SIN.length() == 0) {
			// TODO
			Debugger.log("SIN if statement");
			throw new Exception("Invalid SIN, please try again");
		}

		// title (Mr, Miss, Mrs, Ms)
		if (!title.equals("Mr") && !title.equals("Miss") && !title.equals("Mrs") && !title.equals("Ms")) {
			// TODO
			Debugger.log("title validation if statement");
			throw new Exception("Invalid title, please try again");
		}
		// fName, initial(opt), lName
		if (fName == null || fName.length() > 25 || fName.length() == 0) {
			// TODO
			Debugger.log("fname validation if statement");
			throw new Exception("Invalid First Name, please try again");
		}
		if (middleInitial.length() > 1) {
			// TODO
			Debugger.log("middle initial validation if statement");
			throw new Exception("Invalid Middle Initial, please try again");
		}
		if (lName == null || lName.length() > 25 || lName.length() == 0) {
			// TODO
			Debugger.log("lname validation if statement");
			throw new Exception("Invalid Last Name, please try again");
		}
		// gender (M/F)
		if (!gender.equals("f") && !gender.equals("m") && !gender.equals("x")) {
			// TODO
			Debugger.log("gender validation if statement");
			throw new Exception("Invalid Gender, please try again");
		}
		// maritalStatus (Married, Common Law, Widowed, Divorced, Separated, Single)
		if (!maritalStatus.equals("Married") && !maritalStatus.equals("Common Law") && !maritalStatus.equals("Widowed")
				&& !maritalStatus.equals("Divorced") && !maritalStatus.equals("Seperated")
				&& !maritalStatus.equals("Single")) {
			// TODO
			Debugger.log("maritalStatus validation if statement");
			throw new Exception("Invalid Marital Status, please try again");
		}
		// prevMaritalStatus (opt / same options)
		if (prevMaritalStatus != null) {
			if (!prevMaritalStatus.equals("Married") && !prevMaritalStatus.equals("Common Law")
					&& !prevMaritalStatus.equals("Widowed") && !prevMaritalStatus.equals("Divorced")
					&& !prevMaritalStatus.equals("Seperated") && !prevMaritalStatus.equals("Single")) {
				// TODO
				Debugger.log("prevMaritalStatus validation if statement");
				throw new Exception("Invalid Previous Marital Status, please try again");
			}
		}
		// lastNameChange (Y/N) ??
		// citizen (Y/N) checkbox
		// address (Street, apartment, po box, po box location, RR#, City,
		// Privince/territory, Postal code, SOME ARE OPTIONAL)
		if (address == null || address.length() > 200 || address.length() == 0) {
			// TODO
			Debugger.log("address validation if statement");
			throw new Exception("Invalid Address, please try again");
		}
		if (apartment.length() > 200) {
			// TODO
			Debugger.log("apartment validation if statement");
			throw new Exception("Invalid Apartment, please try again");
		}
		if (poBox.length() > 20) {
			// TODO
			Debugger.log("pobox validation if statement");
			throw new Exception("Invalid PoBox, please try again");
		}
		if (poBoxLocation.length() > 200) {
			// TODO
			Debugger.log("poboxlocation validation if statement");
			throw new Exception("Invalid PoBoxLocation, please try again");
		}
		if (rrNum.length() > 20) {
			// TODO
			Debugger.log("rr validation if statement");
			throw new Exception("Invalid RR, please try again");
		}
		if (city == null || city.length() > 200 || city.length() == 0) {
			// TODO
			Debugger.log("city validation if statement");
			throw new Exception("Invalid City, please try again");
		}
		if (region == null || region.length() > 200 || region.length() == 0) {
			// TODO
			Debugger.log("region validation if statement");
			throw new Exception("Invalid Region, please try again");
		}
		if (postalCode == null || postalCode.length() > 10 || postalCode.length() == 0) {
			// TODO
			Debugger.log("postalcode validation if statement");
			throw new Exception("Invalid PostalCode, please try again");
		}
		// email (we have this)
		if (email == null || email.length() > 200 || email.length() == 0) {
			// TODO
			Debugger.log("email validation if statement");
			throw new Exception("Invalid Email, please try again");
		}
		// phone
		if (phone == null || phone.length() > 15 || phone.length() == 0) {
			// TODO
			Debugger.log("phone validation if statement");
			throw new Exception("Invalid Phone, please try again");
		}

		// if married | commonLaw
		if (maritalStatus.equals("Married") || maritalStatus.equals("Common Law")) {
			// DOB
			try {
				Date newPartnerDOB = new SimpleDateFormat("yyyy-mm-dd").parse(partnerDOB);
				Calendar PartnerDOBCal = Calendar.getInstance();
				PartnerDOBCal.setTime(newPartnerDOB);
				if (PartnerDOBCal.before(DOBCutOff) || PartnerDOBCal.after(Calendar.getInstance())) {
					// TODO
					Debugger.log("Partner Date range if statement");
					throw new Exception("Invalid partner date of birth, please try again");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Debugger.log("Partner Date parse validation error");
				e.printStackTrace();
				throw new Exception("Invalid partner date of birth, please try again");
			}
			if (partnerSIN == null || partnerSIN.length() > 12 || partnerSIN.length() == 0) {
				// TODO
				Debugger.log("partner SIN if statement");
				throw new Exception("Invalid partner SIN, please try again");
			}

			// title (Mr, Miss, Mrs, Ms)
			if (!partnerTitle.equals("Mr") && !partnerTitle.equals("Miss") && !partnerTitle.equals("Mrs")
					&& !partnerTitle.equals("Ms")) {
				// TODO
				Debugger.log("partner title validation if statement");
				throw new Exception("Invalid partner title, please try again");
			}
			// fName, initial(opt), lName
			if (partnerFName == null || partnerFName.length() > 25 || partnerFName.length() == 0) {
				// TODO
				Debugger.log("partner fname validation if statement");
				throw new Exception("Invalid partner First Name, please try again");
			}
			if (partnerInitial.length() > 1) {
				// TODO
				Debugger.log("partner middle initial validation if statement");
				throw new Exception("Invalid partner Middle Initial, please try again");
			}
			if (partnerLName == null || partnerLName.length() > 25 || partnerLName.length() == 0) {
				// TODO
				Debugger.log("partner lname validation if statement");
				throw new Exception("Invalid partner Last Name, please try again");
			}
			// gender (M/F)
			if (!partnerGender.equals("f") && !partnerGender.equals("m") && !partnerGender.equals("x")) {
				// TODO
				Debugger.log("partner gender validation if statement");
				throw new Exception("Invalid partner Gender, please try again");
			}

			if (partnerAddress == null || partnerAddress.length() > 200 || partnerAddress.length() == 0) {
				// TODO
				Debugger.log("partner address validation if statement");
				throw new Exception("Invalid partner Address, please try again");
			}
			if (partnerApartment.length() > 200) {
				// TODO
				Debugger.log("partner apartment validation if statement");
				throw new Exception("Invalid partner Apartment, please try again");
			}
			if (partnerPoBox.length() > 20) {
				// TODO
				Debugger.log("partner pobox validation if statement");
				throw new Exception("Invalid partner PoBox, please try again");
			}
			if (partnerPoBoxLocation.length() > 200) {
				// TODO
				Debugger.log("partner poboxlocation validation if statement");
				throw new Exception("Invalid partner PoBoxLocation, please try again");
			}
			if (partnerRRNum.length() > 20) {
				// TODO
				Debugger.log("partner rr validation if statement");
				throw new Exception("Invalid partner RR, please try again");
			}
			if (partnerCity == null || partnerCity.length() > 200 || partnerCity.length() == 0) {
				// TODO
				Debugger.log("partner city validation if statement");
				throw new Exception("Invalid partner City, please try again");
			}
			if (partnerRegion == null || partnerRegion.length() > 200 || partnerRegion.length() == 0) {
				// TODO
				Debugger.log("partner region validation if statement");
				throw new Exception("Invalid partner Region, please try again");
			}
			if (partnerPostalCode == null || partnerPostalCode.length() > 10 || partnerPostalCode.length() == 0) {
				// TODO
				Debugger.log("partner postalcode validation if statement");
				throw new Exception("Invalid partner PostalCode, please try again");
			}
			// email
			if (partnerEmailAddress == null || partnerEmailAddress.length() > 200
					|| partnerEmailAddress.length() == 0) {
				// TODO
				Debugger.log("partner email validation if statement");
				throw new Exception("Invalid partner Email, please try again");
			}
			// phone
			if (partnerMobilePhone == null || partnerMobilePhone.length() > 15 || partnerMobilePhone.length() == 0) {
				// TODO
				Debugger.log("partner phone validation if statement");
				throw new Exception("Invalid partner Phone, please try again");
			}
		}
		// electionsCanada (Y/N) checkbox
		// foreignProp (Y/N) checkbox
		// sellHome (Y/N) checkbox
		// firstTimeFiler (Y/N) checkbox
		/*
		 * How do you want to recieve your Notice of Assessment? Mail (Canada Post)
		 * AND/OR Register with Canada Revenue agency for online mail already registered
		 */
		if (canadaPost != true && CRAOnline != true) {
			// TODO
			Debugger.log("notice of assessment validation if statement");
			throw new Exception(
					"Invalid Notice of Assessment, please choose at least one of Canada Post or CRA Online");
		}
		// additionalInfo (String)

		return true;
	}

	private void backfillForm(HttpServletRequest request) {
		request.setAttribute("taxYear",request.getParameter("taxYear"));
		request.setAttribute("dateOfBirth", request.getParameter("dateOfBirth")); // returned in yyyy-mm-dd format from the jsp
		request.setAttribute("sin", request.getParameter("sin"));
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("fname", request.getParameter("fname"));
		request.setAttribute("initial", request.getParameter("initial"));
		request.setAttribute("lname", request.getParameter("lname"));
		request.setAttribute("gender", request.getParameter("gender"));
		boolean nameChange = false;
		if (request.getParameter("nameChange") != null) {
			nameChange = true;
		}
		request.setAttribute("nameChange", nameChange);
		request.setAttribute("Marital Status", request.getParameter("maritalStatus"));
		boolean maritalChange = false;
		if (request.getParameter("maritalChange") != null) {
			maritalChange = true;
		}
		request.setAttribute("maritalChange", maritalChange);
		request.setAttribute("prevMaritalStatus", request.getParameter("prevMaritalStatus"));
		boolean canadianCitizen = false;
		if (request.getParameter("canadianCitizen") != null) {
			canadianCitizen = true;
		}
		request.setAttribute("canadianCitizen", canadianCitizen);
		request.setAttribute("address",request.getParameter("address"));
		request.setAttribute("apartment",request.getParameter("apartment"));
		request.setAttribute("po",request.getParameter("po"));
		request.setAttribute("poLocation",request.getParameter("poLocation"));
		request.setAttribute("rr", request.getParameter("rr"));
		request.setAttribute("addressCity",request.getParameter("addressCity"));
		request.setAttribute("addressRegion", request.getParameter("addressRegion"));
		request.setAttribute("addressCountry", request.getParameter("adressCountry"));
		request.setAttribute("addressPostal", request.getParameter("addressPostal"));

		request.setAttribute("email",request.getParameter("email"));
		request.setAttribute("phone",request.getParameter("phone"));
		
		//partner fields
		request.setAttribute("partnerDateOfBirth", request.getParameter("partnerDateOfBirth"));
		request.setAttribute("partnerSin", request.getParameter("partnerSin"));
		request.setAttribute("partnerTitle", request.getParameter("partnerTitle"));
		request.setAttribute("partnerFname", request.getParameter("partnerFname"));
		request.setAttribute("partnerInitial", request.getParameter("partnerInitial"));
		request.setAttribute("partnerLname", request.getParameter("partnerLname"));
		request.setAttribute("partnerGender", request.getParameter("partnerGender"));
		boolean partnerCanadianCitizen = false;
		if (request.getParameter("partnerCanadianCitizen") != null) {
			partnerCanadianCitizen = true;
		}
		request.setAttribute("partnerCanadianCitizen", partnerCanadianCitizen);
		request.setAttribute("partnerAddress", request.getParameter("partnerAddress"));
		request.setAttribute("partnerApartment", request.getParameter("partnerApartment"));
		request.setAttribute("partnerPo", request.getParameter("partnerPo"));
		request.setAttribute("partnerPoLocation", request.getParameter("partnerPoLocation"));
		request.setAttribute("partnerrr", request.getParameter("partnerrr"));
		request.setAttribute("partnerAddressCity", request.getParameter("partnerAddressCity"));
		request.setAttribute("partnerAddressCountry", request.getParameter("partnerAddressCountry"));
		request.setAttribute("partnerAddressRegion", request.getParameter("partnerAddressRegion"));
		request.setAttribute("partnerAddressPostal", request.getParameter("partnerAddressPostal"));
		request.setAttribute("partnerEmail", request.getParameter("partnerEmail"));
		request.setAttribute("partnerPhone", request.getParameter("partnerPhone"));
		boolean electionsCanada = false;
		if (request.getParameter("electionsCanada") != null) {
			electionsCanada = true;
		}
		request.setAttribute("electionsCanada", partnerCanadianCitizen);
		boolean foriegnProperty = false;
		if (request.getParameter("foreignProperty") != null) {
			foriegnProperty = true;
		}
		boolean soldHome = false;
		if (request.getParameter("sellHome") != null) {
			soldHome = true;
		}
		boolean firstTime = false;
		if (request.getParameter("firstTime") != null) {
			firstTime = true;
		}
		// Notice of Assessment
		boolean canadaPost = false;
		if (request.getParameter("mailAssess") != null) {
			canadaPost = true;
		}
		boolean CRAOnline = false;
		if (request.getParameter("craAssess") != null) {
			CRAOnline = true;
		}
		boolean alreadyRegistered = false;
		if (request.getParameter("alreadyRegistered") != null) {
			alreadyRegistered = true;
		}
	}
}
