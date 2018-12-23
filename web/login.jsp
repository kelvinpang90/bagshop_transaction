<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" class="no-js">

<head>

    <meta charset="utf-8">
    <title>My Paris Bags</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/backLogin/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/backLogin/css/supersized.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/backLogin/css/style.css">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/bootstrap/js/html5shiv.js"></script>
    <![endif]-->

</head>

<body>

<div class="page-container">
    <h1>My Paris Bags</h1>
    <form action="${pageContext.request.contextPath}/admin/login.do" method="post" id="theForm">
        <input type="text" name="loginName" class="username" placeholder="Username">
        <input type="password" name="password" class="password" placeholder="Password">
        <button type="submit">Submit</button>
        <div class="error"><span>+</span></div>
    </form>
</div>

<!-- Javascript -->
<script src="${pageContext.request.contextPath}/backLogin/js/jquery-1.8.2.min.js"></script>
</body>

</html>
