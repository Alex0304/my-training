<%--
  Created by IntelliJ IDEA.
  User: DXM-0189
  Date: 2023/2/22
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<%
    // 获取请求的上下文
    String context = request.getContextPath();
%>
<body>
<script>
    function submitTask() {
        var url = $("#fileUrls").val();
        $.ajax({
            type : 'post',
            url : "<%=context %>/task/zip",
            data:"url="+url,
            success : function(taskId) {
                console.log("批量任务id:"+taskId);
                //导入成功
                if (taskId) {
                    //定时任务获取redis导入修改进度
                    var code = 0
                    var timingTask = setInterval(function(){
                        $.ajax({
                            type: 'get',
                            url: "<%=context %>/task/queryProcessById?taskId="+taskId,
                            success: function(result) {
                                code = result.code
                                var successNum = parseInt(result.successNum)
                                var failedNum = parseInt(result.failedNum)
                                var totalNum = parseInt(result.totalNum)
                                if (parseInt(code)==0 || parseInt(code)==1){
                                    var process = parseFloat((failedNum+successNum)/totalNum*100).toFixed(2)
                                    //这里更新进度条的进度和数据
                                    $(".progress-bar").width(process+"%");
                                    $(".progress-bar").text(process+"%");
                                }
                            }
                        });
                        //导入修改完成或异常（停止定时任务）
                        if (parseInt(code) == 1 || parseInt(code) == -1) {
                            alert("任务执行完成")
                            //清除定时执行
                            clearInterval(timingTask);
                            // $.ajax({
                            //     type: 'post',
                            //     url: "/risk/de***ess",
                            //     dataType : 'json',
                            //     success: function(result) {
                            //      //   $("#bulkImportChangesProcessor").hide();
                            //         if (parseInt(progress) == 100) {
                            //             alert("批量导入修改状态成功");
                            //         }
                            //         if (progress == "error") {
                            //             alert("批量导入修改状态失败");
                            //         }
                            //         //获取最新数据
                            //        // window.location.href="/risk/re***ByParam";
                            //     }
                            // });
                        }
                    }, 3000)
                }else {
                    //$("#bulkImportChangesProcessor").hide();
                    alert("处理失败");
                   // window.location.href="/risk/re***ByParam";
                }
            }
        });
    }
</script>
<div>
    文件地址:<textarea rows="6" cols="60" id="fileUrls">
</textarea>
    <button type="button" onclick="submitTask()">提交任务</button>
</div>

<div class="progress progress-striped active">
    <div class="progress-bar progress-bar-success" role="progressbar"
         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
         style="width: 0%;">
        0%
    </div>
</div>
</body>
</html>
