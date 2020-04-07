package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Document;
import domain.NewReturnForm;
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
	private static final Calendar DOBCutOff = new GregorianCalendar(1900, 01, 01);

	// Form fields
	//private String taxYear, dateOfBirth, sin, title, fName, middleInitial, lName, gender, maritalStatus, prevMaritalStatus, address, apartment, poBox, poBoxLocation, rrNum, city, region, country, postalCode, email, phone, partnerDOB, partnerSIN, partnerTitle, partnerFName, partnerInitial, partnerLName, partnerGender, partnerAddress, partnerApartment, partnerPoBox, partnerPoBoxLocation, partnerRRNum, partnerCity, partnerCountry, partnerRegion, partnerPostalCode, partnerEmailAddress, partnerMobilePhone; // TODO
	//private boolean nameChange, maritalChange, canadianCitizen, partnerCanadianCitizen, electionsCanada, foriegnProperty, soldHome, firstTime, canadaPost, CRAOnline, alreadyRegistered;
	NewReturnForm nrf = new NewReturnForm();
	
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
			
			ArrayList<Document> documents = new ArrayList<Document>();
			documents.add(PDFService.createAuthorizationRequest(nrf.getSin(), nrf.getfName(), nrf.getlName()));

			//TODO
			if(TaxReturnManager.createNewTaxReturn(email, taxYear)) {
				Debugger.log("we made a tax return probably");
				if(ParcelManager.createNewReturnParcels(documents, nrf, email)) {
					Debugger.log("we made a new return Parcel probably");
				} else {
					//very bad
					Debugger.log("bad no parcel make ahhh");
				}
			} else {
				//bad
				Debugger.log("no tax return :( oh no bad");
			}
			
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/inbox.jsp").forward(request, response);
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
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			backfillForm(request);
			getServletContext().getRequestDispatcher("/WEB-INF/parcel/createReturn.jsp").forward(request, response);
		}

	}

	private boolean validateForm(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub

		// get all info from form
		nrf.setTaxYear(request.getParameter("taxYear"));
		nrf.setDateOfBirth(request.getParameter("dateOfBirth")); // returned in yyyy-mm-dd format from the jsp
		nrf.setSin(request.getParameter("sin"));
		nrf.setTitle(request.getParameter("title"));
		nrf.setfName(request.getParameter("fname"));
		nrf.setMiddleInitial(request.getParameter("initial"));
		nrf.setlName(request.getParameter("lname"));
		nrf.setGender(request.getParameter("gender"));
		nrf.setNameChange(false);
		if (request.getParameter("nameChange") != null) {
			nrf.setNameChange(true);
		}
		nrf.setMaritalStatus(request.getParameter("maritalStatus"));
		nrf.setMaritalChange(false);
		if (request.getParameter("maritalChange") != null) {
			nrf.setMaritalChange(true);
		}
		nrf.setPrevMaritalStatus("");
		if (nrf.isMaritalChange()) {
			nrf.setPrevMaritalStatus(request.getParameter("prevMaritalStatus"));
		}
		nrf.setCanadianCitizen(false);
		if (request.getParameter("canadianCitizen") != null) {
			nrf.setCanadianCitizen(true);
		}
		nrf.setAddress(request.getParameter("address"));
		nrf.setApartment(request.getParameter("apartment"));
		nrf.setPoBox(request.getParameter("po"));
		nrf.setPoBoxLocation(request.getParameter("poLocation"));
		nrf.setRrNum(request.getParameter("rr"));
		nrf.setCity(request.getParameter("addressCity"));
		nrf.setRegion(request.getParameter("addressRegion"));
		nrf.setCountry(request.getParameter("adressCountry"));
		nrf.setPostalCode(request.getParameter("addressPostal"));

		nrf.setEmail(request.getParameter("email"));
		nrf.setPhone(request.getParameter("phone"));

		nrf.setPartnerDOB("");
		nrf.setPartnerSIN("");
		nrf.setPartnerTitle("");
		nrf.setPartnerFName("");
		nrf.setPartnerInitial("");
		nrf.setPartnerLName("");
		nrf.setPartnerGender("");
		nrf.setPartnerCanadianCitizen(false);
		nrf.setPartnerAddress("");
		nrf.setPartnerApartment("");
		nrf.setPartnerPoBox("");
		nrf.setPartnerPoBoxLocation("");
		nrf.setPartnerRRNum("");
		nrf.setPartnerCity("");
		nrf.setPartnerCountry("");
		nrf.setPartnerRegion("");
		nrf.setPartnerPostalCode("");
		nrf.setPartnerEmailAddress("");
		nrf.setPartnerMobilePhone("");
		if (nrf.getMaritalStatus().equals("Married") || nrf.getMaritalStatus().equals("Common Law")) {
			nrf.setPartnerDOB(request.getParameter("partnerDateOfBirth"));
			nrf.setPartnerSIN(request.getParameter("partnerSin"));
			nrf.setPartnerTitle(request.getParameter("partnerTitle"));
			nrf.setPartnerFName(request.getParameter("partnerFname"));
			nrf.setPartnerInitial(request.getParameter("partnerInitial"));
			nrf.setPartnerLName(request.getParameter("partnerLname"));
			nrf.setPartnerGender(request.getParameter("partnerGender"));
			if (request.getParameter("partnerCanadianCitizen") != null) {
				nrf.setPartnerCanadianCitizen(true);
			}
			nrf.setPartnerAddress(request.getParameter("partnerAddress"));
			nrf.setPartnerApartment(request.getParameter("partnerApartment"));
			nrf.setPartnerPoBox(request.getParameter("partnerPo"));
			nrf.setPartnerPoBoxLocation(request.getParameter("partnerPoLocation"));
			nrf.setPartnerRRNum(request.getParameter("partnerrr"));
			nrf.setPartnerCity(request.getParameter("partnerAddressCity"));
			nrf.setPartnerCountry(request.getParameter("partnerAddressCountry"));
			nrf.setPartnerRegion(request.getParameter("partnerAddressRegion"));
			nrf.setPartnerPostalCode(request.getParameter("partnerAddressPostal"));
			nrf.setPartnerEmailAddress(request.getParameter("partnerEmail"));
			nrf.setPartnerMobilePhone(request.getParameter("partnerPhone"));
		}
		nrf.setElectionsCanada(false);
		if (request.getParameter("electionsCanada") != null) {
			nrf.setElectionsCanada(true);
		}
		nrf.setForiegnProperty(false);
		if (request.getParameter("foreignProperty") != null) {
			nrf.setForiegnProperty(true);
		}
		nrf.setSoldHome(false);
		if (request.getParameter("sellHome") != null) {
			nrf.setSoldHome(true);
		}
		nrf.setFirstTime(false);
		if (request.getParameter("firstTime") != null) {
			nrf.setFirstTime(true);
		}
		// Notice of Assessment
		nrf.setCanadaPost(false);
		if (request.getParameter("mailAssess") != null) {
			nrf.setCanadaPost(true);
		}
		nrf.setCRAOnline(false);
		if (request.getParameter("craAssess") != null) {
			nrf.setCRAOnline(true);
		}
		nrf.setAlreadyRegistered(false);
		if (request.getParameter("alreadyRegistered") != null) {
			nrf.setAlreadyRegistered(true);
		}

		// make sure info isn't bad
		// dateOfBirth (Date?)

		try {
			Date newDOB = new SimpleDateFormat("yyyy-mm-dd").parse(nrf.getDateOfBirth());
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
		if (nrf.getSin() == null || nrf.getSin().length() > 12 || nrf.getSin().length() == 0) {
			// TODO
			Debugger.log("SIN if statement");
			throw new Exception("Invalid SIN, please try again");
		}

		// title (Mr, Miss, Mrs, Ms)
		if (!nrf.getTitle().equals("Mr") && !nrf.getTitle().equals("Miss") && !nrf.getTitle().equals("Mrs") && !nrf.getTitle().equals("Ms")) {
			// TODO
			Debugger.log("title validation if statement");
			throw new Exception("Invalid title, please try again");
		}
		// fName, initial(opt), lName
		if (nrf.getfName() == null || nrf.getfName().length() > 25 || nrf.getfName().length() == 0) {
			// TODO
			Debugger.log("fname validation if statement");
			throw new Exception("Invalid First Name, please try again");
		}
		if (nrf.getMiddleInitial().length() > 1) {
			// TODO
			Debugger.log("middle initial validation if statement");
			throw new Exception("Invalid Middle Initial, please try again");
		}
		if (nrf.getlName() == null || nrf.getlName().length() > 25 || nrf.getlName().length() == 0) {
			// TODO
			Debugger.log("lname validation if statement");
			throw new Exception("Invalid Last Name, please try again");
		}
		// gender (M/F)
		if (!nrf.getGender().equals("f") && !nrf.getGender().equals("m") && !nrf.getGender().equals("x")) {
			// TODO
			Debugger.log("gender validation if statement");
			throw new Exception("Invalid Gender, please try again");
		}
		// maritalStatus (Married, Common Law, Widowed, Divorced, Separated, Single)
		if (!nrf.getMaritalStatus().equals("Married") && !nrf.getMaritalStatus().equals("Common Law") && !nrf.getMaritalStatus().equals("Widowed")
				&& !nrf.getMaritalStatus().equals("Divorced") && !nrf.getMaritalStatus().equals("Separated")
				&& !nrf.getMaritalStatus().equals("Single")) {
			// TODO
			Debugger.log("maritalStatus validation if statement");
			throw new Exception("Invalid Marital Status, please try again");
		}
		// prevMaritalStatus (opt / same options)
		if (nrf.isMaritalChange() != false) {
			if (!nrf.getPrevMaritalStatus().equals("Married") && !nrf.getPrevMaritalStatus().equals("Common Law")
					&& !nrf.getPrevMaritalStatus().equals("Widowed") && !nrf.getPrevMaritalStatus().equals("Divorced")
					&& !nrf.getPrevMaritalStatus().equals("Separated") && !nrf.getPrevMaritalStatus().equals("Single") && !nrf.getPrevMaritalStatus().equals("na")) {
				// TODO
				Debugger.log("prevMaritalStatus validation if statement");
				throw new Exception("Invalid Previous Marital Status, please try again");
			}
		}
		// lastNameChange (Y/N) ??
		// citizen (Y/N) checkbox
		// address (Street, apartment, po box, po box location, RR#, City,
		// Privince/territory, Postal code, SOME ARE OPTIONAL)
		if (nrf.getAddress() == null || nrf.getAddress().length() > 200 || nrf.getAddress().length() == 0) {
			// TODO
			Debugger.log("address validation if statement");
			throw new Exception("Invalid Address, please try again");
		}
		if (nrf.getApartment().length() > 200) {
			// TODO
			Debugger.log("apartment validation if statement");
			throw new Exception("Invalid Apartment, please try again");
		}
		if (nrf.getPoBox().length() > 20) {
			// TODO
			Debugger.log("pobox validation if statement");
			throw new Exception("Invalid PoBox, please try again");
		}
		if (nrf.getPoBoxLocation().length() > 200) {
			// TODO
			Debugger.log("poboxlocation validation if statement");
			throw new Exception("Invalid PoBoxLocation, please try again");
		}
		if (nrf.getRrNum().length() > 20) {
			// TODO
			Debugger.log("rr validation if statement");
			throw new Exception("Invalid RR, please try again");
		}
		if (nrf.getCity() == null || nrf.getCity().length() > 200 || nrf.getCity().length() == 0) {
			// TODO
			Debugger.log("city validation if statement");
			throw new Exception("Invalid City, please try again");
		}
		if (nrf.getRegion() == null || nrf.getRegion().length() > 200 || nrf.getRegion().length() == 0) {
			// TODO
			Debugger.log("region validation if statement");
			throw new Exception("Invalid Region, please try again");
		}
		if (nrf.getPostalCode() == null || nrf.getPostalCode().length() > 10 || nrf.getPostalCode().length() == 0) {
			// TODO
			Debugger.log("postalcode validation if statement");
			throw new Exception("Invalid PostalCode, please try again");
		}
		// email (we have this)
		if (nrf.getEmail() == null || nrf.getEmail().length() > 200 || nrf.getEmail().length() == 0) {
			// TODO
			Debugger.log("email validation if statement");
			throw new Exception("Invalid Email, please try again");
		}
		// phone
		if (nrf.getPhone() == null || nrf.getPhone().length() > 15 || nrf.getPhone().length() == 0) {
			// TODO
			Debugger.log("phone validation if statement");
			throw new Exception("Invalid Phone, please try again");
		}

		// if married | commonLaw
		if (nrf.getMaritalStatus().equals("Married") || nrf.getMaritalStatus().equals("Common Law")) {
			// DOB
			try {
				Date newPartnerDOB = new SimpleDateFormat("yyyy-mm-dd").parse(nrf.getPartnerDOB());
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
			if (nrf.getPartnerSIN() == null || nrf.getPartnerSIN().length() > 12 || nrf.getPartnerSIN().length() == 0) {
				// TODO
				Debugger.log("partner SIN if statement");
				throw new Exception("Invalid partner SIN, please try again");
			}

			// title (Mr, Miss, Mrs, Ms)
			if (!nrf.getPartnerTitle().equals("Mr") && !nrf.getPartnerTitle().equals("Miss") && !nrf.getPartnerTitle().equals("Mrs")
					&& !nrf.getPartnerTitle().equals("Ms")) {
				// TODO
				Debugger.log("partner title validation if statement");
				throw new Exception("Invalid partner title, please try again");
			}
			// fName, initial(opt), lName
			if (nrf.getPartnerFName() == null || nrf.getPartnerFName().length() > 25 || nrf.getPartnerFName().length() == 0) {
				// TODO
				Debugger.log("partner fname validation if statement");
				throw new Exception("Invalid partner First Name, please try again");
			}
			if (nrf.getPartnerInitial().length() > 1) {
				// TODO
				Debugger.log("partner middle initial validation if statement");
				throw new Exception("Invalid partner Middle Initial, please try again");
			}
			if (nrf.getPartnerLName() == null || nrf.getPartnerLName().length() > 25 || nrf.getPartnerLName().length() == 0) {
				// TODO
				Debugger.log("partner lname validation if statement");
				throw new Exception("Invalid partner Last Name, please try again");
			}
			// gender (M/F)
			if (!nrf.getPartnerGender().equals("f") && !nrf.getPartnerGender().equals("m") && !nrf.getPartnerGender().equals("x")) {
				// TODO
				Debugger.log("partner gender validation if statement");
				throw new Exception("Invalid partner Gender, please try again");
			}

			if (nrf.getPartnerAddress() == null || nrf.getPartnerAddress().length() > 200 || nrf.getPartnerAddress().length() == 0) {
				// TODO
				Debugger.log("partner address validation if statement");
				throw new Exception("Invalid partner Address, please try again");
			}
			if (nrf.getPartnerApartment().length() > 200) {
				// TODO
				Debugger.log("partner apartment validation if statement");
				throw new Exception("Invalid partner Apartment, please try again");
			}
			if (nrf.getPartnerPoBox().length() > 20) {
				// TODO
				Debugger.log("partner pobox validation if statement");
				throw new Exception("Invalid partner PoBox, please try again");
			}
			if (nrf.getPartnerPoBoxLocation().length() > 200) {
				// TODO
				Debugger.log("partner poboxlocation validation if statement");
				throw new Exception("Invalid partner PoBoxLocation, please try again");
			}
			if (nrf.getPartnerRRNum().length() > 20) {
				// TODO
				Debugger.log("partner rr validation if statement");
				throw new Exception("Invalid partner RR, please try again");
			}
			if (nrf.getPartnerCity() == null || nrf.getPartnerCity().length() > 200 || nrf.getPartnerCity().length() == 0) {
				// TODO
				Debugger.log("partner city validation if statement");
				throw new Exception("Invalid partner City, please try again");
			}
			if (nrf.getPartnerRegion() == null || nrf.getPartnerRegion().length() > 200 || nrf.getPartnerRegion().length() == 0) {
				// TODO
				Debugger.log("partner region validation if statement");
				throw new Exception("Invalid partner Region, please try again");
			}
			if (nrf.getPartnerPostalCode() == null || nrf.getPartnerPostalCode().length() > 10 || nrf.getPartnerPostalCode().length() == 0) {
				// TODO
				Debugger.log("partner postalcode validation if statement");
				throw new Exception("Invalid partner PostalCode, please try again");
			}
			// email
			if (nrf.getPartnerEmailAddress() == null || nrf.getPartnerEmailAddress().length() > 200
					|| nrf.getPartnerEmailAddress().length() == 0) {
				// TODO
				Debugger.log("partner email validation if statement");
				throw new Exception("Invalid partner Email, please try again");
			}
			// phone
			if (nrf.getPartnerMobilePhone() == null || nrf.getPartnerMobilePhone().length() > 15 || nrf.getPartnerMobilePhone().length() == 0) {
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
		if ((nrf.isCanadaPost() != true && nrf.isCRAOnline() != true)||(nrf.isCanadaPost() != true && nrf.isAlreadyRegistered() != true)) {
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
		request.setAttribute("maritalStatus", request.getParameter("maritalStatus"));
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
		request.setAttribute("electionsCanada", electionsCanada);
		boolean foriegnProperty = false;
		if (request.getParameter("foreignProperty") != null) {
			foriegnProperty = true;
		}
		request.setAttribute("foreignProperty", foriegnProperty);
		boolean soldHome = false;
		if (request.getParameter("sellHome") != null) {
			soldHome = true;
		}
		request.setAttribute("sellHome", soldHome);
		boolean firstTime = false;
		if (request.getParameter("firstTime") != null) {
			firstTime = true;
		}
		request.setAttribute("firstTime", firstTime);
		// Notice of Assessment
		boolean canadaPost = false;
		if (request.getParameter("mailAssess") != null) {
			canadaPost = true;
		}
		request.setAttribute("mailAssess", canadaPost);
		boolean CRAOnline = false;
		if (request.getParameter("craAssess") != null) {
			CRAOnline = true;
		}
		request.setAttribute("craAssess", CRAOnline);
		boolean alreadyRegistered = false;
		if (request.getParameter("alreadyRegistered") != null) {
			alreadyRegistered = true;
		}
		request.setAttribute("alreadyRegistered", alreadyRegistered);
	}
}
