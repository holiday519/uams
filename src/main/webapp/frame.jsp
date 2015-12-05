<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="basicApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/layout-default-latest.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/main.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/anmask.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/lib/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/lib/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/lib/angular.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/lib/angular-route.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/lib/angular-file-upload-shim.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/lib/angular-file-upload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/angrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/anmask.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/anvalidate-conf.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/anvalidate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/anpopup.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/anbutton.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/app.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/error.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common/menudata.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/frame.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/viewUser.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/viewProduct.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/viewLicense.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/viewInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/viewClusterUKey.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/views/viewVDIUKey.js"></script>
<script type="text/javascript">
var basePath = "<%=(String)request.getContextPath() %>";
var loginUserId = "<%=(String)session.getAttribute("loginUserId") %>";
</script>
<title>frame</title>
</head>
<body ng-controller="frame.ctrl">
	<div id="header" class="outer-north">
	    <div class="logo"><img src="<%=request.getContextPath() %>/images/top_logo.png"></div>
	    <div class="right">
	        <div class="exit"><a href="javascript:void(0)" ng-click="logout()">退出</a></div>
	        <div class="information_top">欢迎：<font>{{loginUser.userName}}</font>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;v0.1.1001</div>
	    </div>
	</div>
	<div class="outer-center">
	    <div id="sidebar" class="inner-west">
	        <div id="menu" frame.menu></div>
	    </div>
	   	<div id="content" class="inner-center" >
	    	<div ng-view></div>
	    </div>
	</div>
</body>
</body>
</html>