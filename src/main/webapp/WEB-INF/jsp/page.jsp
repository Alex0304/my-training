<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>商品信息</title>
</head>
<%
  // 获取请求的上下文
  String context = request.getContextPath();
%>
<script type="text/javascript">
  // 当前第几页数据
  var currentPage = ${result.currentPage};

  // 总页数
  var totalPage = ${result.totalPage};

  function submitForm(actionUrl){
    var formElement = document.getElementById("productForm");
    formElement.action = actionUrl;
    formElement.submit();
  }

  // 第一页
  function firstPage(){
    if(currentPage == 1){
      alert("已经是第一页数据");
      return false;
    }else{
      submitForm("<%=context %>/product/page?page=1");
      return true;
    }
  }

  // 下一页
  function nextPage(){
    if(currentPage == totalPage){
      alert("已经是最后一页数据");
      return false;
    }else{
      submitForm("<%=context %>/product/page?page=" + (currentPage+1));
      return true;
    }
  }

  // 上一页
  function previousPage(){
    if(currentPage == 1){
      alert("已经是第一页数据");
      return false;
    }else{
      submitForm("<%=context %>/product/page?page=" + (currentPage-1));
      return true;
    }
  }

  // 尾页
  function lastPage(){
    if(currentPage == totalPage){
      alert("已经是最后一页数据");
      return false;
    }else{
      submitForm("<%=context %>/product/page?page=${result.totalPage}");
      return true;
    }
  }
  <%--function initPage(){--%>
  <%--  var genderRequest = "${gender}" ;--%>
  <%--  var genderVal = 0;--%>
  <%--  var genderElement = document.getElementById("gender");--%>
  <%--  if(genderRequest != ""){--%>
  <%--    genderVal = parseInt(genderRequest);--%>
  <%--  }--%>

  <%--  var options = genderElement.options;--%>
  <%--  var i = 0;--%>
  <%--  for(i = 0; i < options.length; i++){--%>
  <%--    if(options[i].value == genderVal){--%>
  <%--      options[i].selected=true;--%>
  <%--      break;--%>
  <%--    }--%>
  <%--  }--%>

  <%--}--%>
</script>
<body >
<div style="margin-left: 100px; margin-top: 100px;">
  <div>
    <font color="red">${errorMsg }</font>
  </div>

<%--  <div>--%>
<%--    <form action="<%=context %>/product/page"   id="productForm"  method="post">--%>
<%--      商品名称--%>
<%--      <input type="text" name="productName" id="pro_name" style="width:120px" value="${productName }">--%>
<%--      --%>

<%--      <input type="submit" value="查询">--%>
<%--    </form>--%>
<%--  </div>--%>
  <br>
  商品信息列表：<br>
  <br>
  <div>
    <a href="<%=request.getContextPath()+"/product/add"%>">新增商品</a>
  </div>
  <!-- 后台返回结果为空 -->
  <c:if test="${fn:length(result.dataList) eq 0 }">
    <span>查询的结果不存在</span>
  </c:if>

  <!-- 后台返回结果不为空 -->
  <c:if test="${fn:length(result.dataList) gt 0 }">
    <table border="1px" cellspacing="0px"
           style="border-collapse: collapse">
      <thead>
      <tr height="30">
        <th width="130">商品名称</th>
        <th width="130">商品描述</th>
        <th width="130">商品价格</th>
        <th width="130">操作</th>
      </tr>
      </thead>
      <c:forEach items="${result.dataList }" var="product">
        <tr>
          <td><c:out value="${product.name }"></c:out></td>
          <td><c:out value="${product.desc }"></c:out></td>
          <td><c:out value="${product.price }"></c:out></td>
          <td><a href="<%=request.getContextPath()+"/product/update?"%>id=${product.id}">修改</a>
            <a href="<%=request.getContextPath()+"/product/delById?"%>id=${product.id}">删除</a>
          </td>
          <td></td>
        </tr>
      </c:forEach>
    </table>
    <br> 共${result.totalRecord }条记录共${result.totalPage }页  当前第${result.currentPage }页
    <a href="#" onclick="firstPage();">首页</a>
    <a href="#" onclick="nextPage();">下一页</a>
    <a href="#" onclick="previousPage();">上一页</a>
    <a href="#" onblur="lastPage();">尾页</a>
  </c:if>
</div>
</body>
</html>
