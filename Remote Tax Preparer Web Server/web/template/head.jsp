<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>MCL Taxes &amp; Bookkeeping</title>
<?-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/template/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/template/css/mcl-main.css">
    <?-- Javascript -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/template/js/jQuery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/template/js/bootstrap.js"></script>
    <script src="https://kit.fontawesome.com/1213a7076a.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="main">
    <div class="text-center" id="logo">
        <a href="https://www.mcltaxes.com/"><img id="logo-image" src="${pageContext.request.contextPath}/template/img/MCL-logo-large.png" width="283px"/></a>
        <i id="burger" class="fas fa-bars" onclick="toggleNav()"></i>
    </div>

    <div class="block text-center" id="nav">
		<c:if test="${role == 1}">
        <span class="nav-first">
            <a href="/createReturn"><button class="btn btn-primary btn-lg"><b>Create Return</b></button></a>
        </span></c:if>
        <c:if test="${role == 1}">
        <span class="nav-item">
            <a href="/return">Returns</a>
        </span>
            <span class="nav-separator">|</span>
        </c:if>
        <span class="nav-item">
            <a href="/inbox">Inbox</a>
        </span>
        <span class="nav-separator">|</span>
        <span class="nav-item">
            <a href="/settings">Account</a>
        </span>
		<c:if test="${role > 1}">
			<span class="nav-separator">|</span>
        	<span class="nav-item">
            	<a href="/reports">Reports</a>
        	</span>
		</c:if>
        <c:if test="${role == 1}">
            <span class="nav-separator">|</span>
            <span class="nav-item">
            	<a href="/payments">Payments</a>
        	</span>
        </c:if>
		<c:if test="${role > 2}">
			<span class="nav-separator">|</span>
        	<span class="nav-item">
            	<a href="/admin">Admin</a>
        	</span>
		</c:if>
        <span class="nav-separator">|</span>
        <span class="nav-item">
            <a href="/login?action=logout">Logout</a>
        </span>
    </div>
    <hr class="nav-separator">
    <div class="block" id="content">
        <c:choose>
            <c:when test="${successMessage!=null}">
            <div class="row">
                <div class="col-12">
                    <div class="alert alert-success">
                            ${successMessage}
                    </div>
                </div>
            </div>
            </c:when>
            <c:when test="${errorMessage!=null}">
                <div class="row">
                    <div class="col-12">
                        <div class="alert alert-warning">
                                ${errorMessage}
                        </div>
                    </div>
                </div>
            </c:when>
        </c:choose>