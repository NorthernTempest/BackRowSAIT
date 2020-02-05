<%--
  User: Kerk Day
  Date: 2020-02-05
  Time: 12:30 PM
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:directive.include file = "../template/head.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <form action="" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="example@email.com" class="form-control" value="${email}">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Log in</button>
                    <c:choose>
                        <c:when test="${errorMessage!=null}">
                            <div class="alert alert-danger" role="alert">
                                    ${errorMessage}
                            </div>
                        </c:when>
                    </c:choose>
                </div>
            </form>
        </div>
        <div class="col-md-6 align-middle text-center">
            Don't have an account?<br>
            <button class="btn btn-primary">
                Register here!
            </button>
        </div>
    </div>
</div>


<jsp:directive.include file = "../template/foot.jsp" />