<%--
  User: Kerk Day
  Date: 2020-02-05
  Time: 12:30 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../../template/head.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <form action="" method="POST">
                <div class="form-group">
                    <label for="address1">Street Address</label>
                    <input id="address1" name="address1" class="form-control">
                    <input id="address2" name="address2" class="form-control">
                </div>
                <div class="form-group">
                    <label for="addressCity">City</label>
                    <input id="addressCity" name="addressCity" class="form-control">
                </div>
                <div class="form-group">
                    <label for="addressRegion">Province/State</label>
                    <select id="addressRegion" class="form-control" name="addressRegion">
                        <option value="">-----</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Country</label>
                    <select class="crs-country form-control" data-region-id="addressRegion" name="addressCountry">
                    </select>
                </div>
            </form>
        </div>
        <div class="col-md-6 align-content-center">
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

<script type="text/javascript" src="../../template/js/country-regions.js"></script>

<jsp:directive.include file = "../../template/foot.jsp" />
