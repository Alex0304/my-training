<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Demo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css">
    <script language="javascript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<h4>Demo Page</h4>

<c:if test="${not empty msg}">
    <div class="alert alert-info" role="alert">Message: ${msg}</div>
</c:if>

<div class="alert alert-success" role="alert" id="message"></div>

<script type="text/javascript">
    $(document).ready(function(){
        console.log("jQuery is running");

        $("#message").html("jQuery is running");
    });
</script>
</body>
</html>