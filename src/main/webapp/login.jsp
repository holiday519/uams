<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="loginApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="keywords" content="汉柏用户认证管理系统">
	<title>汉柏用户认证管理系统</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/main.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/lib/angular.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/login.js"></script>
	<script type="text/javascript">
		var basePath = "<%=request.getContextPath() %>";
	</script>
</head>
<body class="login_bg" ng-controller="loginCtrl">
	<div class="login_box">
		<form class="login_form">
			<ul>
				<li class="type_text">
					<div class="login_alert" ng-click="usrAlert.isShow=false" ng-show="usrAlert.isShow">{{usrAlert.errorMsg}}</div>
					<label>用户名<span>|</span></label>
					<input type="text" ng-model="username" ng-keydown="enter($event)" ng-trim="true" />
				</li>
				<li class="type_text">
					<div class="login_alert" ng-click="pwdAlert.isShow=false" ng-show="pwdAlert.isShow">{{pwdAlert.errorMsg}}</div>
					<label>密码<span>|</span></label>
					<input type="password" ng-model="password" ng-keydown="enter($event)" />
				</li>
				<li class="submit"><a href="javascript:void(0)" ng-click="login()">登 录</a></li>
			</ul>
		</form>
	</div>
</body>
</html>