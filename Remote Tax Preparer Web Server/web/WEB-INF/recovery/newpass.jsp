<%--
  User: Kerk Day, Jesse Goerzen
  Date: 2020-02-10
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/headunauthorized.jsp" />

<div class="col-md-6">
	<form method="post" action="/recover">
		<label for="newPass1">New Password</label>
		<input type="password" id="newPass1" name="newPass1" class="form-control">
		<label for="newPass2">New Password (again)</label>
		<input type="password" id="newPass2" name="newPass2" class="form-control">
		<input type="hidden" name="verify" value="${verify}">
		<input type="submit" value="Set Password">
	</form>
</div>

<jsp:directive.include file="../../template/foot.jsp" />
