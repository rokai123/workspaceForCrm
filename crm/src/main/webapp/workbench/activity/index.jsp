<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){



		$("#addBtn").click(function(){
			// $("#createActivityModal").modal("show");
			//点击添加按钮后为日期框添加插件
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			$.ajax({
				url :"workbench/activity/getUserList.do",
				type : "get",
				dataType : "json",
				success : function(data){
					let html = "<option></option>";
					//遍历出每一个n，就是每一个user对象
					$.each(data,function(index,user){
						html += "<option value='"+user.id+"'>"+user.name+"</option>";

					})

					$("#create-owner").html(html);

					//设置当前登录的用户为默认的创建市场活动所有者
					$("#create-owner").val("${user.id}");

					//所有者下拉框处理完毕后，展现模态窗口
					//所有者選択プルダウン処理完了後、モーダルウィンドウを表示する
					$("#createActivityModal").modal("show");
				}
			})
		})
		
		//为保存按钮绑定事件，执行添加操作
		$("#saveBtn").click(function(){

			$.ajax({
				url :"workbench/activity/save.do",
				type : "post",
				dataType : "json",
				data : {

					"owner" : $.trim($("#create-owner").val()),
					"name" : $.trim($("#create-name").val()),
					"startDate" : $.trim($("#create-startDate").val()),
					"endDate" : $.trim($("#create-endDate").val()),
					"cost" : $.trim($("#create-cost").val()),
					"description" : $.trim($("#create-description").val())


				},
				success : function(data){

					if (data.success){
						//添加成功后
						//刷新市场活动信息列表（局部刷新）

						//清空添加模态窗口中的数据
						//jquery对象转换为dom对象JQuery对象[下标]
						// $("#activityAddForm")[0].reset();
						//关闭添加状态的模态窗口
						$("#createActivityModal").modal("hide");

					}else{
						alert("新規操作中にエラーが発生しました")
					}

				}


			})

		})

		//页面加载完毕后触发一个方法
		//默认展开列表的第一页，每页展现两条记录
		pageList(1,2);

		//为查询按钮绑定事件。触发pageList方法
		$("#searchBtn").click(function(){
		//点击查询按钮的时候，我们应该将搜索框中的信息保存起来，保存到隐藏域中
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));


			pageList(1,2);
		})

		//为全选的复选框绑定事件。触发全选操作
		$("#qx").click(function(){
			$("input[name=xz]").prop('checked',this.checked);
		})

		//以下这种做法是不行的
		/*$("input[name=xz]").click(function () {
			alert(123);
		})*/
		//因为动态生成的元素，是不能够以普通绑定事件的形式来进行操作的
		/*
			动态生成的元素，我们要以on方法的形式来触发事件
			语法：
				$(需要绑定元素的有效的外层元素).on(绑定事件的方式,需要绑定的元素的jquery对象,回调函数)
		 */
		$("#activityBody").on('click',$("input[name=xz]"),function(){
			$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
		})

		//点击删除按钮，执行市场活动删除操作
		$("#deleteBtn").click(function(){
			//找到所有被选中的复选框的jquery对象
			let $xz = $("input[name=xz]:checked");

			if($xz.length==0){
				alert("削除したい項目を選択してください")

			//选了的情况下有可能是一条，也有可能是多条
			}else {

				if (confirm("選択中のデータを削除してもよろしいですか？")){

					//url:workbench/activity/delete.do?id=xxx&id=xxx&id=xxx
					//拼接参数
					let param = "";
					for(let i=0;i<$xz.length;i++){

						param +="id="+$($xz[i]).val();

						//如果不是最后一个元素，需要在后面追加&符
						if (i<$xz.length-1){
							param += "&";
						}

					}

					$.ajax({
						url :"workbench/activity/delete.do",
						type : "post",
						dataType : "json",
						data : param,
						success : function(data){
							/*
                            data
                                {"success":true/false}
                             */
							if(data.success){
								//删除成功后调用分页方法刷新模块,回到第一页，每页展现2条信息
								pageList(1,2);
							}else{
								alert("删除失败")
							}
						}
					})
				}




			}
		})


	});



	/*
	* pageNo 页码
	* pageSize 每页展现的记录数
	* 我们在哪些情况下需要刷新一下市场活动列表？调用pageList方法
	* （1）点击左侧菜单的“市场活动”超链接
	* （2）添加，修改，删除后
	* （3）点击查询按钮时
	* （4）点击分页组件的时候
	* */
	function pageList(pageNo,pageSize){

		//将全选的复选框的对钩干掉
		$("#qx").prop("checked",false);

		//查询前，将隐藏域中保存的信息取出来，重新赋予到搜索框中
		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));

		$.ajax({
			url:"workbench/activity/pageList.do",
			type : "get",
			data: {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $.trim($("#search-name").val()),
				"owner" : $.trim($("#search-owner").val()),
				"startDate" : $.trim($("#search-startDate").val()),
				"endDate" : $.trim($("#search-endDate").val())
			},
			dataType : "json",
			success : function(data){
				let html = "";

				$.each(data.dataList,function(i,n) {
					html +=' <tr className="active">';
					html +='  	<td><input type="checkbox" value="'+n.id+'" name="xz"/></td>';
					html +='  	<td><a style="text-decoration: none; cursor: pointer;" onClick="window.location.href=\'workbench/activity/detail.jsp\';">'+n.name+'</a></td>';
					html +='  	<td>'+n.owner+'</td>';
					html +='  	<td>'+n.startDate+'</td>';
					html +='  	<td>'+n.endDate+'</td>';
					html +='  </tr>';
				})

				$("#activityBody").html(html);

				//计算总页数
				let totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

				//数据处理完毕后，结合分页插件，对前端展现分页信息
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});


			}

		})
	}



</script>
</head>
<body>

	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">マーケティングキャンペーン作成</h4>
				</div>
				<div class="modal-body">
				
					<form id="activityAddForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
<%--								  <option>zhangsan</option>--%>
<%--								  <option>lisi</option>--%>
<%--								  <option>wangwu</option>--%>
								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">開始日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" onkeydown="return false;">
<%--								HTML5提供了专门的日期和时间输入类型，可以强制用户使用浏览器内置的日期/时间选择器，而不是手动输入
                                <input type="data" class="form-control" id="create-startTime">--%>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">終了日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" onkeydown="return false;">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
<%--					data-dismiss="modal"表示关闭模态窗口--%>
					<button type="button" class="btn btn-default" data-dismiss="modal" style="color: #ac2925">閉じる</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>マーケティング活動一覧</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input id="search-name" class="form-control" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input id="search-owner" class="form-control" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input id="search-startDate" class="form-control time" type="text"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input id="search-endDate" class="form-control time" type="text" id="endTime">
				    </div>
				  </div>
				  
<%--				  <button type="submit" class="btn btn-default">查询</button>--%>
				  <button id="searchBtn" type="button" class="btn btn-default">查询</button>


				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
					<!--
						data-toggle="modal":表示触发该按钮，将要打开一个模态窗口
						data-target="#createActivityModal"：表示要打开哪个模态窗口，通过#id的形式找到该窗口

						现在是以属性和属性值的方式写在了button元素中，这样做的问题是没有办法对按钮的功能进行扩充。
					-->
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 新規作成</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editActivityModal"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span>削除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
<%--						<tr class="active">--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--							<td>2020-10-10</td>--%>
<%--							<td>2020-10-20</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>2020-10-10</td>--%>
<%--                            <td>2020-10-20</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>