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
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script>
	$(function() {

		if(window.top!=window){
			window.top.location=window.location;
		}

		//ページ読み込み完了後、テキストボックスの内容をクリアする
		$("#loginAct").val("");
		//ページ読み込み完了後、テキストボックスに自動フォーカスを設定する
		$("#loginAct").focus();

		//ログインボタンにクリックイベントをバインドして、ログイン処理を実行する
		$("#submitBtn").click(function() {
			Login();
		})

		//現在のウィンドウにキーボードイベントをバインドする
		$(window).keydown(function(event){
			//エンターキー =13
			if(event.keyCode==13){
				Login();
			}
		})

	})
	//普通的自定义的function方法，一定要写在$(function(){})的外面
	//カスタム関数は基本的に $(function(){}) の外側で定義し、内部で呼び出すのがベストプラクティスです
	function Login(){
		//验证账号密码不能为空
		//アカウントとパスワードが空でないことを検証する
		//取得账号密码
		//アカウントとパスワードを取得する
		let loginAct = $.trim($("#loginAct").val());
		let loginPwd = $.trim($("#loginPwd").val());
		if(loginAct == ''|| loginPwd == ''){
			$("#msg").html("アカウントとパスワードは必須入力です")
			//如果账号密码为空，则需要及时强制终止该方法。
			//アカウントまたはパスワードが空の場合、直ちに当該メソッドの処理を強制終了する必要があります。
			return false;
		}

		//去后台验证登录相关操作
		//バックエンドでのログイン認証処理
		$.ajax({
			url:"settings/user/login.do",
			data:{
				"loginAct":loginAct,
				"loginPwd":loginPwd
			},
			type:"post",
			dataType:'json',
			success:function(data){
				//如果登录成功
				//ログイン成功時の処理
				if (data.success){
					//跳转到工作台的初始页
					//ワークベンチ（作業台）の初期ページにリダイレクトする
					window.location.href="workbench/index.jsp";

				//如果登录失败
				//ログイン失敗時の処理
				}else {
					$("#msg").html(data.msg);
				}
			}
		})

	}

</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2025&nbsp;盧凱</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>ログイン</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" type="text" placeholder="ユーザー名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" type="password" placeholder="パスワード">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">

							<span id="msg" style="color: red"></span>
						
					</div>
					<button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">ログイン</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>