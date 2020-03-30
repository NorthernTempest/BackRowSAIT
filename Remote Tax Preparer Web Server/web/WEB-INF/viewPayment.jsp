<%--
  Created by IntelliJ IDEA.
  User: Kerk Day
  Date: 2020-03-30
  Time: 12:08 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<div class="container">
    <div class="row">
        <div class="col-12">
            <table id="payments">
                <thead>
                    <tr>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="payment" items="payments">
                    <tr>
                        <td>${payment.year}</td>
                        <td>${payment.paymentType}</td>
                        <td>$${payment.amount}</td>
                        <td>${payment.date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:directive.include file="../template/foot.jsp" />
