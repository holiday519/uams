<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>viewCluster</title>
</head>
<body>
	<div ng-controller="viewClusterUKey.ctrl">
		<div id="wz" class="wz">
			<span class="wz_1">UKey管理</span>>>Cluster UKey
		</div>
		<div class="names_info"></div>
		<opv-button-group opvModel="clusterModel"></opv-button-group>
		<angrid options="angridOptions"></angrid>
	</div>
</body>
</html>