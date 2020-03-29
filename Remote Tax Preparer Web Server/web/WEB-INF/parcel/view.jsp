<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="container">
	<div class="row" style="background: none">
		<div class="col-sm-10">
			<h1>${parcel.subject}</h1>
		</div>
		<div class="col-sm-2">
			<a href="/parcel/create?parcelID=${parcel.parcelID}" class="btn btn-primary" role="button" style="margin-top:10px;">Reply</a>
		</div>
	</div>
	<div class="row" style="border-radius: 5px 5px 0 0;">
		<div class="col-md-6">
			<b>From:</b> ${parcel.sender}<br>
			<c:choose>
				<c:when test="${parcel.receiver!=null}">
					<b>To:</b> ${parcel.receiver}<br>
				</c:when>
			</c:choose>
			<b>Date Sent:</b> ${parcel.dateSent}<br>
			<c:choose>
				<c:when test="${parcel.expirationDate!=null}">
					<b>Expiry Date:</b>${parcel.expirationDate}<br>
				</c:when>
			</c:choose>

		</div>
		<div class="col-md-6">
			<c:forEach var="doc" items="${parcel.documents}">
				<form action="/parcel/view" method="post">
					<input type="hidden" name="filePath" value="${doc.filePath}">
					<button type="submit" class="btn btn-link btn-sm"><i class="fas fa-file"></i> ${doc.fileName}</button>
				</form>

			</c:forEach>
		</div>
	</div>
	<div class="row" style="border-radius: 0 0 5px 5px;">
		<div class="col-12">
			<br>
			<p>${parcel.message}</p>
		</div>
	</div>
</div>

<jsp:directive.include file="../../template/foot.jsp" />
