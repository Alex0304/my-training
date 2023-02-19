<%@ page import="com.ch.train.entity.Product" %><%--
  Created by IntelliJ IDEA.
  User: chenhuan
  Date: 2023/2/19
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>编辑页面</h1>
<form action="<%=request.getContextPath()+"/product/toAdd"%>" method="post">
    <input type="number" hidden="true" name="id" value="${product.id}">
    商品名称:<input type="text" name="name" value="${product.name}" ><br>
    商品描述:<input type="text" name="desc" value="${product.desc}" ><br>
    商品价格:<input type="number" name="price" value="${product.price}"><br>
    <input type="submit" value="保存">
</form>
</body>
</html>
