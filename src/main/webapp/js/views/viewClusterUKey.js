basicApp.controller('viewClusterUKey.ctrl', function($scope, httpService, angridService, anpopupService, validateService, $upload, $location) {
	anpopupService.setScope($scope);
	var filename = null;
	var addClusterUKey = function() {
		$scope.addClusterUKey = {
			suitecbox : false,
			vdicbox : false,
			fwcbox : false,
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
			height : '620px',
			template : '<form class="dialog_template" validateform>'
			+				'<ul class="form">'
			+					'<li>' 
			+						'<span class="wenzi">license名称:</span>'
			+						'<span class="xing">*</span>'
			+						'<span class="spaninput">'
			+							'<input type="text" ng-model="addClusterUKey.licensename" name="licensename" class="input" anvalidate="required,maxlength[16]" />'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<div class="wenzi1"><input type="checkbox" ng-model="addClusterUKey.suitecbox" />suite:</div>'
			+						'<ul class="form">'
			+							'<li class="divli">' 
			+								'<div>'
			+									'<span class="wenzi">数量:</span>'
			+									'<span class="xing">&nbsp;</span>'
			+									'<span class="spaninput">'
			+										'<input type="number" ng-model="addClusterUKey.suitecount" name="suitecount" class="input" min="0" anvalidate="required" ng-disabled="!addClusterUKey.suitecbox" />'
			+									'</span>'
			+								'</div>'
			+							'</li>'
			+							'<li class="divli">'
			+								'<div>'
			+									'<span class="wenzi">有效期:</span>'
			+									'<span class="xing">&nbsp;</span>'
			+									'<span class="spaninput">'
			+										'<input type="number" ng-model="addClusterUKey.suiteterm" name="suiteterm" class="input" min="0" anvalidate="required" ng-disabled="!addClusterUKey.suitecbox" />'
			+									'</span>'
			+								'</div>'
			+							'</li>'
			+						'</ul>'
			+					'</li>'
			+					'<li>' 
			+						'<div class="wenzi1"><input type="checkbox" ng-model="addClusterUKey.vdicbox" />VDI:</div>'
			+						'<ul class="form">'
			+							'<li class="divli">' 
			+								'<div>'
			+									'<span class="wenzi">数量:</span>'
			+									'<span class="xing">&nbsp;</span>'
			+									'<span class="spaninput">'
			+										'<input type="number" ng-model="addClusterUKey.vdicount" name="vdicount" class="input" min="0" anvalidate="required" ng-disabled="!addClusterUKey.vdicbox" />'
			+									'</span>'
			+								'</div>'
			+							'</li>'
			+							'<li class="divli">'
			+								'<div>'
			+									'<span class="wenzi">有效期:</span>'
			+									'<span class="xing">&nbsp;</span>'
			+									'<span class="spaninput">'
			+										'<input type="number" ng-model="addClusterUKey.vditerm" name="vditerm" class="input" min="0" anvalidate="required" ng-disabled="!addClusterUKey.vdicbox" />'
			+									'</span>'
			+								'</div>'
			+							'</li>'
			+						'</ul>'
			+					'</li>'
			+					'<li>' 
			+						'<div class="wenzi1"><input type="checkbox" ng-model="addClusterUKey.fwcbox" />防火墙:</div>'
			+						'<ul class="form">'
			+							'<li class="divli">' 
			+								'<div>'
			+									'<span class="wenzi">数量:</span>'
			+									'<span class="xing">&nbsp;</span>'
			+									'<span class="spaninput">'
			+										'<input type="number" ng-model="addClusterUKey.fwcount" name="fwcount" class="input" min="0" anvalidate="required" ng-disabled="!addClusterUKey.fwcbox" />'
			+									'</span>'
			+								'</div>'
			+							'</li>'
			+							'<li class="divli">'
			+								'<div>'
			+									'<span class="wenzi">有效期:</span>'
			+									'<span class="xing">&nbsp;</span>'
			+									'<span class="spaninput">'
			+										'<input type="number" ng-model="addClusterUKey.fwterm" name="fwterm" class="input" min="0" anvalidate="required" ng-disabled="!addClusterUKey.fwcbox" />'
			+									'</span>'
			+								'</div>'
			+							'</li>'
			+						'</ul>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">导入UKey:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<input type="file" ng-file-select="addClusterUKey.uploadUKey($files)" onclick="this.value=null">'
			+						'</span>'
			+					'</li>'
			+					'<li>' 
			+						'<span class="wenzi">备注:</span>'
			+						'<span class="xing">&nbsp;</span>'
			+						'<span class="spaninput">'
			+							'<textarea ng-model="addClusterUKey.note" name="note" anvalidate="maxlength[64]" />'
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
						if(!($scope.addClusterUKey.suitecbox || $scope.addClusterUKey.vdicbox 
								|| $scope.addClusterUKey.fwcbox)) {
							return;
						}
						httpService({
							data : {
						        method : 'POST',
						        url : basePath + "/createClusterUKey.do",
						        data : {
						        	licenseName : $scope.addClusterUKey.licensename,
						        	suiteCount : $scope.addClusterUKey.suitecount, 
				        			suiteTerm : $scope.addClusterUKey.suiteterm,
				        			vdiCount : $scope.addClusterUKey.vdicount,
				        			vdiTerm : $scope.addClusterUKey.vditerm,
						        	fwCount : $scope.addClusterUKey.fwcount, 
				        			fwTerm : $scope.addClusterUKey.fwterm,
				        			note : $scope.addClusterUKey.note,
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
	var deleteClusterUKey = function() {
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
			            url : basePath + "/deleteClusterUKeys.do",
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
	var refreshClusterUKey = function() {
		angridService.refreshGrid();
	};
	// 表格
	$scope.angridOptions = {
		url : basePath + "/listClusterUKeys.do",
		pageSize : 10,
		pageNo : 1,
		reqParam : {clusterUKey : {}},
        keyAttr : "clusterUKeys",
        needSelect : true,
        columns :
        [{
            field : 'licenseName',
            displayName : '名称',
            colWidth : '100px'
        }, {
            field : 'suiteCount',
            displayName : 'Suite个数',
            colWidth : '100px'
        }, {
            field : 'suiteTerm',
            displayName : 'Suite有效期',
            colWidth : '100px'
        }, {
            field : 'vdiCount',
            displayName : 'VDI个数',
            colWidth : '100px'
        }, {
            field : 'vdiTerm',
            displayName : 'VDI有效期',
            colWidth : '100px'
        }, {
            field : 'fwCount',
            displayName : '防火墙个数',
            colWidth : '100px'
        }, {
            field : 'fwTerm',
            displayName : '防火墙有效期',
            colWidth : '100px'
        }, {
        	field : 'note',
        	displayName : '备注',
        	colWidth : '150px'
        }, {
        	field : 'licenseUrl',
        	displayName : '操作',
        	colWidth : '100px',
        	htmlTemplate : function(licensePath) {
            	var content = angular.element(document.createElement('div'));
            	var url = $location.absUrl();
            	var download = angular.element('<a href="' + url.substring(0, url.indexOf("/uams/") + 6) + licensePath.replace("\\", "/") + '" class="export">下载</a>');
            	return content.append(download);
            }
        }]
    };
	//数据模型
	$scope.clusterModel = {
		//按钮组
		buttonGroup : [{'id' : 'add', 'cls' : 'add', 'val' : '添加', 'click' : addClusterUKey},
				        {'id' : 'delete', 'cls' : 'delete', 'val' : '删除', 'click' : deleteClusterUKey},
				        {'id' : 'refresh', 'cls' : 'refresh', 'val' : '刷新', 'click' : refreshClusterUKey}]
	};
});