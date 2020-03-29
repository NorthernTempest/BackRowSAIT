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
                <input class="form-control" type="number" onchange="setYear()" name="taxYear" id="taxYear" value="${taxYear}" required>
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
                <input id="title" name="title" class="form-control" placeholder="eg. 'Mr'" value="${title}">
            </div>
            <div class="form-group">
                <label for="fname">First Name</label>
                <input class="form-control" name="fname" id="fname" value="${fname}" required>
                <label for="initial">Middle Initial</label>
                <input class="form-control" name="inital" id="initial" value="${initial}">
                <label for="lname">Last Name</label>
                <input class="form-control" name="lname" id="lname" value="${lname}" required>
            </div>
            <div class="form-group">
                <label for="gender">Gender</label>
                <select class="form-control" name="gender" id="gender">
                    <option value="f" ${gender.equals("f")?"selected":""}>Female</option>
                    <option value="m" ${gender.equals("m")?"selected":""}>Male</option>
                    <option value="x" ${gender.equals("x")?"selected":""}>Other/prefer not to say</option>
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
                    <option value="Common Law" ${maritalStatus.equals("Common Law")?"selected":""}>Common Law</option>
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
            <div class="form-group">
                <label for="prevMaritalStatus"><span class="taxYearMinus">Last year</span>s Marital Status</label>
                <select class="form-control" name="prevMaritalStatus" id="prevMaritalStatus">
                    <option value="na">Not Applicable</option>
                    <option value="Married" ${prevMaritalStatus.equals("Married")?"selected":""}>Married</option>
                    <option value="Common Law" ${prevMaritalStatus.equals("Common Law")?"selected":""}>Common Law</option>
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
                <label for="address1">Street Address</label>
                <input id="address1" name="address1" class="form-control" value="${address1}">
                <label for="address1">Street Address 2</label>
                <input id="address2" name="address2" class="form-control" value="${address2}">
                <br>
                <label for="addressCity">City</label>
                <input id="addressCity" name="addressCity" class="form-control" value="${addressCity}">
                <label for="addressCountry">Country</label>
                <select class="crs-country form-control" id="addressCountry" data-region-id="addressRegion" data-value="shortcode" name="addressCountry" data-default-value="${addressCountry}">
                </select>
                <label for="addressRegion">Province/State</label>
                <select id="addressRegion" class="form-control" data-value="shortcode" name="addressRegion" data-default-value="${addressRegion}">
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
                <input id="partnerTitle" name="partnerTitle" class="form-control" placeholder="eg. 'Mr'" value="${partnerTitle}">
            </div>
            <div class="form-group">
                <label for="partnerFname">First Name</label>
                <input class="form-control spouser" name="partnerFname" id="partnerFname" value="${partnerFname}">
                <label for="partnerInitial">Middle Initial</label>
                <input class="form-control" name="partnerInital" id="partnerInitial" value="${partnerInitial}">
                <label for="partnerLname">Last Name</label>
                <input class="form-control spouser" name="partnerLname" id="partnerLname" value="${partnerLname}">
            </div>
            <div class="form-group">
                <label for="partnerGender">Gender</label>
                <select class="form-control" name="partnerGender" id="partnerGender">
                    <option value="f" ${partnerGender.equals("f")?"selected":""}>Female</option>
                    <option value="m" ${partnerGender.equals("m")?"selected":""}>Male</option>
                    <option value="x" ${partnerGender.equals("x")?"selected":""}>Other/prefer not to say</option>
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
                <label for="partnerAddress1">Street Address</label>
                <input id="partnerAddress1" name="partnerAddress1" class="form-control" value="${partnerAddress1}">
                <label for="partnerAddress2">Street Address 2</label>
                <input id="partnerAddress2" name="partnerAddress2" class="form-control" value="${partnerAddress2}">
                <br>
                <label for="partnerAddressCity">City</label>
                <input id="partnerAddressCity" name="partnerAddressCity" class="form-control" value="${partnerAddressCity}">
                <label for="partnerAddressCountry">Country</label>
                <select class="crs-country form-control" id="partnerAddressCountry" data-region-id="addressRegion" data-value="shortcode" name="addressCountry" data-default-value="${partnerAddressCountry}">
                </select>
                <label for="partnerAddressRegion">Province/State</label>
                <select id="partnerAddressRegion" class="form-control" data-value="shortcode" name="partnerAddressRegion" data-default-value="${partnerAddressRegion}">
                </select>
                <label for="partnerAddressPostal">Postal Code</label>
                <input class="form-control" id="partnerAddressPostal" name="partnerAddressPostal" value="${partnerAddressPostal}">
            </div>
            <br>
            <div class="form-group">
                <h3>Contact Details</h3>
                <label for="email">Email</label>
                <input class="form-control" type="email" id="partnerEmail" name="partnerEmail" value="${partnerEmail}" required>
                <label for="phone">Phone</label>
                <input class="form-control" type="tel" id="partnerPhone" name="partnerPhone" value="${partnerPhone}" required>
            </div>
        </div>
    </div>
    <br>

    <%--Provide information to elections canada Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <input type="checkbox" class="form-check-input" name="electionsCanada" id="electionsCanada" value="y" ${electionsCanada?"checked":""}>
                <label class="form-check-label" for="electionsCanada">Would you like to provide your information to Elections Canada?</label>
            </div>
        </div>
    </div>
    <%--Did you own foreign property at any time in (year)--%>
    <%--with a total cost of more than $100,000? Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <input type="checkbox" class="form-check-input" name="foreignProperty" id="foreignProperty" value="y" ${foreignProperty?"checked":""}>
                <label class="form-check-label" for="foreignProperty">Did you own foreign property at any time in <span class="taxYear">this last year</span> with a total cost of more than $100,000?</label>
            </div>
        </div>
    </div>

    <%--Did you sell a home in 2019? Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <input type="checkbox" class="form-check-input" name="sellHome" id="sellHome" value="y" ${sellHome?"checked":""}>
                <label class="form-check-label" for="sellHome">Did you sell a home in <span class="taxYear">this last year</span>?</label>
            </div>
        </div>
    </div>

    <%--Is this your first time filing your taxes? Y/N--%>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <input type="checkbox" class="form-check-input" name="firstTime" id="firstTime" value="y" ${firstTime?"checked":""}>
                <label class="form-check-label" for="firstTime">Is this your first time filing your taxes?</label>
            </div>
        </div>
    </div>

    <%--How do you want to recieve your Notice of Assessment?--%>
    <%--Mail (Canada Post)--%>
    <%--AND/OR--%>
    <%--Register with Canada Revenue agency for online mail--%>
    <%--already registered--%>
    <div class="row">
        <div class="col-md-6">
            <p>How would you like to receive your notice of assessment?</p>
            <div class="form-group">
                <input type="checkbox" class="form-check-input" name="mailAssess" id="mailAssess" value="y" ${mailAssess?"checked":""}>
                <label class="form-check-label" for="mailAssess">Mail (Canada Post)</label>
            </div>
            <br>
            <div class="form-group">
                <input type="checkbox" class="form-check-input" name="craAssess" id="craAssess" value="y" ${craAssess?"checked":""}>
                <label class="form-check-label" for="craAssess">Register with Canada Revenue Agency for online mail</label>
            </div>
            <div class="form-group">
                <input type="checkbox" class="form-check-input" name="alreadyRegistered" id="alreadyRegistered" value="y" ${alreadyRegistered?"checked":""}>
                <label class="form-check-label" for="alreadyRegistered">I'm already registered for CRA online mail</label>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <button type="submit">Submit</button>
            </div>
        </div>
    </div>

</form>

<script>
    document.getElementById("taxYear").min = new Date().getFullYear()-6;
    document.getElementById("taxYear").max = new Date().getFullYear()-1;
    document.getElementById("taxYear").value = new Date().getFullYear()-1;

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

    function setReq() {

    }
</script>

<script type="text/javascript" src="../../template/js/country-regions.js"></script>

<jsp:directive.include file = "../../template/foot.jsp" />