<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="col-12">
    <a href="/parcel/create?parcelID=${parcel.parcelID}" class="btn btn-primary" role="button">Reply</a>
</div>
<div class="col-12">
	<h4>${parcel.subject} - ${parcel.sender}</h4>
	<b>Date Sent:</b> ${parcel.dateSent}<br> <b>Expiry Date:</b>
	${parcel.expirationDate}<br>
	<p>${parcel.message}</p>

    <c:forEach var="doc" items="${parcel.documents}">
        ${doc.filePath}<br>
    </c:forEach>

</div>

<jsp:directive.include file="../../template/foot.jsp" />
