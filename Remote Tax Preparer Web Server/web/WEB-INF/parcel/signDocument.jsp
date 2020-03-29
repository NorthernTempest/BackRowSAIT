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
            <object data="/document?filePath=${parcel.documents.get(0).filePath}" type="application/pdf">
                <embed src="/document?filePath=${parcel.documents.get(0).filePath}" type="application/pdf" />
            </object>
        </div>
    </div>
</div>

<jsp:directive.include file="../../template/foot.jsp" />
