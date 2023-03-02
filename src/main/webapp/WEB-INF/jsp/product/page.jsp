<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
  <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
  <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <title>商品信息</title>
</head>
<%
  // 获取请求的上下文
  String context = request.getContextPath();
%>
<script type="text/javascript">

  // 当前第几页数据
  var currentPage = ${result.number}+1;

  // 总页数
  var totalPage = ${result.totalPages};


  // 用户id
  var userId = ${requestScope.userId};

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
      submitForm("<%=context %>/product/queryByPage?page=1");
      return true;
    }
  }

  // 下一页
  function nextPage(){
    if(currentPage == totalPage){
      alert("已经是最后一页数据");
      return false;
    }else{
      submitForm("<%=context %>/product/queryByPage?page="+(currentPage+1));
      return true;
    }
  }

  // 上一页
  function previousPage(){
    if(currentPage == 1){
      alert("已经是第一页数据");
      return false;
    }else{
      submitForm("<%=context %>/product/queryByPage?page="+(currentPage-1));
      return true;
    }
  }

  // 尾页
  function lastPage(){
    if(currentPage == totalPage){
      alert("已经是最后一页数据");
      return false;
    }else{
      submitForm("<%=context %>/product/queryByPage?page=${result.totalPages}");
      return true;
    }
  }

  function delProduct(id,userId){
    var productIdForm={
      "userId": userId,
      "id": id
    }
    $.ajax({
      url: "<%=context %>/product/deleteById",
      type: "POST",
      data: JSON.stringify(productIdForm),
      contentType: "application/json",  //缺失会出现URL编码，无法转成json对象
      success: function () {
        //刷新列表
        submitForm("<%=context %>/product/queryByPage?page=1");
      }
    });
  }

  function queryUserRule(){
    var userId = $("#userId").val();
    if(userId==null){
      alert("userId 不能为空")
      return false;
    }
    $.ajax({
      url: "<%=context %>/sharding/queryRuleByUserId?userId="+userId,
      type: "get",
      success: function (data) {
        alert(data)
      }
    });
  }

  function collectShipInfo(){
    $.ajax({
      url: "<%=context %>/shipProduct",
      type: "get",
      success: function () {
        alert("抓取成功")
      }
    });
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
  function addProduct(){
    $('#addProductModal').modal()
  }
  
  function toAddProduct(){
    var formElement = document.getElementById("saveForm");
    formElement.submit();
  }


  function updateProduct(val) {
    var value = $(val).parent().parent().find("td");
    var id = value.eq(0).text();
    var name = value.eq(1).text();
    var desc = value.eq(2).text();
    var price = value.eq(3).text();
    var userId = value.eq(4).text();
    $('#updateId').val(id);
    $('#updateUserId').val(userId);
    $('#updateName').val(name);
    $('#updateDesc').val(desc);
    $('#updatePrice').val(price);
    $('#updateProductModal').modal()
  }
  function toUpdateProduct(){
    var formElement = document.getElementById("updateForm");
    formElement.submit();
  }
</script>
<body >
<div style="margin-left: 100px; margin-top: 100px;">
  <div>
    <font color="red">${errorMsg }</font>
  </div>

  <div>
    <form action="<%=context %>/product/queryByPage"   id="productForm"  method="post">
      用户id
      <input type="text" id="userId" name="userId" id="userId" style="width:120px" value="${userId}">
      <input type="submit" value="查询">
    </form>
  </div>
  <br>
  商品信息列表：<br>
  <br>
  <div>
    <a  href="javascript:void(0);"  onclick="addProduct()">新增商品</a>
<%--    <a href="<%=request.getContextPath()+"/product/add"%>" margin-left="200px">新增商品</a>--%>
    <a href="javascript:void(0);"  onclick="queryUserRule()">查询用户分库分表规则</a>
    <a href="javascript:void(0);"  onclick="collectShipInfo()">抓取保存物流商信息</a>
    <a href="<%=request.getContextPath()+"/product/collect"%>">采集商品</a>
    <a href="<%=request.getContextPath()+"/product/process"%>">批量任务</a>

  </div>
  <!-- 后台返回结果为空 -->
  <c:if test="${fn:length(result.content) eq 0 }">
    <span>查询的结果不存在</span>
  </c:if>

  <!-- 后台返回结果不为空 -->
  <c:if test="${fn:length(result.content) gt 0 }">
    <table border="1px" cellspacing="0px"
           style="border-collapse: collapse">
      <thead>
      <tr height="30">
        <th width="130">商品id</th>
        <th width="130">商品名称</th>
        <th width="130">商品描述</th>
        <th width="130">商品价格</th>
        <th width="130">用户id</th>
        <th width="130">操作</th>
      </tr>
      </thead>
      <c:forEach items="${result.content }" var="product">
        <tr>
          <td><c:out value="${product.id }"></c:out></td>
          <td><c:out value="${product.name }"></c:out></td>
          <td><c:out value="${product.desc }"></c:out></td>
          <td><c:out value="${product.price }"></c:out></td>
          <td><c:out value="${product.userId }"></c:out></td>
          <td>
<%--            <a href="<%=request.getContextPath()+"/product/update?"%>id=${product.id}&userId=${product.userId}">修改</a>--%>
<%--            <a href="<%=request.getContextPath()+"/product/deleteById?"%>id=${product.id}" onclick="unbindPhone('${id }','${product.userId }')">删除</a>--%>
          <a href="javascript:void(0);"  onclick="updateProduct(this)">修改</a>
          <a href="javascript:void(0);"  onclick="delProduct('${product.id }','${product.userId }')">删除</a>

          </td>
          <td></td>
        </tr>
      </c:forEach>
    </table>
    <br> 共${result.totalElements }条记录共${result.totalPages }页  当前第${result.number+1 }页
    <a href="#" onclick="firstPage();">首页</a>
    <a href="#" onclick="nextPage();">下一页</a>
    <a href="#" onclick="previousPage();">上一页</a>
    <a href="#" onclick="lastPage();">尾页</a>
  </c:if>
</div>
<div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">新增商品</h4>
      </div>
<%--      <div class="modal-body">在这里添加一些文本</div>--%>
      <div>
        <form id="saveForm" action="<%=request.getContextPath()+"/product/toAdd"%>" method="post">
          用户id:<input type="number" name="userId"><br>
          商品名称:<input type="text" name="name"  ><br>
          商品描述:<input type="text" name="desc"  ><br>
          商品价格:<input type="text" name="price"><br>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="toAddProduct()">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->
</div>

<div class="modal fade" id="updateProductModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="updateProductModalLabel">修改商品</h4>
      </div>
      <%--      <div class="modal-body">在这里添加一些文本</div>--%>
      <div>
        <form id="updateForm" action="<%=request.getContextPath()+"/product/toUpdate"%>" method="post">
          <input type="number" name="userId" hidden="true" id="updateUserId">
          <input type="number" name="id" hidden="true" id="updateId">
          商品名称:<input type="text" name="name" id="updateName" ><br>
          商品描述:<input type="text" name="desc" id="updateDesc" ><br>
          商品价格:<input type="text" name="price" id="updatePrice"><br>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="toUpdateProduct()">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->
</div>


</body>
</html>
