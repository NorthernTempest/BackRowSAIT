package domain;

/**
 * A temporary record of a form filled out by the user.
 * 
 * @author Cesar Guzman
 */
public final class NewReturnForm {
	// Form fields
	private String taxYear, dateOfBirth, sin, title, fName, middleInitial, lName, gender, maritalStatus,
			prevMaritalStatus, address, apartment, poBox, poBoxLocation, rrNum, city, region, country, postalCode,
			email, phone, partnerDOB, partnerSIN, partnerTitle, partnerFName, partnerInitial, partnerLName,
			partnerGender, partnerAddress, partnerApartment, partnerPoBox, partnerPoBoxLocation, partnerRRNum,
			partnerCity, partnerCountry, partnerRegion, partnerPostalCode, partnerEmailAddress, partnerMobilePhone;
	private boolean nameChange, maritalChange, canadianCitizen, partnerCanadianCitizen, electionsCanada,
			foriegnProperty, soldHome, firstTime, canadaPost, CRAOnline, alreadyRegistered;

	/**
	 * Gets the tax year of the form.
	 * 
	 * @return String The tax year of the form.
	 */
	public String getTaxYear() {
		return taxYear;
	}

	/**
	 * Sets the tax year of the form.
	 * 
	 * @param taxYear String The tax year of the form.
	 */
	public void setTaxYear(String taxYear) {
		this.taxYear = taxYear;
	}

	/**
	 * Gets the date that the customer was born.
	 * 
	 * @return String The date that the customer was born.
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date that the customer was born.
	 * 
	 * @param dateOfBirth String The date that the customer was born.
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the social insurance number of the customer.
	 * 
	 * @return String The social insurance number of the customer.
	 */
	public String getSin() {
		return sin;
	}

