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
    <a class="btn btn-primary" href="/parcel/create" role="button">New Message</a>
    <br>
</div>
<div class="col-12">
	<div class="form-group">
		<label for="taxYear">Tax Year</label>
		<select name="taxYear" id="taxYear" class="form-control" value="${taxYear}" onclick="updateFilter(this)">
		</select>
	</div>
	<div class="form-check">
		<input type="checkbox" class="form-check-input" id="hasDocuments" onclick="updateFilter(this)">
		<label class="form-check-label" for="hasDocuments">Has documents</label>
	</div>
	<div class="form-check">
		<input type="checkbox" class="form-check-input" id="reqSig" onclick="updateFilter(this)">
		<label class="form-check-label" for="reqSig">Requires Signature</label>
	</div>
	<table class="table table-hover table-dark">
		<thead>
			<tr>
				<th scope="col" onclick="fillTable('subject')">Subject</th>
				<th scope="col">Message</th>
				<th scope="col" onclick="fillTable('dateSentString')">Date Sent</th>
				<th scope="col" onclick="fillTable('noOfDocuments')">Attached</th>
				<th scope="col" onclick="fillTable('expirationDateString')">Expiration Date</th>
			</tr>
		</thead>
		<tbody id="tableDom">

		</tbody>
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
            "taxReturnYear": "${parcel.taxReturnYear}",
            "requiresSignature": ${parcel.requiresSignature? true : false }
        },
        </c:forEach>
        {}
    ];
    parcels.pop();

	let yearEl;
	let today = new Date().getFullYear();

	let savedSort;
	let filter = {
		"year": (today-1).toString(),
		"documents": false,
		"reqSig": false
	};

	window.onload = function() {
		yearEl = document.getElementById("taxYear");
		let years = [];

		for (let parcel in parcels) {
			if(years.indexOf(parcels[parcel].taxReturnYear) === -1) {
				years.push(parcels[parcel].taxReturnYear);
			}
		}
		for (let year in years) {
			let opt = document.createElement("option");
			opt.value = ""+years[year];
			opt.textContent = ""+years[year];
			yearEl.appendChild(opt);
		}

		document.getElementById("hasDocuments").checked = false;
		document.getElementById("reqSig").checked = false;

        fillTable(null);
    }

	function updateFilter(el) {
		switch (el.id) {
			case "taxYear":
				let taxYearEl = document.getElementById("taxYear");
				filter.year = taxYearEl.options[taxYearEl.selectedIndex].value;
				break;
			case "hasDocuments":
				filter.documents = !filter.documents;
				break;
			case "reqSig":
				filter.reqSig = !filter.reqSig;
				break;
		}
		fillTable(savedSort);
	}

    function fillTable(sort) {
        let table = parcels;

        if(sort !== null && typeof sort !== 'undefined') savedSort = sort;
        else savedSort = "dateSent";

        if (typeof filter.year !== 'undefined') {
            let tempTable = [];
            for(let parcel in table) {
                if (table[parcel].taxReturnYear === filter.year) {
					tempTable.push(table[parcel]);
				} else {
				}
            }
            table = tempTable;
        }
        if (filter.documents) {
            let tempTable = [];
            for(let parcel in table) {
                if (table[parcel].noOfDocuments >= 1)
                    tempTable.push(table[parcel]);
            }
            table = tempTable;
        }
        if (filter.reqSig) {
            let tempTable = [];
            for(let parcel in table) {
                if (table[parcel].requiresSignature)
                    tempTable.push(table[parcel]);
            }
            table = tempTable;
        }
        table.sort((a, b) => (a[savedSort] > b[savedSort]) ? 1 : -1);

        //Print out the results.
        let tableDom = document.getElementById("tableDom");

        tableDom.innerHTML = "";

        if (table.length < 1) {
            tableDom.innerText = "Nothing here!";
        } else {
            for (let parcel in table) {
                let row = document.createElement("tr");
                row.addEventListener('click', function() {
                    fillTable(null, )
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

	function toggleCheckbox(element)
	{
		element.checked = !element.checked;
	}

</script>

<jsp:directive.include file="../../template/foot.jsp" />
