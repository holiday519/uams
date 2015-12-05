basicApp.controller('viewUser.ctrl', function($scope, httpService, angridService, anpopupService, utilService, validateService) {
	anpopupService.setScope($scope);
	function getSelProducts(products) {
		var selProducts = [];
		angular.forEach(products, function(product, idx) {
			if(product.selected) {
				selProducts.push(product);
			}
		});
		return selProducts;
	}
	// 按钮功能
	var addUser = function() {
		// 因为该弹出框是要绑定在body上，所以变量应该绑定在$scope上
		$scope.addUser = {
			usertype : 1 // 默认值
		};
		httpService({
			data : {
		        method : 'POST',
		        url : basePath + "/listProducts.do",
		        data : {product : {}, page : {}}
		    },
		    success : function(data) {
		    	if(data.errorCode == 0) {
		    		$scope.addUser.products = data.products;
		    	}
		    }
		});
		anpopupService.openDialog({
			title : "添加用户",
			width : '500px',
			height : '500px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">' 
			+					'<li>' 
			+						'<span class="wenzi">用户名:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="addUser.username" name="username" class="input" anvalidate="required,formatname,maxlength[16],minlength[4]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">密码:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="password" ng-model="addUser.password" name="password" class="input" anvalidate="required,password,maxlength[16],minlength[6]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">确认密码:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="password" ng-model="addUser.confirmpwd" name="confirmpwd" class="input" anvalidate="required,equals[password]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">手机号:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="addUser.telephone" name="telephone" class="input" anvalidate="required,telephone,maxlength[20],minlength[7]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">邮箱:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="email" ng-model="addUser.email" name="email" class="input" anvalidate="required,email,maxlength[64],minlength[1]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">产品列表:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<div ng-repeat="product in addUser.products">'
			+								'<input type="checkbox" ng-model="product.selected" />&nbsp;{{product.productName}}&nbsp;&nbsp;&nbsp;&nbsp;'
			+							'</div>'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">备注:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="addUser.note" name="note" anvalidate="maxlength[64]" />'
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
						        url : basePath + "/createUser.do",
						        data : {
					        		userName : $scope.addUser.username, 
					        		password : $scope.addUser.password,
				        			telephone : $scope.addUser.telephone,
				        			email : $scope.addUser.email,
				        			products : utilService.propertiesFilter(getSelProducts($scope.addUser.products), new Array(["selected"])),
				        			userTypeCode : 2,
				        			note : $scope.addUser.note
				        		}
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
					    		angridService.refreshGrid();
						    	if(data.errorCode == 0) {
						    		anpopupService.openAlert("notice", "添加用户成功！");
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
	var editUser = function() {
		var rows = angridService.getSelectedRows();
		if(rows.length == 0) {
			anpopupService.openAlert('warning', "请选择所要编辑的用户！");
			return;
		}
		if(rows.length > 1) {
			anpopupService.openAlert('warning', "每次只能编辑一个用户！");
			return;
		}
		$scope.editUser = {
			userid : rows[0].userId,
			username : rows[0].userName,
			telephone : rows[0].telephone,
			email : rows[0].email,
			usertype : rows[0].userTypeCode,
			note : rows[0].note
		};
		httpService({
			data : {
		        method : 'POST',
		        url : basePath + "/listProducts.do",
		        data : {product : {}, page : {}}
		    },
		    success : function(data) {
		    	if(data.errorCode == 0) {
		    		angular.forEach(data.products, function(product, idx1) {
		    			product.selected = false;
	    				angular.forEach(rows[0].products, function(selproduct, idx2) {
	    					if(product.productId == selproduct.productId) {
	    						product.selected = true;
	    					}
	    					return false;
	    				});
	    			});
		    		$scope.editUser.products = data.products;
		    	}
		    }
		});
		anpopupService.openDialog({
			title : "编辑用户",
			width : '500px',
			height : '500px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">' 
			+					'<li>' 
			+						'<span class="wenzi">用户名:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="editUser.username" name="username" class="input" anvalidate="required,formatname,maxlength[16],minlength[4]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">手机号:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="editUser.telephone" name="telephone" class="input" anvalidate="required,telephone,maxlength[20],minlength[7]"  />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">邮箱:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="email" ng-model="editUser.email" name="email" class="input" anvalidate="required,email,maxlength[64],minlength[1]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">产品列表:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<div ng-repeat="product in editUser.products">'
			+								'<input type="checkbox" ng-model="product.selected" ng-change="editUser.selectProduct(product)" />&nbsp;{{product.productName}}&nbsp;&nbsp;&nbsp;&nbsp;'
			+							'</div>'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">备注:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="editUser.note" name="note" anvalidate="maxlength[64]" />'
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
						        	userId : $scope.editUser.userid,
					        		userName : $scope.editUser.username, 
				        			telephone : $scope.editUser.telephone,
				        			email : $scope.editUser.email,
				        			products : utilService.propertiesFilter(getSelProducts($scope.editUser.products), new Array(["selected"])),
				        			userTypeCode : 2,
				        			note : $scope.editUser.note
				        		}
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
					    		angridService.refreshGrid();
						    	if(data.errorCode == 0) {
						    		anpopupService.openAlert("notice", "添加编辑成功！");
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
	var deleteUser = function() {
		var rows = angridService.getSelectedRows();
		if(rows.length == 0) {
			anpopupService.openAlert('warning', "请选择所要删除的用户！");
			return;
		}
		var ids = [];
		angular.forEach(rows, function(row, idx) {
			ids.push(row.userId);
		});
		anpopupService.openAlert('prompt', "确定要删除下列用户：", {
			confirm : function() {
				httpService({
					data : {
			            method : 'POST',
			            url : basePath + "/deleteUsers.do",
			            data : {ids : ids}
			        },
			        success : function(data) {
			        	angridService.refreshGrid();
			        	if(data.errorCode == 0) {
			        		anpopupService.openAlert("notice", "添加删除成功！");
			        	}else if(lang["error.user."+data.errorCode]) {
				    		anpopupService.openAlert('warning', lang["error.user."+data.errorCode]);
				    	}else {
				    		anpopupService.openAlert('warning', lang["error."+data.errorCode]);
				    	}
			        }
				});
			}
		});
	};
	var refreshUser = function() {
		angridService.refreshGrid();
	};
	
	var updatePwd = function() {
		var rows = angridService.getSelectedRows();
		if(rows.length == 0) {
			anpopupService.openAlert('warning', "请选择所要编辑的用户！");
			return;
		}
		if(rows.length > 1) {
			anpopupService.openAlert('warning', "每次只能编辑一个用户！");
			return;
		}
		$scope.updatePwd = {
			userid : rows[0].userId,
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
			+							'<input type="password" ng-model="updatePwd.password" name="password" class="input" anvalidate="required,password,maxlength[16],minlength[6]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">确认密码:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="password" ng-model="updatePwd.confirmpwd" name="confirmpwd" class="input" anvalidate="required,equals[password]" />'
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
						        	userId : $scope.updatePwd.userid,
					        		password : $scope.updatePwd.password
				        		}
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
					    		angridService.refreshGrid();
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
	
	// 表格
	$scope.angridOptions = {
		url : basePath + "/listUsers.do",
		pageSize : 10,
		pageNo : 1,
		reqParam : {user : {}},
        keyAttr : "users",
        needSelect : true,
        columns :
        [{
            field : 'userName',
            displayName : '用户名',
            colWidth : '100px'
        }, {
            field : 'telephone',
            displayName : '手机号',
            colWidth : '150px'
        }, {
            field : 'email',
            displayName : '邮箱',
            colWidth : '150px'
        }, {
            field : 'products',
            displayName : '权限类型',
            colWidth : '150px',
            template : function(products) {
            	if(products.length == 0) {
            		return "";
            	}else {
            		var productList = "";
            		angular.forEach(products, function(product, idx) {
            			productList += product['productName'] + ",";
            		});
            		return productList.substr(0, productList.length-1);
            	}
            }
        }, {
            field : 'userTypeCode',
            displayName : '角色',
            colWidth : '100px',
            template : function(userType) {
            	if(userType == 1) {
            		return "超级管理员";
            	}else if(userType == 2) {
            		return "普通用户";
            	}
            }
        }, {
            field : 'note',
            displayName : '备注',
            colWidth : '200px'
        }]
    };
	//数据模型
	$scope.userModel = {
		//按钮组
		buttonGroup : [{'id' : 'add', 'cls' : 'add', 'val' : '添加', 'click' : addUser},
		                {'id' : 'edit', 'cls' : 'edit', 'val' : '编辑', 'click' : editUser},
				        {'id' : 'delete', 'cls' : 'delete', 'val' : '删除', 'click' : deleteUser},
				        {'id' : 'refresh', 'cls' : 'refresh', 'val' : '刷新', 'click' : refreshUser},
				        {'id' : 'updatepwd', 'cls' : 'update_passwd', 'val' : '修改密码', 'click' : updatePwd}]
	};
});