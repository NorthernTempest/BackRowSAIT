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
                "${doc.filename}",
                </c:forEach>
                ""
            ],
            "expirationDate": Date.parse("${parcel.expirationDate}"),
            "expirationDateString": Date.parse("${parcel.expirationDate}"),
            "taxReturnYear": ${parcel.taxReturnYear},
            "requiresSignature": ${parcel.requiresSignature? true : false }
        },
        </c:forEach>
        {}
    ];
    parcels.pop();

    function fillTable(sort) {fillTable(sort, null)}
    function fillTable(sort, filter) {
        let table = parcels;
        if (filter !== null) {
            if (filter.year !== null) {
                let tempTable = [];
                for(let parcel in table) {
                    if (parcel.taxReturnYear === filter.year) {
                        tempTable.push(parcel);
                    }
                }
                table = tempTable;
            }
            if (filter.documents !== null) {
                let tempTable = [];
                for(let parcel in table) {
                    if (parcel.noOfDocuments >= filter.documents) {
                        tempTable.push(parcel);
                    }
                }
                table = tempTable;
            }
            if (filter.reqSig !== null) {
                let tempTable = [];
                for(let parcel in table) {
                    if (parcel.requiresSignature == true) {
                        tempTable.push(parcel);
                    }
                }
                table = tempTable;
            }
        }
        if(sort === null || sort === "") {
            sort = "dateSent";
        }
        table.sort((a, b) => (a[sort] > b[sort]) ? 1 : -1);

        //Print out the results.
        let tableDom = document.getElementById("tableDom");

        tableDom.textContent = `<tr>
            <th scope="col">Subject</th>
            <th scope="col">Message</th>
            <th scope="col">Date Sent</th>
            <th scope="col">Attached</th>
            <th scope="col">Expiration Date</th>
        </tr>`;

        for (let parcel in table) {
            let row = document.createElement("tr");
            row.onclick = ("window.location = /parcel/view?parcelID="+parcel.parcelID);

            let rowSubj = document.createElement("td");
            rowSubj.innerText = parcel.subject;
            row.appendChild(rowSubj);

            let rowMessage = document.createElement("td");
            rowMessage.innerText = parcel.message;
            row.appendChild(rowMessage);

            let rowDate = document.createElement("td");
            rowDate.innerText = parcel.dateSentString;
            row.appendChild(rowDate);

            let rowDocs = document.createElement("td");
            rowDocs.innerText = parcel.noOfDocuments;
            row.appendChild(rowDocs);

            let rowExpire = document.createElement("td");
            rowExpire.innerText = parcel.expirationDateString;
            row.appendChild(rowExpire);
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

</script>

<jsp:directive.include file="../../template/foot.jsp" />
