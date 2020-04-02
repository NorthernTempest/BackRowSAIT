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
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <p>Sign below to verify all the details above.</p>
            <div class="wrapper">
                <canvas id="signature-pad" class="signature-pad" width=400 height=200></canvas>
            </div>
            <button type="button" class="btn btn-primary" onclick="sendSignature()">Send Signature</button>
        </div>
        <div class="col-md-6">
            <br>
            <br>
            <button type="button" class="btn btn-light" onclick="signaturePad.clear();">Clear</button>
        </div>
    </div>
</div>

<script src="../../template/js/signaturepad.js" type="text/javascript"></script>
<script>
    let canvas = document.getElementById("signature-pad");
    let signaturePad = new SignaturePad(canvas);
    canvas.style.background = "#ffffff";

    function resizeCanvas() {
        var ratio =  Math.max(window.devicePixelRatio || 1, 1);
        canvas.width = canvas.offsetWidth * ratio;
        canvas.height = canvas.offsetHeight * ratio;
        canvas.getContext("2d").scale(ratio, ratio);
    }

    window.onresize = resizeCanvas;
    resizeCanvas();

    function sendSignature() {
        let url = "/parcel/signDoc"
        let form = new FormData();

        form.append("parcelID", "${parcelID}");
        canvas.toBlob(function(blob) {
            form.append("signature", blob, "signature.png");
        });
        $.ajax({
            url: url,
            type: 'post',
            data: form,
            cache: false,
            contentType: false, //required for multipart
            processData: false  //required for multipart
        }).done(function( data ) {
            window.location = "/inbox?successMessage=Document <b>${parcel.subject}</b> successfully signed";
        });
    }

    function clearSignature() {

    }


</script>
<jsp:directive.include file="../../template/foot.jsp" />