	/**
	 * Sets the social insurance number of the customer.
	 * 
	 * @param sin String The social insurance number of the customer.
	 */
	public void setSin(String sin) {
		this.sin = sin;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the middleInitial
	 */
	public String getMiddleInitial() {
		return middleInitial;
	}

	/**
	 * @param middleInitial the middleInitial to set
	 */
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return the prevMaritalStatus
	 */
	public String getPrevMaritalStatus() {
		return prevMaritalStatus;
	}

	/**
	 * @param prevMaritalStatus the prevMaritalStatus to set
	 */
	public void setPrevMaritalStatus(String prevMaritalStatus) {
		this.prevMaritalStatus = prevMaritalStatus;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the apartment
	 */
	public String getApartment() {
		return apartment;
	}

	/**
	 * @param apartment the apartment to set
	 */
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	/**
	 * @return the poBox
	 */
	public String getPoBox() {
		return poBox;
	}

	/**
	 * @param poBox the poBox to set
	 */
	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	/**
	 * @return the poBoxLocation
	 */
	public String getPoBoxLocation() {
		return poBoxLocation;
	}

	/**
	 * @param poBoxLocation the poBoxLocation to set
	 */
	public void setPoBoxLocation(String poBoxLocation) {
		this.poBoxLocation = poBoxLocation;
	}

	/**
	 * @return the rrNum
	 */
	public String getRrNum() {
		return rrNum;
	}

	/**
	 * @param rrNum the rrNum to set
	 */
	public void setRrNum(String rrNum) {
		this.rrNum = rrNum;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the partnerDOB
	 */
	public String getPartnerDOB() {
		return partnerDOB;
	}

	/**
	 * @param partnerDOB the partnerDOB to set
	 */
	public void setPartnerDOB(String partnerDOB) {
		this.partnerDOB = partnerDOB;
	}

	/**
	 * @return the partnerSIN
	 */
	public String getPartnerSIN() {
		return partnerSIN;
	}

	/**
	 * @param partnerSIN the partnerSIN to set
	 */
	public void setPartnerSIN(String partnerSIN) {
		this.partnerSIN = partnerSIN;
	}

	/**
	 * @return the partnerTitle
	 */
	public String getPartnerTitle() {
		return partnerTitle;
	}

	/**
	 * @param partnerTitle the partnerTitle to set
	 */
	public void setPartnerTitle(String partnerTitle) {
		this.partnerTitle = partnerTitle;
	}

	/**
	 * @return the partnerFName
	 */
	public String getPartnerFName() {
		return partnerFName;
	}

	/**
	 * @param partnerFName the partnerFName to set
	 */
	public void setPartnerFName(String partnerFName) {
		this.partnerFName = partnerFName;
	}

	/**
	 * @return the partnerInitial
	 */
	public String getPartnerInitial() {
		return partnerInitial;
	}

	/**
	 * @param partnerInitial the partnerInitial to set
	 */
	public void setPartnerInitial(String partnerInitial) {
		this.partnerInitial = partnerInitial;
	}

	/**
	 * @return the partnerLName
	 */
	public String getPartnerLName() {
		return partnerLName;
	}

	/**
	 * @param partnerLName the partnerLName to set
	 */
	public void setPartnerLName(String partnerLName) {
		this.partnerLName = partnerLName;
	}

	/**
	 * @return the partnerGender
	 */
	public String getPartnerGender() {
		return partnerGender;
	}

	/**
	 * @param partnerGender the partnerGender to set
	 */
	public void setPartnerGender(String partnerGender) {
		this.partnerGender = partnerGender;
	}

	/**
	 * @return the partnerAddress
	 */
	public String getPartnerAddress() {
		return partnerAddress;
	}

	/**
	 * @param partnerAddress the partnerAddress to set
	 */
	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}

	/**
	 * @return the partnerApartment
	 */
	public String getPartnerApartment() {
		return partnerApartment;
	}

	/**
	 * @param partnerApartment the partnerApartment to set
	 */
	public void setPartnerApartment(String partnerApartment) {
		this.partnerApartment = partnerApartment;
	}

	/**
	 * @return the partnerPoBox
	 */
	public String getPartnerPoBox() {
		return partnerPoBox;
	}

	/**
	 * @param partnerPoBox the partnerPoBox to set
	 */
	public void setPartnerPoBox(String partnerPoBox) {
		this.partnerPoBox = partnerPoBox;
	}

	/**
	 * @return the partnerPoBoxLocation
	 */
	public String getPartnerPoBoxLocation() {
		return partnerPoBoxLocation;
	}

	/**
	 * @param partnerPoBoxLocation the partnerPoBoxLocation to set
	 */
	public void setPartnerPoBoxLocation(String partnerPoBoxLocation) {
		this.partnerPoBoxLocation = partnerPoBoxLocation;
	}

	/**
	 * @return the partnerRRNum
	 */
	public String getPartnerRRNum() {
		return partnerRRNum;
	}

	/**
	 * @param partnerRRNum the partnerRRNum to set
	 */
	public void setPartnerRRNum(String partnerRRNum) {
		this.partnerRRNum = partnerRRNum;
	}

	/**
	 * @return the partnerCity
	 */
	public String getPartnerCity() {
		return partnerCity;
	}

	/**
	 * @param partnerCity the partnerCity to set
	 */
	public void setPartnerCity(String partnerCity) {
		this.partnerCity = partnerCity;
	}

	/**
	 * @return the partnerCountry
	 */
	public String getPartnerCountry() {
		return partnerCountry;
	}

	/**
	 * @param partnerCountry the partnerCountry to set
	 */
	public void setPartnerCountry(String partnerCountry) {
		this.partnerCountry = partnerCountry;
	}

	/**
	 * @return the partnerRegion
	 */
	public String getPartnerRegion() {
		return partnerRegion;
	}

	/**
	 * @param partnerRegion the partnerRegion to set
	 */
	public void setPartnerRegion(String partnerRegion) {
		this.partnerRegion = partnerRegion;
	}

	/**
	 * @return the partnerPostalCode
	 */
	public String getPartnerPostalCode() {
		return partnerPostalCode;
	}

	/**
	 * @param partnerPostalCode the partnerPostalCode to set
	 */
	public void setPartnerPostalCode(String partnerPostalCode) {
		this.partnerPostalCode = partnerPostalCode;
	}

	/**
	 * @return the partnerEmailAddress
	 */
	public String getPartnerEmailAddress() {
		return partnerEmailAddress;
	}

	/**
	 * @param partnerEmailAddress the partnerEmailAddress to set
	 */
	public void setPartnerEmailAddress(String partnerEmailAddress) {
		this.partnerEmailAddress = partnerEmailAddress;
	}

	/**
	 * @return the partnerMobilePhone
	 */
	public String getPartnerMobilePhone() {
		return partnerMobilePhone;
	}

	/**
	 * @param partnerMobilePhone the partnerMobilePhone to set
	 */
	public void setPartnerMobilePhone(String partnerMobilePhone) {
		this.partnerMobilePhone = partnerMobilePhone;
	}

	/**
	 * @return the nameChange
	 */
	public boolean isNameChange() {
		return nameChange;
	}

	/**
	 * @param nameChange the nameChange to set
	 */
	public void setNameChange(boolean nameChange) {
		this.nameChange = nameChange;
	}

	/**
	 * @return the maritalChange
	 */
	public boolean isMaritalChange() {
		return maritalChange;
	}

	/**
	 * @param maritalChange the maritalChange to set
	 */
	public void setMaritalChange(boolean maritalChange) {
		this.maritalChange = maritalChange;
	}

	/**
	 * @return the canadianCitizen
	 */
	public boolean isCanadianCitizen() {
		return canadianCitizen;
	}

	/**
	 * @param canadianCitizen the canadianCitizen to set
	 */
	public void setCanadianCitizen(boolean canadianCitizen) {
		this.canadianCitizen = canadianCitizen;
	}

	/**
	 * @return the partnerCanadianCitizen
	 */
	public boolean isPartnerCanadianCitizen() {
		return partnerCanadianCitizen;
	}

	/**
	 * @param partnerCanadianCitizen the partnerCanadianCitizen to set
	 */
	public void setPartnerCanadianCitizen(boolean partnerCanadianCitizen) {
		this.partnerCanadianCitizen = partnerCanadianCitizen;
	}

	/**
	 * @return the electionsCanada
	 */
	public boolean isElectionsCanada() {
		return electionsCanada;
	}

	/**
	 * @param electionsCanada the electionsCanada to set
	 */
	public void setElectionsCanada(boolean electionsCanada) {
		this.electionsCanada = electionsCanada;
	}

	/**
	 * @return the foriegnProperty
	 */
	public boolean isForiegnProperty() {
		return foriegnProperty;
	}

	/**
	 * @param foriegnProperty the foriegnProperty to set
	 */
	public void setForiegnProperty(boolean foriegnProperty) {
		this.foriegnProperty = foriegnProperty;
	}

	/**
	 * @return the soldHome
	 */
	public boolean isSoldHome() {
		return soldHome;
	}

	/**
	 * @param soldHome the soldHome to set
	 */
	public void setSoldHome(boolean soldHome) {
		this.soldHome = soldHome;
	}

	/**
	 * @return the firstTime
	 */
	public boolean isFirstTime() {
		return firstTime;
	}

	/**
	 * @param firstTime the firstTime to set
	 */
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	/**
	 * @return the canadaPost
	 */
	public boolean isCanadaPost() {
		return canadaPost;
	}

	/**
	 * @param canadaPost the canadaPost to set
	 */
	public void setCanadaPost(boolean canadaPost) {
		this.canadaPost = canadaPost;
	}

	/**
	 * @return the CRAOnline
	 */
	public boolean isCRAOnline() {
		return CRAOnline;
	}

	/**
	 * @param cRAOnline the CRAOnline to set
	 */
	public void setCRAOnline(boolean cRAOnline) {
		CRAOnline = cRAOnline;
	}

	/**
	 * @return Whether the customer is already registered to CRA online.
	 */
	public boolean isAlreadyRegistered() {
		return alreadyRegistered;
	}

	/**
	 * @param alreadyRegistered Whether the customer is already registered to CRA online.
	 */
	public void setAlreadyRegistered(boolean alreadyRegistered) {
		this.alreadyRegistered = alreadyRegistered;
	}

}
