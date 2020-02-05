<%--
  User: Kerk Day
  Date: 2020-02-05
  Time: 12:30 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../../template/head.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <form action="" method="POST">

            </form>
        </div>
        <div class="col-md-6">
            <c:choose>
                <c:when test="${successMessage!=null}">
                    <span class="alert alert-success">
                        ${successMessage}
                    </span>
                </c:when>
                <c:when test="${errorMessage!=null}">
                    <span class="alert alert-warning">
                        ${errorMessage}
                    </span>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>

<jsp:directive.include file = "../../template/foot.jsp" />
