<%--
  User: Kerk Day
  Date: 2020-02-10
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<form method="post" action="NewAccount">
	<div class="row">
		<div class="col-md-6">
			<h2>Creating New ${accountType}</h2>
			<input type="hidden" name="role" id="role" class="form-control" value="${role}">
			<br>
			<h3>Login Details</h3>
			<label for="email">Email</label>
            <input type="email" name="email" id="email" maxlength="100" class="form-control" value="${email}" required>
            <label for="password1">Password</label>
            <input type="password" name="password1" id="password1" maxlength="256" class="form-control password-field" required>
            <label for="password2">Password (again)</label>
            <input type="password" name="password2" id="password2" maxlength="256" class="form-control password-field" required>
		</div>
		<div class="col-md-6">
            <c:choose>
                <c:when test="${errorMessageEmail!=null}">
                    <div class="alert alert-warning"> ${errorMessageEmail} </div><br>
                </c:when>
            </c:choose>
        </div>
	</div>
	<br>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<h3>Personal Info</h3>
				<label for="title">Title</label>
                <select name="title" id="title" class="form-control" required>
					<option value="Na" selected>N/A</option>
					<option value="Mr">Mr</option>
					<option value="Mrs">Mrs</option>
					<option value="Ms">Ms</option>
					<option value="Mx">Mx</option>
				</select>
                <label for="fName">First Name</label>
                <input name="fName" id="fName" maxlength="25" value="${fName}" class="form-control" required>
                <label for="lName">Last Name</label>
                <input name="lName" id="lName" maxlength="25" class="form-control" value="${lName}" required>
			</div>
			<div class="form-group">
				<label for="phone">Primary Phone Number</label>
                <input type="tel" name="phone" id="phone" maxlength="15" class="form-control" value="${phone}" required>
			</div>
		</div>
		<div class="col-md-6"></div>
		<br>
	</div>
	<br>

    <hr>

	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<h3>Settings</h3>
				<label for="language">Preferred Language</label>
                <select name="language" id="language" class="form-control">
					<option value="en" ${language.equals("en")?"selected":""}>English</option>
					<option value="es" ${language.equals("es")?"selected":""}>Español</option>
				</select>
			</div>
		</div>
		<div class="col-md-6"></div>
	</div>
	<br>
	<div class="row">
		<div class="col-12">
			<div class="form-group">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
	</div>
</form>



<script type="text/javascript" src="../template/js/country-regions.js"></script>

<script>
    //Checks if passwords are the same
    document.getElementById("password2").onchange(function() {
        if(document.getElementById("password1").value!=document.getElementById("password2").value) {
            document.getElementById("password2").style.borderColor = "red";
        }
    });
</script>

<jsp:directive.include file="../template/foot.jsp" />
