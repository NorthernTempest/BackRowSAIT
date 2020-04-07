<%--
	Author: Jesse Goerzen
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<div class="container">
	<div class="row">
		<div class="col-12">
			<div class="container">
				<h1>${locked.equals("t") ? "View" : "Update"} Returns</h1>
				<form action="/return/edit" method='${locked.equals("t") ? "GET" : "POST"}'>
					<div class="form-group">
						<label for="email">User (E-Mail)</label>
						<input id="email" class="form-control" type="text" name="email" placeholder="user@example.com" value="${email}" ${locked.equals("t") ? "readOnly" : ""} required>
					</div>
					<div class="form-group">
						<label for="year">Tax Year</label>
						<input id="year" class="form-control" type="number" name="year" value="${year}" max="${lastYear}" min="2000" step="1" required>
					</div>
					<div class="form-group">
						<label for="status">Tax Return Status</label>
						<c:choose>
							<c:when test='${!locked.equals("t")}'>
								<select id="status" name="status" class="form-control">
									<option value="i" ${status.equals("i") ? "selected" : ""}>Instantiated</option>
									<option value="r" ${status.equals("r") ? "selected" : ""}>Request Sent</option>
									<option value="a" ${status.equals("a") ? "selected" : ""}>Accepted</option>
									<option value="p" ${status.equals("p") ? "selected" : ""}>Processing</option>
									<option value="c" ${status.equals("c") ? "selected" : ""}>Waiting for Customer</option>
									<option value="w" ${status.equals("w") ? "selected" : ""}>Waiting for Payment</option>
									<option value="$" ${status.equals("$") ? "selected" : ""}>Paid</option>
									<option value="f" ${status.equals("f") ? "selected" : ""}>Filed</option>
									<option value="x" ${status.equals("x") ? "selected" : ""}>Cancelled</option>
								</select>
							</c:when>
							<c:when test='${status.equals("i")}'><input type="text" class="form-control" value="Instantiated" readOnly></c:when>
							<c:when test='${status.equals("r")}'><input type="text" class="form-control" value="Request Sent" readOnly></c:when>
							<c:when test='${status.equals("a")}'><input type="text" class="form-control" value="Accepted" readOnly></c:when>
							<c:when test='${status.equals("p")}'><input type="text" class="form-control" value="Processing" readOnly></c:when>
							<c:when test='${status.equals("c")}'><input type="text" class="form-control" value="Waiting for Customer" readOnly></c:when>
							<c:when test='${status.equals("w")}'><input type="text" class="form-control" value="Waiting for Payment" readOnly></c:when>
							<c:when test='${status.equals("$")}'><input type="text" class="form-control" value="Paid" readOnly></c:when>
							<c:when test='${status.equals("f")}'><input type="text" class="form-control" value="Filed" readOnly></c:when>
							<c:when test='${status.equals("x")}'><input type="text" class="form-control" value="Cancelled" readOnly></c:when>
							<c:otherwise><input type="text" class="form-control" value="Not Started" readOnly></c:otherwise>
						</c:choose>
					</div>
					<div class="form-group">
						<label for="amount">Tax Return Cost</label>
						<input id="amount" class="form-control" type="number" name="amount" value="${amount}" max="10000" min="0" step=".01" ${locked.equals("t") ? "readOnly" : ""} required>
					</div>
					<button type="submit" class="btn btn-primary">Update</button>
				</form>
			</div>
		</div>
	</div>
</div>

<jsp:directive.include file="../template/foot.jsp" />