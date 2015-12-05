basicApp.controller('viewProduct.ctrl', function($scope, httpService, $filter, $location, angridService, anpopupService, validateService) {
	anpopupService.setScope($scope);
	var addProduct = function() {
		$scope.addProduct = {};
		httpService({
			data : {
		        method : 'POST',
		        url : basePath + "/listProducts.do",
		        data : {product : {}, loginUserId : loginUserId, page : {}}
		    },
		    success : function(data) {
		    	if(data.errorCode == 0) {
		    		$scope.addProduct.products = data.products;
		    		$scope.addProduct.product = data.products[0];
		    	}
		    }
		});
		anpopupService.openDialog({
			title : "添加版本",
			width : '500px',
			height : '300px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">'
			+					'<li>' 
			+						'<span class="wenzi">版本名称:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="addProduct.versionno" name="versionno" class="input" anvalidate="required,maxlength[16]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">所属产品:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<select ng-model="addProduct.product" class="input" ng-options="product.productName for product in addProduct.products" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">备注:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="addProduct.note" name="note" anvalidate="maxlength[64]" />'
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
						        url : basePath + "/createVersion.do",
						        data : {
						        	versionNo : $scope.addProduct.versionno,
						        	product : $scope.addProduct.product, 
				        			createUser : {userId : loginUserId},
				        			note : $scope.addProduct.note
				        		}
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
					    		angridService.refreshGrid();
						    	if(data.errorCode == 0) {
						    		anpopupService.openAlert('notice', "产品创建成功！");
						    	}else if(lang["error.version."+data.errorCode]) {
						    		anpopupService.openAlert('warning', lang["error.version."+data.errorCode]);
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
	var deleteProduct = function() {
		var rows = angridService.getSelectedRows();
		if(rows.length == 0) {
			anpopupService.openAlert('warning', "请选择所要删除的版本！");
			return;
		}
		var ids = [];
		angular.forEach(rows, function(row, idx) {
			ids.push(row.versionId);
		});
		anpopupService.openAlert('prompt', "确认要删除下列版本：", {
			confirm : function() {
				httpService({
					data : {
			            method : 'POST',
			            url : basePath + "/deleteVersions.do",
			            data : {ids : ids}
			        },
			        success : function(data) {
			        	angridService.refreshGrid();
			        	if(data.errorCode == 0) {
			        		anpopupService.openAlert('notice', "版本删除成功！");
			        	}else if(lang["error.version."+data.errorCode]) {
				    		anpopupService.openAlert('warning', lang["error.version."+data.errorCode]);
				    	}else {
				    		anpopupService.openAlert('warning', lang["error."+data.errorCode]);
				    	}
			        }
				});
			}
		}); 
	};
	var refreshProduct = function() {
		angridService.refreshGrid();
	};
	
	// 表格
	$scope.angridOptions = {
		url : basePath + "/listVersions.do",
		pageSize : 10,
		pageNo : 1,
		reqParam : {version : {}, loginUserId : loginUserId},
        keyAttr : "versions",
        needSelect : true,
        columns :
        [{
        	field : 'versionNo',
            displayName : '版本名称',
            colWidth : '150px'
        }, {
            field : 'product',
            displayName : '所属产品',
            colWidth : '150px',
            template : function(product) {
            	return product.productName;
            }
        }, {
            field : 'createUser',
            displayName : '创建者',
            colWidth : '100px',
            template : function(user) {
            	return user.userName;
            }
        }, {
            field : 'createDate',
            displayName : '创建日期',
            colWidth : '150px',
            template : function(date) {
            	return $filter('date')(date, "yyyy-MM-dd");
            }
        }, {
            field : 'publicKey',
            displayName : 'KEY',
            colWidth : '100px',
            htmlTemplate : function(keyPath) {
            	var url = $location.absUrl();
            	return '<a href="' + url.substring(0, url.indexOf("/uams/") + 6) + keyPath.replace("\\", "/") + '" class="export">导出</a>';
            }
        }, {
            field : 'note',
            displayName : '备注',
            colWidth : '200px'
        }]
    };
	//数据模型
	$scope.productModel = {
		//按钮组
		buttonGroup : [{'id' : 'add', 'cls' : 'add', 'val' : '添加', 'click' : addProduct},
				        {'id' : 'delete', 'cls' : 'delete', 'val' : '删除', 'click' : deleteProduct},
				        {'id' : 'refresh', 'cls' : 'refresh', 'val' : '刷新', 'click' : refreshProduct}]
	};
});