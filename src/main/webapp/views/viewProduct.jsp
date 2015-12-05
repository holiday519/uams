<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>viewProduct</title>
</head>
<body>
	<div ng-controller="viewProduct.ctrl">
		<div id="wz" class="wz">
			<span class="wz_1">产品管理</span>>>版本管理
		</div>
		<div class="names_info"></div>
        <opv-button-group opvmodel="productModel"></opv-button-group>
		<angrid options="angridOptions"></angrid>
	</div>
</body>
</html>