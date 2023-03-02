<%--
  Created by IntelliJ IDEA.
  User: DXM-0189
  Date: 2023/2/21
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
    <title>Title</title>
</head>
<%
    // 获取请求的上下文
    String context = request.getContextPath();
%>
<script type="application/javascript">
    $(function () {

        //发送表单ajax请求
        $("#submitId").click(
            function () {
                alert(1);
                $.ajax({
                    url: "<%=context %>/xss/toAddJson",
                    type: "POST",
                    data: JSON.stringify($("#formId").serializeObject()),
                    contentType: "application/json",  //缺失会出现URL编码，无法转成json对象
                    success: function () {
                        alert("成功");
                    }
                });
            });
    });

  /**
   * 自动将form表单封装成json对象
   */
  $.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
      if (o[this.name]) {
        if (!o[this.name].push) {
          o[this.name] = [ o[this.name] ];
        }
        o[this.name].push(this.value || '');
      } else {
        o[this.name] = this.value || '';
      }
    });
    return o;
  };
</script>
<body>
<h1>新增页面</h1>
<form id="formId">
  商品描述:<input type="text" name="desc"><br>
    <input id="submitId" class="ui-button" type="submit" value="保存"/>
</form>
</body>
</html>
