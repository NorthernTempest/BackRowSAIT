<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>MCL Taxes &amp; Bookkeeping</title>
<?-- CSS -->
    <link rel="stylesheet" href="../template/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../template/css/mcl-main.css">
    <?-- Javascript -->
    <script type="text/javascript" src="../template/js/jQuery.min.js"></script>
    <script type="text/javascript" src="../template/js/bootstrap.js"></script>
    <script src="https://kit.fontawesome.com/1213a7076a.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="main">
    <div class="text-center" id="logo">
        <a href="https://www.mcltaxes.com/"><img id="logo-image" src="../template/img/MCL-logo-large.png" width="283px"/></a>
        <i id="burger" class="fas fa-bars" onclick="toggleNav()"></i>
    </div>

    <div class="block text-center" id="nav">

        <span class="nav-item">
            <a href="/">Home</a>
        </span>
        <span class="nav-first">
            <a href="/login"><button class="btn btn-primary btn-lg"><b>Login</b></button></a>
        </span>
    </div>
    <hr class="nav-separator">
    <div class="block" id="content">
        <div class="row">
            <div class="col-12">
                <c:choose>
                    <c:when test="${successMessage!=null}">
                        <div class="alert alert-success">
                                ${successMessage}
                        </div>
                    </c:when>
                    <c:when test="${errorMessage!=null}">
                        <div class="alert alert-danger">
                                ${errorMessage}
                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>