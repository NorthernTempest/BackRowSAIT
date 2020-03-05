<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="col-12">
    <h3>
        Inbox
    </h3>
    <a class="btn btn-primary" href="/parcel/create" role="button">Create Return</a>
</div>
<div class="col-12">
	<table class="table table-hover table-dark">
		<tr>
			<th scope="col">From</th>
			<th scope="col">Subject</th>
			<th scope="col">Message</th>
			<th scope="col">Date Sent</th>
			<th scope="col">Attached</th>
			<th scope="col">Expiration Date</th>
		</tr>
		<c:forEach var="parcel" items="${parcels}">
			<tr
				onclick="window.location='/parcel/view?parcelID=${parcel.parcelID}'">
				<td scope="row">${parcel.sender}</td>
				<td>${parcel.subject}</td>
				<td>${parcel.message.length()>20?parcel.message.substring(0,20)+"...":parcel.message}</td>
				<td>${parcel.dateSent}</td>
				<td>${parcel.documents.size()}</td>
				<td>${parcel.expirationDate}</td>
			</tr>
		</c:forEach>
	</table>

</div>

<script>
    const parcels = [
        <c:forEach var="parcel" items="${parcels}">
        {
            "sender": "${parcel.sender}",
            "receiver": "${parcel.receiver}",
            "subject": "${parcel.subject}",
            "message": "${parcel.message.length()>20?parcel.message.substring(0,20)+"...":parcel.message}",
            "dateSent": Date.parse("${parcel.dateSent}"),
            "noOfDocuments": ${parcel.documents.size()},
            "expirationDate": Date.parse("${parcel.expirationDate}"),
            "taxReturnYear": ${parcel.taxReturnYear},
            "requiresSignature": ${parcel.requiresSignature?"true":"false"}
        },
        </c:forEach>
        {}
    ];
    parcels.pop();


</script>

<jsp:directive.include file="../../template/foot.jsp" />
