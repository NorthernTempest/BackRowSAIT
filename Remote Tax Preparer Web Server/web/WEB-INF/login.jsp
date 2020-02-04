<%--
    Document   : login
    Created on : Oct 10, 2019, 3:13:44 PM
    Author     : 570157
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../template/head.jsp" />

<form action="" method="post">
    <div class="form-group col-md-6">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="example@email.com" class="form-control">
    </div>
    <div class="form-group col-md-6">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" class="form-control">
    <div class="form-group col-md-6">
        <button type="submit" class="btn btn-primary">Log in</button>
        <c:choose>
            <c:when test="${errorMessage!=null}">
                <div class="alert alert-warning" role="alert">
                        ${errorMessage}
                </div>
            </c:when>
        </c:choose>
    </div>
</form>

<jsp:directive.include file = "../template/foot.jsp" />