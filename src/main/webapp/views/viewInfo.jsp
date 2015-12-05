<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>viewInfo</title>
</head>
<body>
	<div ng-controller="viewInfo.ctrl">
		<div id="wz" class="wz">
			<span class="wz_1">系统管理</span>>>认证管理
		</div>
		<div class="names_info"></div>
		<opv-button-group opvModel="infoModel"></opv-button-group>
		<div class="info">
			<div class="information">个人信息</div>
			<div class="basicInfo_content">
				<table>
				    <tbody>
					    <tr>
							<td class="word_layer">用户名：</td><td id="detail_username" class="detailValue_layer">{{information.username}}</td>
					    </tr>
					    <tr>
							<td class="word_layer">手机号：</td><td id="detail_telephone" class="detailValue_layer">{{information.telephone}}</td>
					    </tr>
					    <tr>
							<td class="word_layer">邮箱：</td><td id="detail_email" class="detailValue_layer">{{information.email}}</td>
					    </tr>
					    <tr>
							<td class="word_layer">角色：</td><td id="detail_role" class="detailValue_layer">{{information.role}}</td>
					    </tr>
					    <tr>
							<td class="word_layer">备注：</td><td id="detail_note" class="detailValue_layer">{{information.note}}</td>
					    </tr>
					</tbody>
				</table>
			</div>
	    </div>
    </div>
</body>
</html>