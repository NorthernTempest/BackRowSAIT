<%--
  User: Kerk Day, Jesse Goerzen
  Date: 2020-02-10
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../../template/headunauthorized.jsp" />

<div class="col-md-6">

	<form method="post" action="">
		<p>Enter the email for your account below. You will receive
			further instructions on resetting your password in an email.</p>
		<label for="email">Email</label> <input type="email" id="email"
			name="email" class="form-control">
	</form>

</div>

<jsp:directive.include file="../../template/foot.jsp" />
