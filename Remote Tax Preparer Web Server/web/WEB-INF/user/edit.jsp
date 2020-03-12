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
		<h3>Name</h3>
		<label for="title">Title</label>
		<input id="title" name="title" class="form-control" placeholder="eg. 'mr'" value="${title}" required>
		<label for="fname">First Name</label>
		<input id="fname" name="fname" class="form-control" value="${fname}" required>
		<label for="mname">Middle Name</label>
		<input id="mname" name="mname" class="form-control" value="${mname}">
		<label for="lname">Last Name</label>
		<input id="lname" name="lname" class="form-control" value="${lname}" required>
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
		<label for="address1">Street Address</label>
		<input id="address1" name="address1" class="form-control" value="${address1}" required>
        <label for="address2">Street Address 2</label>
        <input id="address2" name="address2" class="form-control" value="${address2}">
		<label for="addressCity">City</label>
		<input id="addressCity" name="addressCity" class="form-control" value="${addressCity}" required>
		<label for="addressRegion">Province/State</label>
		<select id="addressRegion" class="form-control" name="addressRegion" data-default-value="${addressRegion}" data-value="shortcode" required></select>
		<label for="addressCountry">Country</label>
		<select class="crs-country form-control" id="addressCountry" data-region-id="addressRegion" name="addressCountry" data-default-value="${addressCountry}" data-value="shortcode" required></select>
		<label for="addressPostal">Postal Code</label>
		<input class="form-control" id="addressPostal" name="addressPostal" value="${addressPostal}" required>
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
		<label for="contactPhone">Phone Number</label>
		<input type="tel" id="contactPhone" name="contactPhone" class="form-control" value="${contactPhone}" required>
		<label for="contactFax">Fax Number</label>
		<input type="tel" id="contactFax" name="contactFax" class="form-control" value="${contactFax}">
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
		<h3>Language</h3>
		<label for="language">Language</label>
		<select class="form-control" id="language" name="language" required>
			<option value="en" ${language.equals("en")?"selected":""}>English</option>
			<option value="es" ${language.equals("es")?"selected":""}>Español</option>
		</select>
	</div>
	<div class="col-md-6">
		<c:choose>
			<c:when test="${errorMessageLanguage!=null}">
				<span class="alert alert-warning"> ${errorMessageLanguage} </span>
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
		<label for="newPassword2">Same Password</label>
		<input type="password" id="newPassword2" name="newPassword2" class="form-control">
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
<div class="col-md-6">
	<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteConfirm">
		Delete Account
	</button>
</div>

<script type="text/javascript" src="../../template/js/country-regions.js"></script>

<script>

</script>

<jsp:directive.include file="../../template/foot.jsp" />

<div class="modal fade" id="deleteConfirm" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="deleteConfirmLabel" style="color: black;">Confirm Deletion</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<p style="color: black;">Are you sure that you want to delete your account?</p>
			</div>
			<div class="modal-footer">
				<form action="/settings" method="POST">
					<input type="hidden" name="delete">
					<button type="submit" class="btn btn-danger">Yes, I want to delete my account</button>
				</form>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
