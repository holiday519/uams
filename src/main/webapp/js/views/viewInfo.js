basicApp.controller('viewInfo.ctrl', function($scope, httpService, anpopupService, validateService) {
	anpopupService.setScope($scope);
	$scope.information = {};
	var refreshInfo = function() {
		httpService({
			data : {
		        method : 'POST',
		        url : basePath + "/listUsers.do",
		        data : {user : {userId : loginUserId}, page : {}}
		    },
		    success : function(data) {
				$scope.information = {
					userid : data.users[0].userId,
					username : data.users[0].userName,
					telephone : data.users[0].telephone,
					email : data.users[0].email,
					role : data.users[0].userTypeCode == 1 ? "超级管理员" : "普通用户",
					note : data.users[0].note
				};
		    }
		});
	};
	refreshInfo();
	var editInfo = function() {
		$scope.editInfo = {
			userid : $scope.information.userid,
			username : $scope.information.username,
			telephone : $scope.information.telephone,
			email : $scope.information.email,
			note : $scope.information.note
		};
		anpopupService.openDialog({
			title : "编辑用户",
			width : '500px',
			height : '450px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">' 
			+					'<li>' 
			+						'<span class="wenzi">用户名:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="editInfo.username" name="username" class="input" anvalidate="required,formatname,maxlength[16],minlength[6]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">手机号:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="editInfo.telephone" name="telephone" class="input" anvalidate="required,telephone,maxlength[20],minlength[7]"  />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">邮箱:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="email" ng-model="editInfo.email" name="email" class="input" anvalidate="required,email,maxlength[16],minlength[1]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">备注:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="editInfo.note" name="note" anvalidate="maxlength[64]" />'
			+						'</span>'
			+					'</li>'
			+				'</ul>'
			+			'</form>',
			buttons : [{
				label : "取消",
				clickFunc : function() {
					anpopupService.closeDialog();
				}
			},{
				label : "完成",
				clickFunc : function() {
					if(validateService.validateForm()) {
						httpService({
							data : {
						        method : 'POST',
						        url : basePath + "/updateUser.do",
						        data : {
						        	userId : $scope.editInfo.userid,
					        		userName : $scope.editInfo.username, 
				        			telephone : $scope.editInfo.telephone,
				        			email : $scope.editInfo.email,
				        			userTypeCode : (loginUserId == 1 ? 1 : 2),
				        			note : $scope.editInfo.note
				        		}
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
						    	if(data.errorCode == 0) {
						    		anpopupService.openAlert("notice", "添加编辑成功！");
						    		refreshInfo();
						    	}else if(lang["error.user."+data.errorCode]) {
						    		anpopupService.openAlert('warning', lang["error.user."+data.errorCode]);
						    	}else {
						    		anpopupService.openAlert('warning', lang["error."+data.errorCode]);
						    	}
						    }
						});
					}
				}
			}]
		});
	};
	var updateMyPwd = function() {
		$scope.updateMyPwd = {
			userid : $scope.information.userid,
		};
		anpopupService.openDialog({
			title : "修改密码",
			width : '500px',
			height : '250px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">' 
			+					'<li>' 
			+						'<span class="wenzi">新密码:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="password" ng-model="updateMyPwd.password" name="password" class="input" anvalidate="required,password,maxlength[16],minlength[6]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">确认密码:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="password" ng-model="updateMyPwd.confirmpwd" name="confirmpwd" class="input" anvalidate="required,equals[password]" />'
			+						'</span>'
			+					'</li>'
			+				'</ul>'
			+			'</form>',
			buttons : [{
				label : "取消",
				clickFunc : function() {
					anpopupService.closeDialog();
				}
			},{
				label : "完成",
				clickFunc : function() {
					if(validateService.validateForm()) {
						httpService({
							data : {
						        method : 'POST',
						        url : basePath + "/modifyPassword.do",
						        data : {
						        	userId : $scope.updateMyPwd.userid,
					        		password : $scope.updateMyPwd.password
				        		}
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
						    	if(data.errorCode == 0) {
						    		anpopupService.openAlert("notice", "密码修改成功！");
						    	}else if(lang["error.user."+data.errorCode]) {
						    		anpopupService.openAlert('warning', lang["error.user."+data.errorCode]);
						    	}else {
						    		anpopupService.openAlert('warning', lang["error."+data.errorCode]);
						    	}
						    }
						});
					}
				}
			}]
		});
	};
	//数据模型
	$scope.infoModel = {
		//按钮组
		buttonGroup : [{'id' : 'edit', 'cls' : 'edit', 'val' : '编辑', 'click' : editInfo},
		               {'id' : 'updatepwd', 'cls' : 'update_passwd', 'val' : '修改密码', 'click' : updateMyPwd}]
	};
});