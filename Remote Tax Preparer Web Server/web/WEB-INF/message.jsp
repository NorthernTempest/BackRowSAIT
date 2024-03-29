<%--
  User: Kerk Day
  Date: 2020-02-10
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file="../template/head.jsp" />

<div>${message}</div>

<script>
    if(${redirectTimer} > 0) {
        setTimeout(function() {
            document.location = '${redirectLocation}';
        }, ${redirectTimer})
    }
</script>

<jsp:directive.include file = "../template/foot.jsp" />
