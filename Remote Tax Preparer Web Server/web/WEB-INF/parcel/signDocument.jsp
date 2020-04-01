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
            <h1>${parcel.subject}</h1>
            <p>${parcel.documents.get(0).fileName}</p>
            <object data="/parcel/document?parcelID=${parcel.parcelID}" type="application/pdf" width="100%" height="700px">
                Not a valid PDF! <a href="https://www.mcltaxes.com/contact">Contact us</a> for further options.
            </object>
            <br>
            <p>Sign below to verify all the details above.</p>
            <div class="wrapper">
                <canvas id="signature-pad" class="signature-pad" width=400 height=200></canvas>
            </div>
        </div>
    </div>
</div>

<script src="../../template/js/signaturepad.js" type="text/javascript"></script>
<script>
    let canvas = document.getElementById("signature-pad");

</script>
<jsp:directive.include file="../../template/foot.jsp" />
