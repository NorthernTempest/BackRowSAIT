<%--
  User: Kerk Day
  Date: 2020-02-05
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <table class="table table-dark table-borderless">
                <tbody>
                <tr>
                    <td><b>First Name</b></td>
                    <td>${user.fName}</td>
                </tr>
                <tr>
                    <td><b>Middle Name</b></td>
                    <td>${user.mName}</td>
                </tr>
                <tr>
                    <td><b>Last Name</b></td>
                    <td>${user.lName}</td>
                </tr>
                </tbody>
            </table>
            <br>
            <table class="table table-dark table-borderless">
                <tbody>
                <tr>
                    <td><b>Permission Level</b></td>
                    <td>
                        <c:choose>
                            <c:when test="${user.permissionLevel == 0}">
                                Invalid
                            </c:when>
                            <c:when test="${user.permissionLevel == 1}">
                                User
                            </c:when>
                            <c:when test="${user.permissionLevel == 2}">
                                Tax Preparer
                            </c:when>
                            <c:when test="${user.permissionLevel == 3}">
                                Admin
                            </c:when>
                            <c:when test="${user.permissionLevel == 4}">
                                System Admin
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
                </tbody>
            </table>
            <br>
            <table class="table table-dark table-borderless">
                <tbody>
                <tr>
                    <td><b>Email</b></td>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <td><b>Phone Number</b></td>
                    <td>${user.phone}</td>
                </tr>
                <c:choose>
                    <c:when test='${!user.fax.equals("")}'>
                        <tr>
                            <td><b>Fax Number</b></td>
                            <td>${user.fax}</td>
                        </tr>
                    </c:when>
                </c:choose>
                </tbody>
            </table>
            <br>
            <table class="table table-dark table-borderless">
                <tbody>
                <tr>
                    <td><b>Country</b></td>
                    <td>${user.country}</td>
                </tr>
                <tr>
                    <td><b>Province/State</b></td>
                    <td>${user.province}</td>
                </tr>
                <tr>
                    <td><b>City</b></td>
                    <td>${user.city}</td>
                </tr>
                <tr>
                    <td><b>Address</b></td>
                    <td>${user.streetAddress}
                    <c:choose>
                        <c:when test='${!user.streetAddress2.equals("")}'>
                            <br>${user.streetAddress2}
                        </c:when>
                    </c:choose>
                    </td>
                </tr>
                <tr>
                    <td><b>Postal Code</b></td>
                    <td>${user.postalCode}</td>
                </tr>
                </tbody>
            </table>
            <br>
            <table class="table table-dark table-borderless">
                <tbody>
                <tr>
                    <td><b>First Name</b></td>
                    <td>${user.fName}</td>
                </tr>
                <tr>
                    <td><b>Middle Name</b></td>
                    <td>${user.mName}</td>
                </tr>
                <tr>
                    <td><b>Last Name</b></td>
                    <td>${user.lName}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-md-6">

        </div>
    </div>
</div>

<jsp:directive.include file="../../template/foot.jsp" />
