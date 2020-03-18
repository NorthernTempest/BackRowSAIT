<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<div class="col-12">
	<div class=row>
	<div class=col-md-6>
	<h1>
		User Settings
	</h1>
	<h4>
		Create New Account
	</h4>
	<form action=admin?action=createNewTaxPreparer method=POST>
		<input type=submit class="btn btn-primary" value="Create New Tax Preparer Account">
	</form><br>
	<form action=admin?action=createNewAdmin method=POST>
		<input type=submit class="btn btn-primary" value="Create New Admin Account">
	</form><br>
	<h4>
		Restore Account
	</h4>
	<form action=admin?action=restore method=POST>
		<p>Enter the email of the account you wish to restore: 
		<input type=email name=restoreEmail id=restoreEmail maxlength=100 class=form-control required>
		<input type=submit class="btn btn-primary" value="Restore Account"></p>
	</form><br>
	<h4>
		Deactivate Account
	</h4>
	<form action=admin?action=deactivate method=POST>
	<p>Enter the email of the account you wish to deactivate: 
	<input type=email name=deactivateEmail id=deactivateEmail maxlength=100 class=form-control required>
	<input type=submit class="btn btn-danger" value="Deactivate Account"></p>
	</form>
	</div>
	</div>
</div>
<hr class="nav-separator">
<div class="col-12">
	<h1>
		Backup & Restore
	</h1>
	<h6>
		Last Time Backed Up: 
	</h6>
	<h4>
		Backup:
	</h4>
	<h4>
		Restore: 
	</h4>
</div>
<hr class="nav-separator">
<div class="col-12">
	<h1>
		Configuration Settings
	</h1>
	<br><br><br><br>
</div>

<jsp:directive.include file="../template/foot.jsp" />