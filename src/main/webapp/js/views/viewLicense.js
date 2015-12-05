basicApp.controller('viewLicense.ctrl', function($scope, httpService, $filter, $location, angridService, anpopupService, validateService) {
	anpopupService.setScope($scope);
	var addLicense = function() {
		$scope.addLicense = {
			product : {},
			changeProduct : function() {
				return this.product.productId ? this.product.productId == 1 : true;
			},
			// uid or sn
			infotype : 1,
			infotypeDisabled : function(type) {
				return this.infotype != type;
			},
			// 正式版   or 试用版
			versiontype : 1,
			versiontypeDisabled : function(type) {
				return this.versiontype != type;
			}
		};
		
		httpService({
			data : {
		        method : 'POST',
		        url : basePath + "/listProducts.do",
		        data : {product : {}, loginUserId : loginUserId, page : {}}
		    },
		    success : function(data) {
		    	if(data.errorCode == 0) {
		    		$scope.addLicense.products = data.products;
		    		$scope.addLicense.product = data.products[0];
		    	}
		    }
		});
		// 改变产品，查询该产品所有的版本
		$scope.$watch('addLicense.product', function() {
			httpService({
				data : {
			        method : 'POST',
			        url : basePath + "/listVersions.do",
			        data : {version : {product : $scope.addLicense.product}, loginUserId : loginUserId, page : {}}
			    },
			    success : function(data) {
			    	if(data.errorCode == 0) {
			    		$scope.addLicense.versions = data.versions;
			    		$scope.addLicense.version = data.versions[0];
			    	}
			    }
			});
    	});
		
		anpopupService.openDialog({
			title : "生成license",
			width : '500px',
			height : '600px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">'
			+					'<li>' 
			+						'<span class="wenzi">license名称:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="addLicense.licensename" name="licensename" class="input" anvalidate="required,formatname,maxlength[16],minlength[6]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">所属产品:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<select ng-model="addLicense.product" class="input" ng-options="product.productName for product in addLicense.products" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">所属版本:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<select ng-model="addLicense.version" class="input" ng-options="version.versionNo for version in addLicense.versions" />'
			+						'</span>'
			+					'</li>'
			// 以下是suite配置
			+					'<li ng-show="addLicense.changeProduct()">' 
			+						'<span class="wenzi">指纹信息:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="addLicense.fingerprintinfo" name="fingerprintinfo" anvalidate="required,maxlength[500]" />'
			+						'</span>'
			+					'</li>'
			+					'<li ng-show="addLicense.changeProduct()">' 
			+						'<span class="wenzi">CPU个数:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="number" ng-model="addLicense.cpuamount" name="cpuamount" class="input" min="0" anvalidate="required" />'
			+						'</span>'
			+					'</li>'
			+					'<li ng-show="addLicense.changeProduct()">' 
			+						'<span class="wenzi">CPU核数:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="number" ng-model="addLicense.nuclearamount" name="nuclearamount" class="input" min="0" anvalidate="required" />'
			+						'</span>'
			+					'</li>'
			+					'<li ng-show="addLicense.changeProduct()">' 
			+						'<span class="wenzi">主机个数:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="number" ng-model="addLicense.hostamount" name="hostamount" class="input" min="0" anvalidate="required" />'
			+						'</span>'
			+					'</li>'
			// 以下是vdi配置
			+					'<li ng-hide="addLicense.changeProduct()">'
			+						'<span class="wenzi"><input type="radio" ng-model="addLicense.infotype" value="1" />UID:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="addLicense.uid" name="uid" anvalidate="required,maxlength[500]" ng-disabled="addLicense.infotypeDisabled(1)" />'
			+						'</span>'
			+					'</li>'
			+					'<li ng-hide="addLicense.changeProduct()">'
			+						'<span class="wenzi"><input type="radio" ng-model="addLicense.infotype" value="2" />S/N:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="addLicense.sn" name="sn" class="input" anvalidate="required,maxlength[64]" ng-disabled="addLicense.infotypeDisabled(2)" />'
			+						'</span>'
			+					'</li>'
			+					'<li ng-hide="addLicense.changeProduct()">' 
			+						'<span class="wenzi">最大连接数:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="number" ng-model="addLicense.maxconnectamount" name="maxconnectamount" class="input" min="0" anvalidate="required" />'
			+						'</span>'
			+					'</li>'
			+					'<li ng-hide="addLicense.changeProduct()">'
			+						'<span class="wenzi">版本类别:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="radio" ng-model="addLicense.versiontype" value="2" />试用版&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp'
			+							'<input type="radio" ng-model="addLicense.versiontype" value="1" />正式版'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">有效天数:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="number" ng-model="addLicense.validdayamount" name="validdayamount" class="input" min="0" anvalidate="required" ng-disabled="addLicense.versiontypeDisabled(1)" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">备注:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="addLicense.note" name="note" anvalidate="maxlength[64]" />'
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
						var url = {
							1 : basePath + "/createSuiteLicense.do",
							2 : basePath + "/createVDILicense.do"
						};
						var data = {
							1 : {
								licenseName : $scope.addLicense.licensename,
								version : $scope.addLicense.version,
								createUser : {userId : loginUserId},
								note : $scope.addLicense.note,
								registerInfo : $scope.addLicense.fingerprintinfo,
								validdayAmount : $scope.addLicense.validdayamount,
								cpuAmount : $scope.addLicense.cpuamount,
								nuclearAmout : $scope.addLicense.nuclearamount,
								hostAmount : $scope.addLicense.hostamount
							},
							2 : {
								licenseName : $scope.addLicense.licensename,
								version : $scope.addLicense.version,
								createUser : {userId : loginUserId},
								note : $scope.addLicense.note,
								registerTypeCode : $scope.addLicense.infotype,
								registerInfo : $scope.addLicense.infotype == 1 ? $scope.addLicense.uid : $scope.addLicense.sn,
								usageTypeCode : $scope.addLicense.versiontype,
								validdayAmount : $scope.addLicense.versiontype == 1 ? $scope.addLicense.validdayamount : 7,
								maxConnectAmount : $scope.addLicense.maxconnectamount
							}
						};
						
						httpService({
							data : {
						        method : 'POST',
						        url : url[$scope.addLicense.product.productId],
						        data : data[$scope.addLicense.product.productId]
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
					    		angridService.refreshGrid();
						    	if(data.errorCode == 0) {
						    		anpopupService.openAlert('notice', "license生成成功！");
						    	}else if(lang["error.license."+data.errorCode]) {
						    		anpopupService.openAlert('warning', lang["error.license."+data.errorCode]);
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
	var deleteLicense = function() {
		var rows = angridService.getSelectedRows();
		if(rows.length == 0) {
			anpopupService.openAlert('warning', "请选择所要删除的license！");
			return;
		}
		var ids = [];
		angular.forEach(rows, function(row, idx) {
			ids.push(row.licenseId);
		});
		anpopupService.openAlert('prompt', "确认要删除下列license：", {
			confirm : function() {
				httpService({
					data : {
			            method : 'POST',
			            url : basePath + "/deleteLicenses.do",
			            data : {ids : ids}
			        },
			        success : function(data) {
			        	angridService.refreshGrid();
			        	if(data.errorCode == 0) {
			        		anpopupService.openAlert('notice', "license删除成功！");
			        	}else {
			        		anpopupService.openAlert('warning', lang["error."+data.errorCode]);
			        	}
			        }
				});
			}
		}); 
	};
	var refreshLicense = function() {
		angridService.refreshGrid();
	};
	
	// 表格
	$scope.angridOptions = {
		url : basePath + "/listLicenses.do",
		pageSize : 10,
		pageNo : 1,
		reqParam : {license : {}, loginUserId : loginUserId, page : {}},
        keyAttr : "licenses",
        needSelect : true,
        columns :
        [{
            field : 'licenseName',
            displayName : '名称',
            colWidth : '150px'
        },{
            field : 'version',
            displayName : '所属产品',
            colWidth : '150px',
            template : function(version) {
            	return version.product.productName;
            }
        },{
            field : 'version',
            displayName : '所属版本',
            colWidth : '100px',
            template : function(version) {
            	return version.versionNo;
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
            colWidth : '100px',
            template : function(date) {
            	return $filter('date')(date, "yyyy-MM-dd");
            }
        }, {
            field : 'note',
            displayName : '备注',
            colWidth : '150px'
        }, {
        	field : 'licenseURL',
        	displayName : '操作',
        	colWidth : '150px',
        	htmlTemplate : function(licensePath, licenseObj) {
        		var content = angular.element(document.createElement('div'));
            	var url = $location.absUrl();
            	var download = angular.element('<a href="' + url.substring(0, url.indexOf("/uams/") + 6) + licensePath.replace("\\", "/") + '" class="export">下载</a>');
            	var detail = angular.element('<a href="javascript:void(0)" class="export">详情</a>');
				detail.click(function() {
					var type = licenseObj.maxConnectAmount ? false : true;
					anpopupService.openDialog({
						title : "license详情",
						width : '500px',
						height : '500px',
						template : '<div class="dialog_template">'
						+				'<ul class="form">'
						+					'<li>'
						+						'<span class="wenzi">名称:</span>'
						+						'<span = class="spaninput">'
						+							'<input type="text" value="' + licenseObj.licenseName + '" class="input" readonly="readonly"/>'
						+						'<span>'
						+					'</li>'
						+					'<li>'
						+						'<span class="wenzi">所属产品:</span>'
						+						'<span class="spaninput">'
						+							'<input type="text" value="' + licenseObj.version.product.productName + '" class="input" readonly="readonly"/>'
						+						'</span>'
						+					'</li>'
						+					'<li>'
						+						'<span class="wenzi">指纹信息:</span>'
						+						'<span class="spaninput">'
						+							'<textarea  readonly="readonly">' +licenseObj.registerInfo + '</textarea>'
						+						'</span>'
						+					'</li>'
						+					'<li ng-show="' + type + '">'
						+						'<span class="wenzi">有效天数:</span>'
						+						'<span class="spaninput">'
						+							'<input type="text" value="' + licenseObj.validdayAmount + '" class="input" readonly="readonly"/>'
						+						'</span>'
						+					'</li>'
						+					'<li ng-show="' + type + '">'
						+						'<span class="wenzi">核数:</span>'
						+						'<span class="spaninput">'
						+							'<input type="text" value="' + licenseObj.cpuAmount + '" class="input" readonly="readonly"/>'
						+						'</span>'
						+					'</li>'
						+					'<li ng-show="' + type + '">'
						+						'<span class="wenzi">cpu个数:</span>'
						+						'<span class="spaninput">'
						+							'<input type="text" value="' + licenseObj.nuclearAmout + '" class="input" readonly="readonly"/>'
						+						'</span>'
						+					'</li>'
						+					'<li ng-show="' + type + '">'
						+						'<span class="wenzi">节点个数:</span>'
						+						'<span class="spaninput">'
						+							'<input type="text" value="' + licenseObj.hostAmount + '" class="input" readonly="readonly"/>'
						+						'</span>'
						+					'</li>'
						+					'<li ng-hide="' + type + '">'
						+						'<span class="wenzi">最大连接数:</span>'
						+						'<span class="spaninput">'
						+							'<input type="text" value="' + licenseObj.maxConnectAmount + '" class="input" readonly="readonly"/>'
						+						'</span>'
						+					'</li>'
						+				'</ul>'
						+			'</div>',
						buttons : [{
							label : "确定",
							clickFunc : function() {
								anpopupService.closeDialog();
							}
						}]
					});
				});
				return content.append(download).append('<span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>').append(detail);
            }
        }]
    };
	//数据模型
	$scope.licenseModel = {
		//按钮组
		buttonGroup : [{'id' : 'add', 'cls' : 'add', 'val' : '生成', 'click' : addLicense},
				        {'id' : 'delete', 'cls' : 'delete', 'val' : '删除', 'click' : deleteLicense},
				        {'id' : 'refresh', 'cls' : 'refresh', 'val' : '刷新', 'click' : refreshLicense}]
	};
});