<%--
  Created by IntelliJ IDEA.
  User: Kerk Day
  Date: 2020-03-27
  Time: 9:42 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../../template/head.jsp" />

<div class="container">
    <div class="">
        <div class="col-12">
            <p>${parcel.subject}</p>
            <p>${parcel.documents.get(0).filePath}</p>

            <object data="/DocumentServlet?filePath=${parcel.documents.get(0).filePath}" type="application/pdf" width="100%" height="700px">
                alt : <a href="/DocumentServlet?filePath=${parcel.documents.get(0).filePath}">${parcel.documents.get(0).fileName}</a>
            </object>
        </div>
    </div>
</div>

<jsp:directive.include file="../../template/foot.jsp" />
