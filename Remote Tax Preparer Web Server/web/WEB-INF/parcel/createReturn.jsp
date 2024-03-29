<%--
  Created by IntelliJ IDEA.
  User: Kerk Day
  Date: 2020-03-27
  Time: 9:41 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<form action="" method="post">

    <div class="row">
        <div class="col-12">
            <h3>Create Return</h3>
            <p>All information below will be encrypted, and only available between you and your tax preparer.
                If you would prefer to complete this information on the phone or in person,
                <a href="https://www.mcltaxes.com/contact">contact us</a>.</p>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="taxYear">Tax Year</label>
                <select class="form-control" id="taxYear" name="taxYear">
                </select>
            </div>
        </div>
    </div>
    <br>
    <%--  DOB  --%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="dateOfBirth">Date of Birth</label>
                <input class="form-control" type="date" name="dateOfBirth" id="dateOfBirth" value="${dateOfBirth}" required>
            </div>
            <div class="form-group">
                <label for="sin">SIN</label>
                <input class="form-control" type="number" min="100000000" max="999999999" name="sin" id="sin" value="${sin}" required>
            </div>
        </div>
    </div>
    <br>
    <%--  Name  --%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="title">Title</label>
                <select id="title" name="title" class="form-control">
                    <option value="Mr">Mr</option>
                    <option value="Ms" ${title.equals("Ms")?"selected":""}>Ms</option>
                    <option value="Mrs" ${title.equals("Mrs")?"selected":""}>Mrs</option>
                    <option value="Miss" ${title.equals("Miss")?"selected":""}>Miss</option>
                </select>
            </div>
            <div class="form-group">
                <label for="fname">First Name</label>
                <input class="form-control" name="fname" id="fname" maxlength="25" value="${fname}" required>
                <label for="initial">Middle Initial</label>
                <input class="form-control" name="initial" id="initial" maxlength="1" value="${initial}">
                <label for="lname">Last Name</label>
                <input class="form-control" name="lname" id="lname" maxlength="25" value="${lname}" required>
            </div>
            <div class="form-group">
                <label for="gender">Gender</label>
                <select class="form-control" name="gender" id="gender">
                    <option value="f" ${gender.equals("f")?"selected":""}>Female</option>
                    <option value="m" ${gender.equals("m")?"selected":""}>Male</option>
                </select>
            </div>
        </div>
        <div class="col-md-5 offset-md-1">
            <br>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="nameChange" id="nameChange" value="y" ${nameChange?"checked":""}>
                <label class="form-check-label" for="nameChange">Did your name change during <span class="taxYear">this last year</span>?</label>
            </div>
        </div>
    </div>
    <br>
    <%--Married, Common Law, Widowed, Divorced, Separated, Single--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="maritalStatus">Marital Status</label>
                <select class="form-control" name="maritalStatus" id="maritalStatus">
                    <option value="Married" ${maritalStatus.equals("Married")?"selected":""}>Married</option>
                    <option value="Commonlaw" ${maritalStatus.equals("Commonlaw")?"selected":""}>Common Law</option>
                    <option value="Widowed" ${maritalStatus.equals("Widowed")?"selected":""}>Widowed</option>
                    <option value="Divorced" ${maritalStatus.equals("Divorced")?"selected":""}>Divorced</option>
                    <option value="Separated" ${maritalStatus.equals("Separated")?"selected":""}>Separated</option>
                    <option value="Single" ${maritalStatus.equals("Single")?"selected":""}>Single</option>
                </select>
            </div>
            <br>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="maritalChange" id="maritalChange" value="y"  ${maritalChange?"checked":""}>
                <label class="form-check-label" for="maritalChange" >Did your marital status change during <span class="taxYear">this last year</span>?</label>
            </div>
            <div class="form-group" id="maritalChangeBox">
                <label for="prevMaritalStatus"><span class="taxYearMinus">Last year</span>s Marital Status</label>
                <select class="form-control" name="prevMaritalStatus" id="prevMaritalStatus">
                    <option value="na">Not Applicable</option>
                    <option value="Married" ${prevMaritalStatus.equals("Married")?"selected":""}>Married</option>
                    <option value="Commonlaw" ${prevMaritalStatus.equals("Commonlaw")?"selected":""}>Common Law</option>
                    <option value="Widowed" ${prevMaritalStatus.equals("Widowed")?"selected":""}>Widowed</option>
                    <option value="Divorced" ${prevMaritalStatus.equals("Divorced")?"selected":""}>Divorced</option>
                    <option value="Separated" ${prevMaritalStatus.equals("Separated")?"selected":""}>Separated</option>
                    <option value="Single" ${prevMaritalStatus.equals("Single")?"selected":""}>Single</option>
                </select>
            </div>
        </div>
    </div>
    <br>
    <%--canadian citizen Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="canadianCitizen" id="canadianCitizen" value="y" ${canadianCitizen?"checked":""}>
                <label class="form-check-label" for="canadianCitizen">Are you currently a Canadian Citizen?</label>
            </div>
        </div>
    </div>
    <br>
    <%--Address - Street, apartment, po box, po box location, RR#, City, Privince/territory, Postal code--%>
    <%--SOME ARE OPTIONAL ^--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="address">Street Address</label>
                <input id="address" name="address" class="form-control" value="${address}">
                <label for="apartment">Apartment</label>
                <input id="apartment" name="apartment" class="form-control" value="${apartment}">
                <br>
                <label for="po">PO Box</label>
                <input id="po" name="po" class="form-control" value="${po}">
                <label for="poLocation">PO Box Location</label>
                <input id="poLocation" name="poLocation" class="form-control" value="${poLocation}">
                <label for="rr">RR#</label>
                <input id="rr" name="rr" class="form-control" value="${rr}">
                <br>
                <label for="addressCity">City</label>
                <input id="addressCity" name="addressCity" class="form-control" value="${addressCity}">
                <label for="addressCountry">Country</label>
                <select class="crs-country form-control" id="addressCountry" data-region-id="addressRegion" data-value="shortcode" name="addressCountry" data-preferred="CA,US" data-default-value="${addressCountry.toUpperCase()}">
                </select>
                <label for="addressRegion">Province/State</label>
                <select id="addressRegion" class="form-control" data-value="shortcode" name="addressRegion" data-default-value="${addressRegion.toUpperCase()}">
                </select>
                <label for="addressPostal">Postal Code</label>
                <input class="form-control" id="addressPostal" name="addressPostal" value="${addressPostal}">
            </div>
        </div>
    </div>
    <br>
    <%--Contact--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="email">Email</label>
                <input class="form-control" type="email" id="email" name="email" value="${email}" required>
                <label for="phone">Phone</label>
                <input class="form-control" type="tel" id="phone" name="phone" value="${phone}" required>
            </div>
        </div>
    </div>
    <br>

    <%--if MARRIED / COMMON LAW (FOR PARTNER)--%>

    <%--Mr, Miss, Mrs, Ms--%>

    <%--First Name, Initial, Last Name--%>

    <%--Male/Female--%>

    <%--canadian citizen Y/N--%>

    <%--Address - Street, apartment, po box, po box location, RR#, City, Privince/territory, Postal code--%>

    <%--Email address--%>

    <%--Mobile phone--%>
    <%--ENDIF--%>
    <div class="row" id="partnerDetails">
        <div class="col-md-6">
            <div class="form-group">
                <h1>Partners Details</h1>
                <p>Below are the required details of your dependant partner</p>
            </div>
            <div class="form-group">
                <label for="partnerDateOfBirth">Date of Birth</label>
                <input class="form-control spouser" type="date" name="partnerDateOfBirth" id="partnerDateOfBirth" value="${partnerDateOfBirth}">
                <label for="partnerSin">SIN</label>
                <input class="form-control spouser" type="number" min="100000000" max="999999999" name="partnerSin" id="partnerSin" value="${partnerSin}">
            </div>
            <br>
            <div class="form-group">
                <h3>Personal Information</h3>
                <label for="partnerTitle">Title</label>
                <select id="partnerTitle" name="partnerTitle" class="form-control">
                    <option value="Mr">Mr</option>
                    <option value="Ms" ${partnerTitle.equals("Ms")?"selected":""}>Ms</option>
                    <option value="Mrs" ${partnerTitle.equals("Mrs")?"selected":""}>Mrs</option>
                    <option value="Miss" ${partnerTitle.equals("Miss")?"selected":""}>Miss</option>
                </select>
            </div>
            <div class="form-group">
                <label for="partnerFname">First Name</label>
                <input class="form-control spouser" name="partnerFname" id="partnerFname" value="${partnerFname}">
                <label for="partnerInitial">Middle Initial</label>
                <input class="form-control" name="partnerInitial" id="partnerInitial" value="${partnerInitial}">
                <label for="partnerLname">Last Name</label>
                <input class="form-control spouser" name="partnerLname" id="partnerLname" value="${partnerLname}">
            </div>
            <div class="form-group">
                <label for="partnerGender">Gender</label>
                <select class="form-control" name="partnerGender" id="partnerGender">
                    <option value="f" ${partnerGender.equals("f")?"selected":""}>Female</option>
                    <option value="m" ${partnerGender.equals("m")?"selected":""}>Male</option>
                </select>
            </div>
            <br>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="partnerCanadianCitizen" id="partnerCanadianCitizen" value="y" ${partnerCanadianCitizen?"checked":""}>
                <label class="form-check-label" for="partnerCanadianCitizen">Are they currently a Canadian Citizen?</label>
            </div>
            <br>
            <div class="form-group">
                <h3>Address</h3>
                <label for="partnerAddress">Street Address</label>
                <input id="partnerAddress" name="partnerAddress" class="form-control" value="${partnerAddress}">
                <label for="partnerApartment">Apartment</label>
                <input id="partnerApartment" name="partnerApartment" class="form-control" value="${partnerApartment}">
                <br>
                <label for="partnerPo">PO Box</label>
                <input id="partnerPo" name="partnerPo" class="form-control" value="${po}">
                <label for="partnerPoLocation">PO Box Location</label>
                <input id="partnerPoLocation" name="partnerPoLocation" class="form-control" value="${poLocation}">
                <label for="partnerrr">RR#</label>
                <input id="partnerrr" name="partnerrr" class="form-control" value="${partnerrr}">
                <br>
                <label for="partnerAddressCity">City</label>
                <input id="partnerAddressCity" name="partnerAddressCity" class="form-control" value="${partnerAddressCity}">
                <label for="partnerAddressCountry">Country</label>
                <select class="crs-country form-control" id="partnerAddressCountry" data-region-id="partnerAddressRegion" data-value="shortcode" name="partnerAddressCountry" data-default-value="${partnerAddressCountry.toUpperCase()}">
                </select>
                <label for="partnerAddressRegion">Province/State</label>
                <select id="partnerAddressRegion" class="form-control" data-value="shortcode" name="partnerAddressRegion" data-default-value="${partnerAddressRegion.toUpperCase()}">
                </select>
                <label for="partnerAddressPostal">Postal Code</label>
                <input class="form-control" id="partnerAddressPostal" name="partnerAddressPostal" value="${partnerAddressPostal}">
            </div>
            <br>
            <div class="form-group">
                <h3>Contact Details</h3>
                <label for="email">Email</label>
                <input class="form-control" type="email" id="partnerEmail" name="partnerEmail" value="${partnerEmail}">
                <label for="phone">Phone</label>
                <input class="form-control" type="tel" id="partnerPhone" name="partnerPhone" value="${partnerPhone}">
            </div>
        </div>
    </div>
    <br>

    <%--Provide information to elections canada Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="electionsCanada" id="electionsCanada" value="y" ${electionsCanada?"checked":""}>
                <label class="form-check-label" for="electionsCanada">Would you like to provide your information to Elections Canada?</label>
            </div>
        </div>
    </div>
    <br>
    <%--Did you own foreign property at any time in (year)--%>
    <%--with a total cost of more than $100,000? Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="foreignProperty" id="foreignProperty" value="y" ${foreignProperty?"checked":""}>
                <label class="form-check-label" for="foreignProperty">Did you own foreign property at any time in <span class="taxYear">this last year</span> with a total cost of more than $100,000?</label>
            </div>
        </div>
    </div>
    <br>
    <%--Did you sell a home in 2019? Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="sellHome" id="sellHome" value="y" ${sellHome?"checked":""}>
                <label class="form-check-label" for="sellHome">Did you sell a home in <span class="taxYear">this last year</span>?</label>
            </div>
        </div>
    </div>
    <br>
    <%--Is this your first time filing your taxes? Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="firstTime" id="firstTime" value="y" ${firstTime?"checked":""}>
                <label class="form-check-label" for="firstTime">Is this your first time filing your taxes?</label>
            </div>
        </div>
    </div>
    <br>
    <%--How do you want to recieve your Notice of Assessment?--%>
    <%--Mail (Canada Post)--%>
    <%--AND/OR--%>
    <%--Register with Canada Revenue agency for online mail--%>
    <%--already registered--%>
    <div class="row">
        <div class="col-md-6">
            <p>How would you like to receive your notice of assessment?</p>

            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="mailAssess" id="mailAssess" value="y" ${mailAssess?"checked":""}>
                <label class="form-check-label" for="mailAssess">Mail (Canada Post)</label>
            </div>

            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="alreadyRegistered" id="alreadyRegistered" value="y" ${alreadyRegistered?"checked":""}>
                <label class="form-check-label" for="alreadyRegistered">I'm already registered for CRA online mail</label>
            </div>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" name="craAssess" id="craAssess" value="y" ${craAssess?"checked":""}>
                <label class="form-check-label" for="craAssess">Register with Canada Revenue Agency for online mail</label>
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </div>

