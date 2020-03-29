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
                <input type="checkbox" class="form-check-input" name="nameChange" id="nameChange">
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
                <input type="checkbox" class="form-check-input" name="maritalChange" id="maritalChange" ${maritalChange?"checked":""}>
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
                <input type="checkbox" class="form-check-input" name="canadianCitizen" id="canadianCitizen"  ${canadianCitizen?"checked":""}>
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

            </div>
        </div>
    </div>
    <br>

    <%--Email address--%>

    <%--Mobile phone--%>

    <%--if MARRIED / COMMON LAW (FOR PARTNER)--%>
    <%--SIN--%>
    <%--DOB--%>

    <%--Mr, Miss, Mrs, Ms--%>

    <%--First Name, Initial, Last Name--%>

    <%--Male/Female--%>

    <%--canadian citizen Y/N--%>

    <%--Address - Street, apartment, po box, po box location, RR#, City, Privince/territory, Postal code--%>

    <%--Email address--%>

    <%--Mobile phone--%>
    <%--ENDIF--%>

    <%--Provide information to elections canada Y/N--%>

    <%--Did you own foreign property at any time in (year)--%>
    <%--with a total cost of more than $100,000? Y/N--%>

    <%--Did you sell a home in 2019? Y/N--%>

    <%--Is this your first time filing your taxes? Y/N--%>

    <%--How do you want to recieve your Notice of Assessment?--%>
    <%--Mail (Canada Post)--%>
    <%--AND/OR--%>
    <%--Register with Canada Revenue agency for online mail--%>
    <%--already registered--%>
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
</script>

<jsp:directive.include file = "../../template/foot.jsp" />