<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="row">
<div class="col-md-6">
    <form action="" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="taxYear">Tax Year</label>
            <select name="taxYear" id="taxYear" class="form-control" value="${taxYear}">
            </select>
            <label for="subject">Subject</label>
            <input name="subject" id="subject" class="form-control" value="${subject}">
        </div>
        <div class="form-group">
            <div class="card">
                    <div id="files"></div>
                    <div><button type="button" class="btn btn-primary" onclick="addFile()">More Files</button></div>
            </div>
        </div>
        <div class="form-group">
            <label for="message">Message</label>
            <textarea name="message" id="message" class="form-control">${message}</textarea>
        </div>
        <div class="form-group">
            <button class="btn btn-primary">Submit</button>
        </div>
    </form>
</div>
</div>

<script>

    let yearEl;
    let today = new Date().getFullYear();
    let filesEl;

    window.onload = function() {
        yearEl = document.getElementById("taxYear");
        filesEl = document.getElementById("files");

        for (let y = today - 1; y > today - 6; y--) {
            console.log(y);
            let opt = document.createElement("option");
            opt.value = ""+y;
            opt.textContent = ""+y;
            yearEl.appendChild(opt);
        }
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
