<%--
  User: Kerk Day
  Date: 2020-02-10
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../../template/head.jsp" />

<div class="col-12">
    <c:choose>
        <c:when test="${errorMessage!=null}">
            <span class="alert alert-danger">
                    ${errorMessage}
            </span>
        </c:when>
    </c:choose>
</div>
<form method="post" action="">
    <div class="col-md-6">
        <label for="email">Email</label>
        <input type="email" name="email" id="email">
        <label for="password1">Password</label>
        <input type="password1" name="password1" id="password1">
        <label for="password2">Password (again)</label>
        <input type="password2" name="password2" id="password2">
    </div>
    <div class="col-md-6">
        We do not send any sales emails. We will only send emails as notifications and messages about your tax returns.
    </div>

    <div class="col-md-6">
        <div class="form-group">
            <h3>Personal Info</h3>
            <label for="title">Title</label>
            <select name="title" id="title">
                <option value="Mr">Mr</option>
                <option value="Mrs">Mrs</option>
                <option value="Ms">Ms</option>
                <option value="Mx">Mx</option>
            </select>
            <label for="f-name">First Name</label>
            <input name="f-name" id="f-name">
            <label for="l-name">Last Name</label>
            <input name="l-name" id="l-name">
        </div>
        <div class="form-group">
            <label for="phone">Primary Phone Number</label>
            <input type="tel" name="phone" id="phone">
            <label for="fax">Primary Phone Number</label>
            <input type="tel" name="fax" id="fax">
        </div>
    </div>
    <div class="col-md-6">

    </div>
</form>

<jsp:directive.include file = "../../template/foot.jsp" />