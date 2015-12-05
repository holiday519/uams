angular.module('anGrid', [])
    .directive('angrid', function(optionsService, widthService, angridService, $timeout, httpService) {
        return {
            template : '<div style="min-width: {{angrid.minGridWidth}}px">'
            +               '<div id="title">'
            +                   '<anhead class="cen"></anhead>'
            +               '</div>'
            +               '<div id="details">'
            +                   '<anrows class="fx" ng-repeat="rowData in gridData"></anrows>'
            +               '</div>'
            +               '<div class="bo">'
            +                   '<img src="images/bo.png" style="width: {{gridWidth}}px">'
            +               '</div>'
			+				'<anpage id="page"></anpage>'
            +          '</div>',
            replace : true,
            restrict : 'E',
            scope : false,
            transclude : true,
            controller : function($scope, $element, $attrs, $transclude) {
            	// 注册$scope
            	angridService.setScope($scope);
            	
            	var that = this;
            	// 获取配置
                this.config = optionsService.getGridConfig($scope.$eval($attrs.options));
				// 存储配置
                optionsService.setConfig(this.config);
                // 表格宽度
                $scope.gridWidth = $element[0].clientWidth;
                // 请求数据的方法
                $scope.ajaxData = function() {
                	httpService({
                		data : {
    		                method : 'POST',
    		                url : that.config.url,
    		                data : angular.extend({}, that.config.reqParam, {page : {pageSize : that.config.pageSize, pageNo : that.config.pageNo}})
    		            },
    		            success : function(data) {
    		            	$scope.gridData = data[that.config.keyAttr];
    						$scope.pageData = data['page'];
    		            }
                	});
                };
            },
            require : '^angrid',
            link : function(scope, element, attrs, angridCtrl) {
                // 表格的最小宽度
                scope.angrid = {
                	minGridWidth : widthService.getMinGridWidth(angridCtrl.config.columns, angridCtrl.config.needSelect)
                };
				// 设置一个监听器，监听页面大小的改变            	
            	window.onresize = function() {
	        		$timeout(function() {
	        			scope.gridWidth = element[0].clientWidth;
	        			scope.anblank.blankWidth = widthService.getBlankWidth(element[0].clientWidth, angridCtrl.config.columns, angridCtrl.config.needSelect);
	        		}, 800);
	        	};
	        	scope.ajaxData();
            }
        };
    })
    .directive('anhead', function() {
        return {
            template : '<ul>'
            +			   '<li ng-class="anhead.chkboxClassFunc()" ng-show="anhead.chkboxStat" ng-click="anhead.chkboxClickFunc()"></li>'
            +              '<antitles ng-repeat="title in anhead.titles" class="{{title.titleClass}}" style="width: {{title.colWidth}}"></antitles>'
            +			   '<anblank></anblank>'
            +          '</ul>',
            replace : true,
            restrict : 'E',
            scope : false,
            transclude : true,
            require : '^angrid',
            link : function(scope, element, attrs, angridCtrl) {
            	scope.anhead = {
            		titles : angridCtrl.config.columns,
            		chkboxStat : angridCtrl.config.needSelect,
            		selected : false, // 默认状态为未被选中
            		chkboxClassFunc : function() {
            			return this.selected ? 'checkedClass' : 'checkClass';
            		},
            		chkboxClickFunc : function() {
            			var selected = this.selected = !this.selected;
            			angular.forEach(scope.gridData, function(rowData, idx) {
            				rowData.selected = selected;
            			});
            		}
            	};
            }
        };
    })
    .directive('antitles', function() {
        return {
            template : '<li>{{antitles.titleName}}</li>',
            replace : true,
            restrict : 'E',
            scope : false,
            transclude : true,
            require : '^angrid',
            link : function(scope, element, attrs) {
            	scope.antitles = {
            		titleName : scope.title.displayName
            	};
            }
        };
    })
    .directive('anrows', function() {
		return {
            template : '<ul ng-click="anrows.rowClickFunc($event)" ng-class="anrows.rowClassFunc()">'
            +			   '<ancheckbox></ancheckbox>'
            +			   '<ancolumns ng-repeat="column in anrows.columns" class="{{column.columnClass}}" style="width: {{column.colWidth}}"></ancolumns>'
            +		   '</ul>',
            replace : true,
            restrict : 'E',
            scope : false,
            transclude : true,
            require : '^angrid',
            link : function(scope, element, attrs, angridCtrl) {
            	// 默认行都是未被选中状态
            	scope.rowData.selected = false;
            	scope.anrows = {
            		columns : angridCtrl.config.columns,
            		rowClickFunc : function() {
            			scope.rowData.selected = !scope.rowData.selected;
            		},
            		rowClassFunc : function() {
            			return scope.rowData.selected ? 'rowSelected' : '';
            		}
            	};
            }
        };
    })
    .directive('ancolumns', function($compile) {
		return {
            template : '<li class="text" title="{{ancolumns.colContent}}">{{ancolumns.colContent}}</li>',
            replace : true,
            scope : false,
            transclude : true,
            restrict : 'E',
            link : function(scope, element, attrs) {
            	scope.ancolumns = {};
            	if(scope.column.htmlTemplate) { // 此方法将返回一个带有html标签的文本，在标签上可添加方法
            		element.html($compile(angular.element(scope.column.htmlTemplate(scope.rowData[scope.column.field], scope.rowData)))(scope));
            	}else if(scope.column.template) { // 此方法订制显示的方式
            		scope.ancolumns.colContent = scope.column.template.call(this, scope.rowData[scope.column.field], scope.rowData);
            	}else {
            		scope.ancolumns.colContent = scope.rowData[scope.column.field];
            	}
            }
        };
    })
    .directive('anpage', function(optionsService, $compile) {
        return {
        	template : '<div>'
            +               '<div class="cent" style="width: {{gridWidth}}px">'
            +                   '<div class="word">每页显示{{pageData.pageSize}}条 共{{pageData.amount}}条</div>'
            +                   '<div class="turn">'
            +                       '<ul>'
            +                           '<li class="turnpage"><a href="javascript:void(0)" ng-click="anpage.firstPageFunc()">&lt; 第一页</a></li>'
            +                           '<li class="turnpage"><a href="javascript:void(0)" ng-click="anpage.prevPageFunc()">&lt;&lt; 上一页</a></li>'
            +							'<li id="pageinfo"></li>'
            +                           '<li class="turnpage"><a href="javascript:void(0)" ng-click="anpage.nextPageFunc()">下一页 &gt;&gt;</a></li>'
            +                           '<li class="turnpage"><a href="javascript:void(0)" ng-click="anpage.lastPageFunc()">最后一页 &gt;</a></li>'
            +							'<li class="enter">第&nbsp;<input type="text" ng-model="anpage.pageno" />&nbsp;页</li>'
            +							'<li class="table_search"><a href="javascript:void(0)" ng-click="anpage.skipPageFunc(anpage.pageno)">跳转</a></li>'
            +                       '</ul>'
            +                   '</div>'
            +               '</div>'
            +           '</div>',
            replace : true,
            transclude : true,
            restrict : 'E',
            require : '^angrid',
            link : function(scope, element, attrs, angridCtrl) {
            	scope.anpage = {
            		turnPageFunc : function(pageno) {
            			angridCtrl.config.pageNo = pageno;
            			optionsService.setConfig(angridCtrl.config);
            			scope.ajaxData();
            		},
            		firstPageFunc : function() {
            			this.turnPageFunc(1);
            		},
            		lastPageFunc : function() {
            			var amount = parseInt((scope.pageData.amount / scope.pageData.pageSize) + 1);
	            		this.turnPageFunc(amount);
            		},
            		prevPageFunc : function() {
	            		var pageno = scope.pageData.pageNo;
	            		if(pageno - 1 > 0) {
	            			this.turnPageFunc(pageno - 1);
	            		}
            		},
            		nextPageFunc : function() {
            			var amount = parseInt((scope.pageData.amount / scope.pageData.pageSize) + 1);
	            		var pageno = scope.pageData.pageNo;
            			if(pageno + 1 <= amount) {
            				this.turnPageFunc(pageno + 1);
            			}
            		},
            		skipPageFunc : function(pageno) {
            			var amount = parseInt((scope.pageData.amount / scope.pageData.pageSize) + 1);
            			if(pageno > amount) {
            				this.turnPageFunc(amount);
            			}else if(pageno == 0) {
            				this.turnPageFunc(1);
            			}else {
            				this.turnPageFunc(pageno);
            			}
            		}
            	};
            	
            	scope.$watch('pageData', function() {
            		if(scope.pageData) {
            			var pages = element.find('.pageno');
            			for(var i = 0; i < pages.length; i++) {
            				if(i == 0) {
            					angular.element(pages[i]).replaceWith('<li id="pageinfo"></li>');
            				}else {
            					angular.element(pages[i]).remove();
            				}
            			}
            			var amount = parseInt((scope.pageData.amount / scope.pageData.pageSize) + 1);
	            		var pageno = scope.pageData.pageNo;
	            		var pageinfo = '';
		            	for (var p = 1; p <= amount; p++) {
		            		if(p == pageno) {
		            			pageinfo += '<li class="pageno turn_active"><a href="javascript:void(0)" ng-click="anpage.turnPageFunc(' + p + ')">' + p + '</a></li>';
		            		}else {
		            			pageinfo += '<li class="pageno"><a href="javascript:void(0)" ng-click="anpage.turnPageFunc(' + p + ')">' + p + '</a></li>';
		            		}
		            	}
		            	element.find('#pageinfo').replaceWith($compile(pageinfo)(scope));
            		}
            	});
            }
        };
    })
    .directive('ancheckbox', function() {
    	return {
            template : '<li ng-class="ancheckbox.chkboxClassFunc()" ng-show="ancheckbox.chkboxStat"></li>',
            replace : true,
            scope : false,
            transclude : true,
            restrict : 'E',
            require : '^angrid',
            link : function(scope, element, attrs, angridCtrl) {
            	scope.ancheckbox = {
            		chkboxStat : angridCtrl.config.needSelect,
            		chkboxClassFunc : function() {
            			return scope.rowData.selected ? 'checkedClass' : 'checkClass';
					}
            	};
            }
        };
    })
    .directive('anblank', function(widthService) {
    	return {
            template : '<li style="width: {{anblank.blankWidth}}px"></li>',
            replace : true,
            scope : false,
            transclude : true,
            restrict : 'E',
            require : '^angrid',
            link : function(scope, element, attrs, angridCtrl) {
            	scope.anblank = {
            		blankWidth : widthService.getBlankWidth(scope.gridWidth, angridCtrl.config.columns, angridCtrl.config.needSelect)
            	};
            }
        };
    })
    .factory('optionsService', function() {
    	return {
    		config : {},
    		getGridConfig : function(options) {
    			 var defaults = {
					url : "",
					pageSize : 10,
					pageNo : 1,
					reqParam : null,
					keyAttr : "users",
        			needSelect : true
                };
                return angular.extend({}, defaults, options);
    		},
    		setConfig : function(config) {
    			this.config = config;
    		},
    		getConfig : function() {
    			return this.config;
    		}
    	};
    })
    .factory('widthService', function() { //获取宽度和重画页面
    	return {
    		getColumnTotalWidth : function(columns) {
    			var columnTotalWidth = 0;
    			angular.forEach(columns, function(column, idx) {
	                if(column.colWidth) {
	                    columnTotalWidth += parseInt(column.colWidth);
	                }
	            });
	            return columnTotalWidth;
    		},
    		getBlankWidth : function(gridWidth, columns, needSelect) {
    			var blankWidth = 0;
    			var columnTotalWidth = this.getColumnTotalWidth(columns);
    			if(needSelect) { // 减去多选框的宽度
	            	blankWidth = gridWidth - columnTotalWidth - 30;
	            }else {
	            	blankWidth = gridWidth - columnTotalWidth;
	            }
            	return blankWidth > 30 ? blankWidth : 30;
    		},
    		getMinGridWidth : function(columns, needSelect) {
    			return needSelect ? this.getColumnTotalWidth(columns) + 60 : this.getColumnTotalWidth(columns) + 30;
    		}
    	};
    })
    .factory('angridService', function(optionsService, httpService) {
    	return {
    		scope : {},
    		setScope : function(scope) {
    			this.scope = scope;
    		},
    		getScope : function() {
    			return this.scope;
    		},
    		getSelectedRows : function() {
    			var selectedRows = [];
    			angular.forEach(this.scope.gridData, function(rowData, idx) {
    				if(rowData.selected) {
    					selectedRows.push(rowData);
    				}
    			});
    			return selectedRows;
    		},
    		refreshGrid : function() {
    			var that = this;
    			var config = optionsService.getConfig();
    			httpService({
    				data : {
    	                method : 'POST',
    	                url : config.url,
    	                data : angular.extend({}, config.reqParam, {page : {pageSize : config.pageSize, pageNo : config.pageNo}})
    	            },
    	            success : function(data) {
                		that.scope.gridData = data[config.keyAttr];
    					that.scope.pageData = data['page'];
    	            }
    			});
    		}
    	};
    });
    
    