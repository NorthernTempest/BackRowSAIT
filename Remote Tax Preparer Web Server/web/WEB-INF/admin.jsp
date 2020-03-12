<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="col-12">
	<div class=row>
	<div class=col-md-6>
	<h4>
		User Settings
	</h4>
	<h5>
		Create New Account:
	</h5>
	<h5>
		Restore Account
	</h5>
	<form action=admin?action=restore method=POST>
		<p>Enter the email of the account you wish to restore: 
		<input type=email name=restoreEmail id=restoreEmail maxlength=100 class=form-control required>
		<input type=submit value=Submit></p>
	</form>
	<h5>
		Deactivate Account
	</h5>
	<form action=admin?action=deactivate method=POST>
	<p>Enter the email of the account you wish to deactivate: 
	<input type=email name=deactivateEmail id=deactivateEmail maxlength=100 class=form-control required>
	<input type=submit value=Submit></p>
	</form>
	</div>
	</div>
</div>
<hr class="nav-separator">
<div class="col-12">
	<h4>
		Backup & Restore
	</h4>
	<h6>
		Last Time Backed Up: 
	</h6>
	<h5>
		Backup:
	</h5>
	<h5>
		Restore: 
	</h5>
</div>
<hr class="nav-separator">
<div class="col-12">
	<h4>
		Configuration Settings
	</h4>
	<br><br><br><br>
</div>

<jsp:directive.include file="../../template/foot.jsp" />