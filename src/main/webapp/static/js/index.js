// 重新加载pageList页面
var reloadPage = function(){
	var pageNo = $('#pageNo').val();
	var pageSize = $('#pageSize').val();
	$.ajax({
		type: "POST",
		url: "/showData.htm",
		data: {
			type: "pageList",
			pageNo: pageNo,
			pageSize: pageSize
		},
		dataType: "html",
		success: function (data) {
			$('#pageList').html(data);
		}
	});
};

var initPage = function() {
	var totalSize = $('#totalSize').val();
	if (parseInt(totalSize) > 0) {
		options.totalPages = $('#totalPage').val();
		options.currentPage = $('#pageNo').val();
		options.numberOfPages = $('#pageSize').val();
		options.numberOfPagesArr = [2,3];
		options.goOffPage = true;
		options.totalSize = totalSize;
		options.onPageClicked = function (event, originalEvent, type, page) {
			$.ajax({
				type: "GET",
				url: "/showData.htm",
				data: {
					type: "pageList",
					pageNo: page,
					pageSize: $('#pageSize').val()
				},
				dataType: "html",
				success: function (data) {
					$('#pageList').html(data);
				}
			});
		};
		options.onPageSelected = function (event) {
			var pageSelect = $(event.target);
			$.ajax({
				type: "GET",
				url: "/showData.htm",
				data: {
					type: "pageList",
					pageNo: 1,
					pageSize: pageSelect.val()
				},
				dataType: "html",
				success: function (data) {
					$('#pageList').html(data);
				}
			});
		};
		options.onPageJump = function (event) {
			$.ajax({
				type: "GET",
				url: "/showData.htm",
				data: {
					type: "pageList",
					pageNo: $(event.target).prevAll("input[zdyPageInput='zdy']").val(),
					pageSize: $("select[name='pageselct']").val()
				},
				dataType: "html",
				success: function (data) {
					$('#pageList').html(data);
				}
			});
		};

		$('#downPage').bootstrapPaginator(options);
	} else {
		$('#downPage').empty();
	}
};

/** 弹框 **/
var showEditDialog = function(single,id, code){
	if(single === 'single'){
        if(id > 0){
            // 修改
            $("#title").text("修改");
            $("#code").val(code);
            $("#id").val(id);
            $("#editBtn").text("修改").attr("onclick", "update();");
        } else {
            // 新增
            $("#title").text("新增");
            $("#code").val("");
            $("#id").val("");
            $("#editBtn").text("新增").attr("onclick", "add();");
        }
	} else {
        $("#title").text("批量修改");
        $("#code").val("");
        $("#id").val("");
        $("#editBtn").text("修改").attr("onclick", "batchUpdate();");
	}

        $("#editDialog").modal("show");
    };

/** 批量弹框 **/

/** 新增 **/
var add = function(){
	var url = '/add.json';
	var code = $("#code").val().trim();
	if(code == '' || code == null){
		alert("请输入code");
		return;
	}
	$.ajax({
		type: "POST",
		url: url,
		data: {
			code : code
		},
		dataType: "json",
		success: function (data) {
			if(data.code == 0){
				$('#editDialog').modal('hide');
				reloadPage();
			} else {
				alert(data.msg);
			}
		},
		error: function () {
			$('#loading').modal('hide');
		}
	});
};

/** 修改 **/
var update = function(){
	var url = '/update.json';
	var code = $("#code").val().trim();
	var id = $("#id").val();
	if(code == '' || code == null){
		alert("请输入code");
		return;
	}
	$.ajax({
		type: "POST",
		url: url,
		data: {
			id : id,
			code : code
		},
		dataType: "json",
		success: function (data) {
			if(data.code == 0){
				$('#editDialog').modal('hide');
				reloadPage();
			} else {
				alert(data.msg);
			}
		},
		error: function () {
			$('#loading').modal('hide');
		}
	});
};

/** 批量修改 **/
var batchUpdate = function(){
    var url = '/batchUpdate.json';
    var code = $("#code").val().trim();
    if(code == '' || code == null){
        alert("请输入code");
        return;
    }
    var ids = [];
	var $ids = $("#tab").find('input[name=listOn]');
	$.each($ids,function (i,id) {
		ids.push($(id).val());
    });

    if(ids.length === 0){
        alert("没有修改的数据");
        return;
    }
    var $modal = $("#Progress");
    $.ajax({
        type: "POST",
        url: url,
        data: {
            ids : ids.join(","),
            co : code
        },
        dataType: "json",
        success: function (data) {
            if(data.code == 0){
                $('#editDialog').modal('hide');
                $modal.find("#progressBar").addClass("active").css("width", "0");
                $modal.find('#finishNum').text('0');
                $modal.find('#totalNum').text(data.totalNum);
                $modal.find('#successNum').text('0');
                $modal.find('#failNum').text('').addClass("hide");
                $modal.find('#errMsg').addClass("hide");
                $modal.modal('show');

                checkProcessStatus(data.uuid, function(ret) {
                    if (ret.code != -1) {
                        $modal.find('#successNum').text(ret.successNum);
                        if (ret.failNum) {
                            $modal.find('#failNum').text(ret.failNum).removeClass('hide');
                        }
                        $modal.find('#finishNum').text(ret.successNum + ret.failedNum);

                        var num = 60 + (ret.successNum + ret.failedNum) / ret.totalNum * 40;
                        if (ret.code == 0) {
                            $modal.find("#progressBar").css("width", num + "%");
                        } else {
                            $modal.find("#processText").html("修改完成");
                            $modal.find("#progressBar").css("width", "100%").removeClass("active");
                            $modal.find('.modal-footer button').html('close');
                            if (ret.msg) {
                                $modal.find("#errMsg").removeClass("hide").html(ret.msg);
                            }
                        }
                    } else {
                        $modal.find("#errMsg").removeClass("hide").html(ret.msg);
                    }
                });

                reloadPage();
            } else {
                alert(data.msg);
            }
        },
        error: function () {
            $('#loading').modal('hide');
        }
    });
};

/**
 * Front progress bar
 */
var checkProcessStatus = function () {
    var interval;
    var uuid;
    return function (uuid, callback) {
        if (!interval)
            interval = setInterval(go, 2000);

        this.callback = callback;
        this.uuid = uuid;
    };
    function go() {
        $.ajax({
            type: "POST",
            url: "/checkSyncProcess.json",
            data: {"uuid": this.uuid},
            dataType: "json",
            success: function (data) {
                var pm = data.processMsg;

                if (pm == undefined || pm.code != 0) {
                    clearTimeout(interval);
                    interval = "";
                }
                callback(pm);
            }
        });
    }
}();

/** 删除 **/
var del = function(id){
	if(confirm("是否要删除？")){
		var url = '/del.json';
		$.ajax({
			type: "POST",
			url: url,
			data: {
				id : id
			},
			dataType: "json",
			success: function (data) {
				if(data.code == 0){
					reloadPage();
				} else {
					alert(data.msg);
				}
			},
			error: function () {
				$('#loading').modal('hide');
			}
		});
	}
};

var searchByCode = function () {
    var code = $("#searchValue").val();
	var pageSize = $('#pageSize').val();
	$.ajax({
        type: "GET",
        url: "/search.htm",
        data: {
            pageNo: 1,
            pageSize: pageSize,
			code:code
        },
        dataType: "html",
        success: function (data) {
            $('#pageList').html(data);
        }
    });
};