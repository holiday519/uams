basicApp.controller('viewVDIUKey.ctrl', function($scope, httpService, angridService, anpopupService, validateService, $upload, $location) {
	anpopupService.setScope($scope);
	var filename = null;
	var addVDIUKey = function() {
		$scope.addVDIUKey = {
			uploadUKey : function($files) {
				filename = $files[0].name;
				$upload.upload({
					url : basePath + "/uploadUKey.do",
					file : $files[0]
				}).then(function() {
					
				});
			}
		};
		anpopupService.openDialog({
			title : "添加license",
			width : '500px',
			height : '400px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">'
			+					'<li>' 
			+						'<span class="wenzi">license名称:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="addVDIUKey.licensename" name="licensename" class="input" anvalidate="required,maxlength[16]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">有效期:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="number" ng-model="addVDIUKey.validdayamount" name="validdayamount" class="input" min="0" anvalidate="required" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">最大连接数:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="number" ng-model="addVDIUKey.maxconnectamount" name="maxconnectamount" class="input" min="0" anvalidate="required" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">导入UKey:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<input type="file" ng-file-select="addVDIUKey.uploadUKey($files)" onclick="this.value=null" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">备注:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="addVDIUKey.note" name="note" anvalidate="maxlength[64]" />'
			+						'</span>'
			+					'</li>'
			+				'</ul>'
			+			'</form>',
			buttons : [{
				label : "取消",
				clickFunc : function() {
					anpopupService.closeDialog();
					filename = null;
				}
			},{
				label : "完成",
				clickFunc : function() {
					if(validateService.validateForm()) {
						if(filename == null) {
							return;
						}
						httpService({
							data : {
						        method : 'POST',
						        url : basePath + "/createVDIUKey.do",
						        data : {
						        	licenseName : $scope.addVDIUKey.licensename,
						        	validdayAmount : $scope.addVDIUKey.validdayamount, 
						        	maxConnectAmount : $scope.addVDIUKey.maxconnectamount,
				        			note : $scope.addVDIUKey.note,
				        			ukeyName : filename
				        		}
						    },
						    success : function(data) {
						    	anpopupService.closeDialog();
					    		angridService.refreshGrid();
						    	if(data.errorCode == 0) {
						    		anpopupService.openAlert('notice', "license创建成功！");
						    	}else if(lang["error.version."+data.errorCode]) {
						    		anpopupService.openAlert('warning', lang["error.license."+data.errorCode]);
						    	}else {
						    		anpopupService.openAlert('warning', lang["error."+data.errorCode]);
						    	}
						    	filename = null;
						    }
						});
					}
				}
			}]
		});
	};
	var deleteVDIUKey = function() {
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
			            url : basePath + "/deleteVDIUKeys.do",
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
	var refreshVDIUKey = function() {
		angridService.refreshGrid();
	};
	// 表格
	$scope.angridOptions = {
		url : basePath + "/listVDIUKeys.do",
		pageSize : 10,
		pageNo : 1,
		reqParam : {vdiUKey : {}},
        keyAttr : "vdiUKeys",
        needSelect : true,
        columns :
        [{
            field : 'licenseName',
            displayName : '名称',
            colWidth : '150px'
        }, {
            field : 'validdayAmount',
            displayName : '有效期',
            colWidth : '100px'
        }, {
            field : 'maxConnectAmount',
            displayName : '最大连接数',
            colWidth : '100px'
        }, {
            field : 'note',
            displayName : '备注',
            colWidth : '150px'
        }, {
            field : 'licenseURL',
            displayName : '操作',
            colWidth : '150px',
            htmlTemplate : function(licensePath) {
            	var content = angular.element(document.createElement('div'));
            	var url = $location.absUrl();
            	var download = angular.element('<a href="' + url.substring(0, url.indexOf("/uams/") + 6) + licensePath.replace("\\", "/") + '" class="export">下载</a>');
            	return content.append(download);
            }
        }]
    };
	//数据模型
	$scope.vdiModel = {
		//按钮组
		buttonGroup : [{'id' : 'add', 'cls' : 'add', 'val' : '添加', 'click' : addVDIUKey},
				        {'id' : 'delete', 'cls' : 'delete', 'val' : '删除', 'click' : deleteVDIUKey},
				        {'id' : 'refresh', 'cls' : 'refresh', 'val' : '刷新', 'click' : refreshVDIUKey}]
	};
});