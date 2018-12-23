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
        <button type="submit">Sign me in</button>
        <div class="error"><span>+</span></div>
    </form>
</div>

<!-- Javascript -->
<script src="${pageContext.request.contextPath}/backLogin/js/jquery-1.8.2.min.js"></script>
<script src="${pageContext.request.contextPath}/backLogin/js/supersized.3.2.7.min.js"></script>
<script src="${pageContext.request.contextPath}/backLogin/js/scripts.js"></script>
<script>
    jQuery(function($){

        $.supersized({

            // Functionality
            slide_interval     : 4000,    // Length between transitions
            transition         : 1,    // 0-None, 1-Fade, 2-Slide Top, 3-Slide Right, 4-Slide Bottom, 5-Slide Left, 6-Carousel Right, 7-Carousel Left
            transition_speed   : 1000,    // Speed of transition
            performance        : 1,    // 0-Normal, 1-Hybrid speed/quality, 2-Optimizes image quality, 3-Optimizes transition speed // (Only works for Firefox/IE, not Webkit)

            // Size & Position
            min_width          : 0,    // Min width allowed (in pixels)
            min_height         : 0,    // Min height allowed (in pixels)
            vertical_center    : 1,    // Vertically center background
            horizontal_center  : 1,    // Horizontally center background
            fit_always         : 0,    // Image will never exceed browser width or height (Ignores min. dimensions)
            fit_portrait       : 1,    // Portrait images will not exceed browser height
            fit_landscape      : 0,    // Landscape images will not exceed browser width

            // Components
            slide_links        : 'blank',    // Individual links for each slide (Options: false, 'num', 'name', 'blank')
            slides             : [
                {image : '/backLogin/img/backgrounds/3.jpg'}
            ]

        });

    });
</script>
</body>

</html>
