<%--
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
<%--新增数据以后，我们点击保存后要回到列表页面--%>
<h1>新增页面</h1>
<form action="<%=request.getContextPath()+"/product/toAdd"%>" method="post">
  商品名称:<input type="text" name="name"  ><br>
  商品描述:<input type="text" name="desc"  ><br>
  商品价格:<input type="text" name="price"><br>
  <input type="submit" value="保存">
</form>
</body>
</html>
