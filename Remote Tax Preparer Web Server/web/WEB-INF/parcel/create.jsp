<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="row">
<div class="col-12">
    <form action="" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="taxYear">Tax Year</label>
            <select name="taxYear" id="taxYear" class="form-control" value="${taxYear}">
            </select>
            <label for="subject">Subject</label>
            <input name="subject" id="subject" class="form-control" value="${subject}">
        </div>
        <div class="form-group">
            <div id="files"></div>
            <div><button class="btn btn-primary" onclick="addFile()">Add File</button></div>
        </div>
        <div class="form-group">
            <label for="message">Message</label>
            <textarea name="message" id="message" class="text-area">${message}</textarea>
        </div>
    </form>
</div>
</div>

<script>
    const yearEl = document.getElementById("taxYear");
    const today = new Date().getFullYear();
    for(let y = today-1; y > today-6; y--) {
        let option = document.createElement("option");
        option.value=y;
        option.value=y;
        yearEl.appendChild(option);
    }

    const filesEl = document.getElementById("files");
    addFile();
    function addFile() {
        if(filesEl.childElementCount > 10) {
            let file = document.createElement("input");
            file.type = "file";
            file.name = "multiPartServlet";
            filesEl.appendChild(file);
        }
    }

    function removeFile() {
        if(filesEl.childElementCount < 1) {
            filesEl.pop();
        }
    }

</script>

<jsp:directive.include file="../../template/foot.jsp" />
