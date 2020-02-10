<%--
  User: Kerk Day
  Date: 2020-02-10
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../template/head.jsp" />

<div class="col-12">
    <c:choose>
        <c:when test="${message!=null}">
    <span class="alert alert-danger">
            ${message}
    </span>
        </c:when>
    </c:choose>
</div>

<jsp:directive.include file = "../template/foot.jsp" /></title>