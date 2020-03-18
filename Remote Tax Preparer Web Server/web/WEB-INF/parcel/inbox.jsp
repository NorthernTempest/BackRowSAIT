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
	<table id="tableDom" class="table table-hover table-dark">
		<tr>
			<th scope="col">Subject</th>
			<th scope="col">Message</th>
			<th scope="col">Date Sent</th>
			<th scope="col">Attached</th>
			<th scope="col">Expiration Date</th>
		</tr>
	</table>

</div>

<script>
    const parcels = [
        <c:forEach var="parcel" items="${parcels}">
        {
            "parcelID": "${parcel.parcelID}",
            "sender": "${parcel.sender}",
            "receiver": "${parcel.receiver}",
            "subject": "${parcel.subject}",
            "message": "${parcel.message}",
            "dateSent": Date.parse("${parcel.dateSent}"),
            "dateSentString": "${parcel.dateSent}",
            "noOfDocuments": ${parcel.documents.size()},
            "documents":[
                <c:forEach var="doc" items="${parcel.documents}">
                "${doc.fileName}",
                </c:forEach>
                ""
            ],
            "expirationDate": Date.parse("${parcel.expirationDate}"),
            "expirationDateString": "${parcel.expirationDate}",
            "taxReturnYear": ${parcel.taxReturnYear},
            "requiresSignature": ${parcel.requiresSignature? true : false }
        },
        </c:forEach>
        {}
    ];
    parcels.pop();

    window.onload = function() {
        fillTable(null);
    }

    function fillTable(sort) {fillTable(sort, null)}
    function fillTable(sort, filter) {
        let table = parcels;

        if (typeof filter === 'undefined') filter = "";
        if (typeof filter.year !== 'undefined') {
            let tempTable = [];
            for(let parcel in table) {
                if (parcel.taxReturnYear === filter.year)
                    tempTable.push(parcel);
            }
            table = tempTable;
        }
        if (typeof filter.documents !== 'undefined') {
            let tempTable = [];
            for(let parcel in table) {
                if (parcel.noOfDocuments >= filter.documents)
                    tempTable.push(parcel);
            }
            table = tempTable;
        }
        if (typeof filter.reqSig !== 'undefined') {
            let tempTable = [];
            for(let parcel in table) {
                if (parcel.requiresSignature == true)
                    tempTable.push(parcel);
            }
            table = tempTable;
        }
        if(typeof sort === 'undefined') {
            sort = "dateSent";
        }
        table.sort((a, b) => (a[sort] > b[sort]) ? 1 : -1);

        //Print out the results.
        let tableDom = document.getElementById("tableDom");

        if (table.length < 1) {
            tableDom.innerText = "Nothing here!";
        } else {
            tableDom.innerHTML = `<tr>
            <th scope="col">Subject</th>
            <th scope="col">Message</th>
            <th scope="col">Date Sent</th>
            <th scope="col">Attached Documents</th>
            <th scope="col">Expiration Date</th>
        </tr>`;

            for (let parcel in table) {
                let row = document.createElement("tr");
                row.addEventListener('click', function() {
                    goto('/parcel/view?parcelID=' + table[parcel].parcelID);
                });

                let rowSubj = document.createElement("td");
                rowSubj.innerText = table[parcel].subject;
                rowSubj.scope = "row";
                row.appendChild(rowSubj);

                let rowMessage = document.createElement("td");
                rowMessage.innerText = table[parcel].message;
                row.appendChild(rowMessage);

                let rowDate = document.createElement("td");
                rowDate.innerText = table[parcel].dateSentString;
                row.appendChild(rowDate);

                let rowDocs = document.createElement("td");
                rowDocs.innerText = table[parcel].noOfDocuments;
                row.appendChild(rowDocs);

                let rowExpire = document.createElement("td");
                rowExpire.innerText = table[parcel].expirationDateString;
                row.appendChild(rowExpire);

                tableDom.appendChild(row);
            }
        }

    }

    function getQuery(tag) {
        let url = window.location.search.substring(1);
        let tags = url.split("&");
        for (let x in tags) {
            let pair = x.split("=");
            if (pair[0] === tag) {
                return decodeURIComponent(pair[1]);
            }
        }

    }

    function goto(where) {
        window.location = where;
    }

</script>

<jsp:directive.include file="../../template/foot.jsp" />