</form>

<script>
    let yearMin = new Date().getFullYear()-6;
    let yearMax = new Date().getFullYear()-1;
    let yearEl = document.getElementById("taxYear");

    window.onload = function() {
        for(let x = yearMin; x <= yearMax; x++) {
            let option = document.createElement('option');
            option.value = x;
            option.innerText = x;
            if (x === ${taxYear != null ? taxYear : -10000}) {
                option.selected = true;
            }
            yearEl.appendChild(option);
        }
    }

    function setYear() {
        let els = document.getElementsByClassName("taxYear");
        for (let x in els) {
            els[x].innerHTML = document.getElementById("taxYear").value;
        }
        let elsM = document.getElementsByClassName("taxYearMinus");
        for (let x in els) {
            elsM[x].innerHTML = document.getElementById("taxYear").value-1;
        }
    }

    function updateForm() {
        let e = document.getElementById("maritalStatus"), selected = e.options[e.selectedIndex].value;
        if(selected == "Married" || selected == "Commonlaw") {
            document.getElementById("partnerDetails").style.display = "box";
        } else {
            document.getElementById("partnerDetails").style.display = "none";
        }

        if (document.getElementById("maritalChange").checked) {
            document.getElementById("maritalChangeBox").style.display = "";
        } else {
            document.getElementById("maritalChangeBox").style.display = "none";
        }

        if(document.getElementById("alreadyRegistered").checked) {
            document.getElementById("craAssess").disabled = true;
            document.getElementById("craAssess").checked = false;
        } else {
            document.getElementById("craAssess").disabled = false;
        }
    }

    setInterval(updateForm, 100);
</script>

<script type="text/javascript" src="../../template/js/country-regions.js"></script>

<jsp:directive.include file = "../../template/foot.jsp" />