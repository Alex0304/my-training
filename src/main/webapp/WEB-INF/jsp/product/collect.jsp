<%--
  Created by IntelliJ IDEA.
  User: DXM-0189
  Date: 2023/2/21
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    // 获取请求的上下文
    String context = request.getContextPath();
%>
<head>
    <title>商品链接采集</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
</head>
<script>
    function collectProduct(){
       var url = $("#productUrl").val();
        $.ajax({
            url: "<%=context %>/product/toCollect?url="+url,
            type: "get",
            success: function (result) {
                $("#productInfo").append(result);
            }
        });
    }
</script>
<body>
<div >
  商品链接:<input type="text" name="url" id="productUrl"><br>
    <button type="button" onclick="collectProduct()">开始抓取</button>
</div>
<div id="productInfo">
</div>
</body>
</html>
