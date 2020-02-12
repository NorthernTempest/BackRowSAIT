<%--
  User: Kerk Day
  Date: 2020-02-12
  Time: 3:53 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../../template/head.jsp" />

<div class="col-12">
    <c:choose>
        <c:when test="${successMessage!=null}">
            <span class="alert alert-success">
                    ${successMessage}
            </span>
        </c:when>
        <c:when test="${errorMessage!=null}">
            <span class="alert alert-danger">
                    ${errorMessage}
            </span>
        </c:when>
    </c:choose>
</div>
<div class="col-md-6">
    <h2>
        ${user.title}. ${user.f_name} ${user.l_name}
    </h2>

</div>

<jsp:directive.include file = "../../template/foot.jsp" />