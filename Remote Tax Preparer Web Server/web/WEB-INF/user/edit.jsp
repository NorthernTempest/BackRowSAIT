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
		<span style="color: red">*</span>Required fields for each section
	</div>
	<hr>
	<div class="col-md-6">
		<h3>Name</h3>
		<label for="title">Title<span style="color: red">*</span></label> <input
			id="title" name="fname" class="form-control" value="${title}">
		<label for="fname">First Name<span style="color: red">*</span></label>
		<input id="fname" name="fname" class="form-control" value="${fname}">
		<label for="mname">Middle Name</label> <input id="mname" name="mname"
			class="form-control" value="${mname}"> <label id="lname">Last
			Name<span style="color: red">*</span>
		</label> <input id="lname" name="lname" class="form-control" value="${lname}">
	</div>
	<div class="col-md-6">
		<c:choose>
			<c:when test="${errorMessageName!=null}">
				<span class="alert alert-warning"> ${errorMessageName} </span>
			</c:when>
		</c:choose>
	</div>
	<hr>
	<div class="col-md-6">
		<h3>Address</h3>
		<label for="address1">Street Address<span style="color: red">*</span></label>
		<input id="address1" name="address1" class="form-control"
			value="${address1}"> <input id="address2" name="address2"
			class="form-control" value="${address2}"> <label
			for="addressCity">City<span style="color: red">*</span></label> <input
			id="addressCity" name="addressCity" class="form-control"
			value="${addressCity}"> <label for="addressRegion">Province/State<span
			style="color: red">*</span></label> <select id="addressRegion"
			class="form-control" name="addressRegion" value="${addressRegion}">
		</select> <label for="addressCountry">Country<span style="color: red">*</span></label>
		<select class="crs-country form-control" id="addressCountry"
			data-region-id="addressRegion" name="addressCountry"
			value="${addressCountry}">
		</select> <label for="addressPostal">Postal Code<span
			style="color: red">*</span></label> <input class="form-control"
			id="addressPostal" name="addressPostal" value="${addressPostal}">
	</div>
	<div class="col-md-6">
		<c:choose>
			<c:when test="${errorMessageAddress!=null}">
				<span class="alert alert-warning"> ${errorMessageAddress} </span>
			</c:when>
		</c:choose>
	</div>
	<hr>
	<div class="col-md-6">
		<h3>Contact Details</h3>
		<label for="contactPhone">Phone Number<span style="color: red">*</span></label>
		<input type="tel" id="contactPhone" name="contactPhone"
			class="form-control" value="${contactPhone}"> <label
			for="contactFax">Fax Number</label> <input type="tel" id="contactFax"
			name="contactFax" class="form-control" value="${contactFax}">
	</div>
	<div class="col-md-6">
		<c:choose>
			<c:when test="${errorMessageContact!=null}">
				<span class="alert alert-warning"> ${errorMessageContact} </span>
			</c:when>
		</c:choose>
	</div>
	<hr>
	<div class="col-md-6">
		<h3>Change Password</h3>
		<label for="oldPassword">Old Password<span style="color: red">*</span></label>
		<input type="password" id="oldPassword" name="oldPassword"
			class="form-control"> <label for="newPassword1">New
			Password<span style="color: red">*</span>
		</label> <input type="password" id="newPassword1" name="newPassword1"
			class="form-control"> <label for="newPassword2">Same
			Password<span style="color: red">*</span>
		</label> <input type="password" id="newPassword2" name="newPassword2"
			class="form-control">
	</div>
	<div class="col-md-6">
		<c:choose>
			<c:when test="${errorMessagePassword!=null}">
				<span class="alert alert-warning"> ${errorMessagePassword} </span>
			</c:when>
		</c:choose>
	</div>
	<hr>
	<div class="col-md-6">
		<button type="submit" class="btn btn-primary">Save Changes</button>
	</div>
</form>
<br>
<form action="" method="POST">
	<div class="col-md-6">
		<input type="hidden" name="delete">
		<button type="submit" class="btn btn-danger">Delete Account</button>
	</div>
</form>

<script type="text/javascript"
	src="../../template/js/country-regions.js"></script>

<jsp:directive.include file="../../template/foot.jsp" />
