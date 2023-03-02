<%--
  Created by IntelliJ IDEA.
  User: DXM-0189
  Date: 2023/2/21
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css"></script>
</head>
<body>
<div>
    <div >
        <td>商品名称:</td>
        <td style="color: #2b669a">${collectProduct.title}</td>
    </div>
    <div>
        <td>商品描述:</td>
        <td style="color: #2b669a">${collectProduct.desc}</td>
    </div>
    <div>
        <td>商品价格:</td>
        <td style="color: #2b669a">${collectProduct.price}</td>
    </div>
    <div >
        <td>变种信息:</td>
        <td style="color: #2b669a">${collectProduct.variations}</td>
    </div>
</div>
</body>
</html>
