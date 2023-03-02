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
<h1>查询用户分库分表规则</h1>
<form action="<%=request.getContextPath()+"/sharding/queryRuleByUserId"%>" method="get">
  用户id:<input type="text" name="userId"><br>
  <input type="submit" value="查询">
</form>

</body>
</html>
