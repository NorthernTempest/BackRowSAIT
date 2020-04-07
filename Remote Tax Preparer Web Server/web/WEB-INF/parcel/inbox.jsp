<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="col-12">
    <h3>Inbox</h3>
<%--    <a class="btn btn-primary" href="/parcel/create" role="button">New Message</a>--%>
</div>
<div class="row" style="background: none;">
	<div class="col-md-6">
		<div class="form-group">
			<label for="taxYear">Tax Year</label>
			<select name="taxYear" id="taxYear" class="form-control" value="${taxYear}" onclick="updateFilter(this)">
			</select>
		</div>
	</div>
	<div class="col-md-6">
		<br>
		<div class="form-check">
			<input type="checkbox" class="form-check-input" id="hasDocuments" onclick="updateFilter(this)">
			<label class="form-check-label" for="hasDocuments">Has documents</label>
		</div>
		<div class="form-check">
			<input type="checkbox" class="form-check-input" id="reqSig" onclick="updateFilter(this)">
			<label class="form-check-label" for="reqSig">Requires Signature</label>
		</div>
		<div class="form-check">
			<input type="checkbox" class="form-check-input" id="sentMail" onclick="updateFilter(this)">
			<label class="form-check-label" for="sentMail">Sent Messages</label>
		</div>
	</div>
</div>
<div class="row" style="background: none;">
	<div class="col-12">
		<table class="table table-hover table-dark">
			<thead>
				<tr>
					<th scope="col" onclick="fillTable('subject')" id="subjectSort">Subject</th>
					<th scope="col">Message</th>
					<th scope="col" onclick="fillTable('dateSentString')" id="dateSort">Date Sent</th>
					<th scope="col" onclick="fillTable('noOfDocuments')" id="attachSort">Attached</th>
					<th scope="col" onclick="fillTable('expirationDateString')" id="expireSort">Expiration Date</th>
				</tr>
			</thead>
			<tbody id="tableDom">

			</tbody>
		</table>
	</div>
</div>
<script>
	const user = "${user}";
    const parcels = [
        <c:forEach var="parcel" items="${parcels}">
        {
            "parcelID": "${parcel.parcelID}",
            "sender": "${parcel.sender}",
            "receiver": "${parcel.receiver}",
            "subject": "${parcel.subject}",
            "message": '${parcel.message.replaceAll("[\\n\\r\\f]{1,}[\\w\\W]*","...")}',
            "dateSent": Date.parse("${parcel.dateSent}"),
            "dateSentString": "<fmt:formatDate value="${parcel.dateSent}" pattern="yyyy-MM-dd HH:mm" />",
            "noOfDocuments": "${parcel.documents.size()}",
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

	let savedSort = "";
	let filter = {
		"year": (today-1).toString(),
		"documents": false,
		"reqSig": false,
		"sentMail": false
	};

	window.onload = function() {
		yearEl = document.getElementById("taxYear");
		let years = [(today-1).toString()];

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
		document.getElementById("sentMail").checked = false;

        fillTable("-dateSentString");
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
			case "sentMail":
				filter.sentMail = !filter.sentMail;
				break;
		}
		fillTable("+"+savedSort);
	}

    function fillTable(sort) {
        let table = parcels;

        if (typeof filter.year !== 'undefined') {
            let tempTable = [];
            for(let parcel in table) {
                if (table[parcel].taxReturnYear === filter.year) {
					tempTable.push(table[parcel]);
				} else {
				}
            }
            table = tempTable;
            console.log(table);
        }
        if (filter.documents) {
            let tempTable = [];
            for(let parcel in table) {
                if (table[parcel].noOfDocuments >= 1)
                    tempTable.push(table[parcel]);
            }
            table = tempTable;
			console.log(table);
        }
        if (filter.reqSig) {
            let tempTable = [];
            for(let parcel in table) {
                if (table[parcel].requiresSignature)
                    tempTable.push(table[parcel]);
            }
            table = tempTable;
			console.log(table);
        }
		if (filter.reqSig) {
			let tempTable = [];
			for(let parcel in table) {
				if (table[parcel].requiresSignature)
					tempTable.push(table[parcel]);
			}
			table = tempTable;
			console.log(table);
		}
		if (!filter.sentMail) {
			let tempTable = [];
			for(let parcel in table) {
				if (table[parcel].sender !== user)
					tempTable.push(table[parcel]);
			}
			table = tempTable;
			console.log(table);
		}

		if (savedSort.charAt(0) === '+') {
			savedSort = sort.replace(/\+/g, "");
		} else if(savedSort === sort) {
			savedSort = "-"+sort.replace(/\+/g, "");
		} else {
			savedSort = sort.replace(/\+/g, "");
		}

		document.getElementById("subjectSort").innerText = "Subject";
		document.getElementById("dateSort").innerText = "Date Sent";
		document.getElementById("attachSort").innerText = "Attached";
		document.getElementById("expireSort").innerText = "Expiration Date";

		let arrow = "<b>↑</b>";
        if(savedSort.charAt(0) !== '-') {
			arrow = "<b>↓</b>";
		}

        if (table.length > 1) {
			table.sort(dynamicSort(savedSort));
		}

        switch (savedSort) {
        	case "subject":
			case "-subject":
				document.getElementById("subjectSort").innerHTML = "Subject "+arrow;
				break;
			case "dateSentString":
			case "-dateSentString":
				document.getElementById("dateSort").innerHTML = "Date Sent "+arrow;
				break;
			case "noOfDocuments":
			case "-noOfDocuments":
				document.getElementById("attachSort").innerHTML = "Attached "+arrow;
				break;
			case "expirationDateString":
			case "-expirationDateString":
				document.getElementById("expireSort").innerHTML = "Expiration Date "+arrow;
				break;
		}

        //Print out the results.
        let tableDom = document.getElementById("tableDom");

        tableDom.innerHTML = "";

        if (table.length < 1) {
            tableDom.innerText = "Nothing here!";
        } else {
            for (let parcel in table) {
                let row = document.createElement("tr");
                row.addEventListener('click', function() {
                    window.location = '/parcel/view?parcelID=' + table[parcel].parcelID;
                });

                let rowSubj = document.createElement("td");
                rowSubj.innerText = table[parcel].subject;
                if (table[parcel].requiresSignature) {
					rowSubj.innerHTML += `
					<div class='inboxIcon'>
						<i class="fas fa-file-signature"></i>
						<span>Requires Signature</span>
					</div>
					`;
				}
				if (table[parcel].sender === user) {
					rowSubj.innerHTML += `
					<div class='inboxIcon'>
						<i class="fas fa-share"></i>
						<span>Sent</span>
					</div>
					`;
				}
                rowSubj.scope = "row";
                row.appendChild(rowSubj);

                let rowMessage = document.createElement("td");
                rowMessage.innerHTML = table[parcel].message;
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

	function toggleCheckbox(element) {
		element.checked = !element.checked;
	}

	function dynamicSort(property) {
		var sortOrder = 1;

		if(property[0] === "-") {
			sortOrder = -1;
			property = property.substr(1);
		}
		if(typeof property === 'number'){

		} else {
			return function (a, b) {
				if (sortOrder == -1) {
					return b[property].localeCompare(a[property]);
				} else {
					return a[property].localeCompare(b[property]);
				}
			}
		}
	}

</script>

<jsp:directive.include file="../../template/foot.jsp" />
