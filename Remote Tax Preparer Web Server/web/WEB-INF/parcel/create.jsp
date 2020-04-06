<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<form action="" method="post" enctype="multipart/form-data">
<div class="container">
    <c:choose>
        <c:when test="${parcel!=null}">
            <div class="row" style="background: #aaa; color: black">
                <div class="col-12">
                    <h3>Replying to:</h3>
                    <b>Subject: </b>${parcel.subject}<br>
                    <c:choose>
                        <c:when test="${parcel.taxReturnYear!=null}">
                            <b>Tax Year: </b>${parcel.taxReturnYear}<br>
                            <input type="hidden" name="taxYear" value="${parcel.taxReturnYear}">
                        </c:when>
                    </c:choose>
                    <b>Message: </b>${parcel.message}
                </div>
            </div>
        </c:when>
    </c:choose>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <input type="hidden" name="parcelID" value="${parcel.parcelID}">
                <input type="hidden" name="receiver" id="receiver" class="form-control" value="${sendTo}">
                <c:choose>
                    <c:when test="${parcel.taxReturnYear==null}">
                        <label for="taxYear">Tax Year</label>
                        <select name="taxYear" id="taxYear" class="form-control" value="${parcel.taxReturnYear}">
                        </select>
                    </c:when>
                </c:choose>
                <label for="subject">Subject</label>
                <input name="subject" id="subject" class="form-control" value="${parcel.subject==null?"":"RE: "}${parcel.subject}">

            </div>
            <div class="form-group form-check">
                <c:choose>
                    <c:when test="${role > 1}">
                        <input type="checkbox" class="form-check-input" name="reqSig" id="reqSig" value="y" ${reqSig?"checked":""}>
                        <label class="form-check-label" for="reqSig">Requires a Signature</label>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label>Attachments</label>
                <div class="card">
                    <div id="files"></div>
                    <div><button type="button" class="btn btn-dark btn-sm" onclick="addFile()">More Files</button></div>
                </div>
            </div>
        </div>
        <div class="col-12">
            <label for="message">Message</label>
            <textarea name="message" id="message" class="form-control">${message}</textarea>
            <br>
        </div>
        <div class="col-12">
            <c:choose>
                <c:when test="${parcel!=null}">
                    <button type="submit" class="btn btn-primary">Send Reply</button>
                </c:when>
                <c:when test="${parcel==null}">
                    <button type="submit" class="btn btn-primary">Send Message</button>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
</form>

<script>
    let filesEl;

    window.onload = function() {
        filesEl = document.getElementById("files");
        addFile();
    }

    function addFile() {
        if (filesEl.childElementCount < 10) {
            let fileEl = document.createElement("input");
            fileEl.type = "file";
            fileEl.name = "multiPartServlet";
            fileEl.className = "form-control-file";
            filesEl.appendChild(fileEl);
        }
    }

    function removeFile() {
        if(filesEl.childElementCount > 1) {
            filesEl.pop();
        }
    }

</script>

<jsp:directive.include file="../../template/foot.jsp" />
