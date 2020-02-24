<%--
  User: Kerk Day
  Date: 2020-02-10
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../template/headunauthorized.jsp" />

<div class="row">
    <div class="col-12">
        <c:choose>
            <c:when test="${errorMessage!=null}">
                <span class="alert alert-danger">
                        ${errorMessage}
                </span>
            </c:when>
        </c:choose>
    </div>
</div>

<form method="post" action="">
    <div class="row">
        <div class="col-md-6">
            <h3>Login Details</h3>
            <label for="email">Email</label>
            <input type="email" name="email" id="email" maxlength="100" class="form-control">
            <label for="password1">Password</label>
            <input type="password" name="password1" id="password1" maxlength="256" class="form-control password-field">
            <label for="password2">Password (again)</label>
            <input type="password" name="password2" id="password2" maxlength="256" class="form-control password-field">
        </div>
        <div class="col-md-6">
            We do not send sales emails. You will only receive emails regarding new messages and notifications regarding your tax returns.
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <h3>Personal Info</h3>
                <label for="title">Title</label>
                <select name="title" id="title" class="form-control">
                    <option value="Na">N/A</option>
                    <option value="Mr">Mr</option>
                    <option value="Mrs">Mrs</option>
                    <option value="Ms">Ms</option>
                    <option value="Mx">Mx</option>
                </select>
                <label for="f-name">First Name</label>
                <input name="f-name" id="f-name" maxlength="25" class="form-control">
                <label for="m-name">Middle Name</label>
                <input name="m-name" id="m-name" maxlength="25" class="form-control">
                <label for="l-name">Last Name</label>
                <input name="l-name" id="l-name" maxlength="25" class="form-control">
            </div>
            <div class="form-group">
                <label for="phone">Primary Phone Number</label>
                <input type="tel" name="phone" id="phone" maxlength="15" class="form-control" pattern="1?\W*([2-9][0-8][0-9])\W*([2-9][0-9]{2})\W*([0-9]{4})(\se?x?t?(\d*))?">
                <label for="fax">Fax Number</label>
                <input type="tel" name="fax" id="fax" maxlength="15" class="form-control" pattern="1?\W*([2-9][0-8][0-9])\W*([2-9][0-9]{2})\W*([0-9]{4})(\se?x?t?(\d*))?">
            </div>
        </div>
        <div class="col-md-6">

        </div>
        <br>

    </div>
    <br>

    <div class="row">
        <div class="col-md-6">
            <h3>Address</h3>
            <label for="address1">Street Address</label>
            <input id="address1" name="address1" class="form-control" value="${address1}">
            <input id="address2" name="address2" class="form-control" value="${address2}">

            <label for="addressCity">City</label>
            <input id="addressCity" name="addressCity" class="form-control" value="${addressCity}">

            <label for="addressCountry">Country</label>
            <select class="crs-country form-control" id="addressCountry" data-region-id="addressRegion" name="addressCountry" value="${addressCountry}">
            </select>

            <label for="addressRegion">Province/State</label>
            <select id="addressRegion" class="form-control" name="addressRegion" value="${addressRegion}">
            </select>

            <label for="addressPostal">Postal Code</label>
            <input class="form-control" id="addressPostal" name="addressPostal" value="${addressPostal}">
        </div>
        <div class="col-md-6">
            <c:choose>
                <c:when test="${errorMessageAddress!=null}">
                <span class="alert alert-warning">
                        ${errorMessageAddress}
                </span>
                </c:when>
            </c:choose>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <h3>Settings</h3>
                <label for="language">Preferred Language</label>
                <select name="language" id="language" value="eng" class="form-control">
                    <option value="eng">English</option>
                    <option value="spn">Spanish</option>
                    <option value="fre">French</option>
                </select>
            </div>
        </div>
        <div class="col-md-6">

        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </div>
        <div class="col-md-6">

        </div>
    </div>
</form>

<script>
    //Checks if passwords are the same
    document.getElementById("password2").onchange(function() {
        if(document.getElementById("password1").value!=document.getElementById("password2").value) {
            document.getElementById("password2").style.transform = "scale(2, 1)";
        }
    })
</script>

<script type="text/javascript" src="../template/js/country-regions.js"></script>

<jsp:directive.include file = "../template/foot.jsp" />