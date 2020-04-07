<%--
  User: Kerk Day
  Date: 2020-02-05
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<style>
    td:first-child {
        width: 35%;
        text-align: right;
    }
</style>

<div class="container">
    <div class="row" style="background: none;">
        <div class="col-md-8" style="background: black;border-radius: 5px">
            <h2>Name</h2>
            <table class="table table-dark table-borderless">
                <tbody>
                <tr>
                    <td><b>First Name</b></td>
                    <td>${user.getFName()}</td>
                </tr>
                <tr>
                    <td><b>Middle Name</b></td>
                    <td>${user.getMName()}</td>
                </tr>
                <tr>
                    <td><b>Last Name</b></td>
                    <td>${user.getLName()}</td>
                </tr>
                </tbody>
            </table>
            <h2>Contact Details</h2>
            <table class="table table-dark table-borderless">
                <tbody>
                <tr>
                    <td><b>Email</b></td>
                    <td><a href="mailto:${user.email}">${user.email}</a></td>
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
            <c:if test="${role > 1}">
            <h2>Website Permissions</h2>
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
            </c:if>
            <h2>Address</h2>
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
        </div>
        <div class="col-md-4 text-md-right align-content-md-end">
            <c:if test="${role > 1 && user.permissionLevel == 1}">
                <a href="/return">
                    <button class="btn btn-success" type="button">Charge User</button>
                </a>
                <br>
                <br>
            </c:if>
            <c:if test="${role > 1}">
                <a href="/settings?email=${user.email}">
                    <button class="btn btn-primary" type="button">Edit User</button>
                </a>
                <br>
                <br>
            </c:if>
            <c:if test="${role > 2 && !user.active}">
                <form action="" method="post">
                    <input type="hidden" name="action" value="activate">
                    <input type="hidden" name="email" value="${user.email}">
                    <button class="btn btn-primary" type="submit">Activate User</button>
                </form>
                <br>
            </c:if>
            <c:if test="${role > 2 && user.active}">
                <form action="" method="post">
                    <input type="hidden" name="action" value="deactivate">
                    <input type="hidden" name="email" value="${user.email}">
                    <button class="btn btn-danger" type="submit">Deactivate User</button>
                </form>
                <br>
            </c:if>
            <c:if test="${role == 1 && user.active}">
                <form action="" method="post">
                    <input type="hidden" name="action" value="deactivate">
                    <input type="hidden" name="email" value="${user.email}">
                    <button class="btn btn-danger" type="submit">Delete Account</button>
                </form>
                <br>
            </c:if>
        </div>
    </div>
</div>

<jsp:directive.include file="../../template/foot.jsp" />