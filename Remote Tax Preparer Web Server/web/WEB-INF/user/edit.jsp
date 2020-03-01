<%--
  User: Kerk Day
  Date: 2020-02-05
  Time: 12:30 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<form action="" method="POST">
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
    <hr>

    <div class="col-md-6">
        <h3>Contact Details</h3>
        <label for="contactPhone">Phone Number</label>
        <input type="tel" id="contactPhone" name="contactPhone" class="form-control" value="${contactPhone}">

        <label for="contactFax">Fax Number</label>
        <input type="tel" id="contactFax" name="contactFax" class="form-control" value="${contactFax}">
    </div>
    <div class="col-md-6">
        <c:choose>
            <c:when test="${errorMessageContact!=null}">
                <span class="alert alert-warning">
                        ${errorMessageContact}
                </span>
            </c:when>
        </c:choose>
    </div>

    <hr>

    <div class="col-md-6">
        <h3>Change Password</h3>
        <label for="oldPassword">Old Password</label>
        <input type="password" id="oldPassword" name="oldPassword" class="form-control">

        <label for="newPassword1">New Password</label>
        <input type="password" id="newPassword1" name="newPassword1" class="form-control">

        <label for="newPassword2">New Password (again)</label>
        <input type="password" id="newPassword2" name="newPassword2" class="form-control">
    </div>
    <div class="col-md-6">
        <c:choose>
            <c:when test="${errorMessagePassword!=null}">
                <span class="alert alert-warning">
                        ${errorMessagePassword}
                </span>
            </c:when>
        </c:choose>
    </div>

    <hr>

    <div class="col-md-6">
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </div>
</form>

<script type="text/javascript"
	src="../../template/js/country-regions.js"></script>

<jsp:directive.include file="../../template/foot.jsp" />
