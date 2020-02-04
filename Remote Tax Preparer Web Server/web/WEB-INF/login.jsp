<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ include file="./template/header.jsp" %>

<form action="" method="POST">
	<div class="form-group-row">
		<label for="email" class="col-sm-2 col-form-label">Email</label>
		<div class="col-sm-10">
			<input type="email" class="form-control" id="email" name="email" placeholder="name@example.com">
		</div>
	</div>
	<div class="form-group-row">
		<label for="password" class="col-sm-2 col-form-label">Password</label>
		<div class="col-sm-10">
			<input type="password" class="form-control" id="password" name="password">
		</div>
		
	</div>
</form>

<%@ include file="./template/footer.jsp" %>