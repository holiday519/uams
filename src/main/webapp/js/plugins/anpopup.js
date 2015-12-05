angular.module('anPopup', ['anMask'])
	.directive('anpopup', function(anpopupService) {
		return {
        	template : '<div class="dialog" id="{{anpopup.type}}" style="top:{{anpopup.top}};left:{{anpopup.left}};width:{{anpopup.width}};height:{{anpopup.height}};">'
        	+				'<div class="dialog_top" andrag>'
        	+					'<div class="title">{{anpopup.title}}</div>'
        	+					'<div class="close"><a href="javascript:void(0)" ng-click="anpopup.closePopup()"><img src="images/close.gif"></a></div>'
        	+				'</div>'
        	+				'<div class="dialog_content" style="height:{{anpopup.contentHeight}}">'
        	+					'<template></template>'
        	+				'</div>'
        	+				'<div class="dialog_footer">'
        	+					'<ul class="dialog_footer_step">'
        	+						'<buttons ng-repeat="button in anpopup.buttons" ng-click="button.clickFunc()">{{button.label}}</buttons>'
        	+					'</ul>'
        	+				'</div>'
        	+			'</div>',
            replace : true,
            transclude : true,
            restrict : 'E',
            scope : false,
            controller : function($scope, $element, $attrs, $transclude) {
            	$scope.anpopup = anpopupService.getConfig();
            	$scope.anpopup.contentHeight = parseInt(anpopupService.getConfig().height) - 77 + "px";
            	$scope.anpopup.closePopup = anpopupService.getClosePopup();
            }
        };
	})
	.directive('template', function($compile, anpopupService) {
		return {
			restrict : 'E',
			link : function(scope, element, attrs) {
				element.replaceWith($compile(anpopupService.getConfig().template)(scope));
			}
		};
	})
	.directive('buttons', function() {
		return {
			template : '<li><a href="javascript:void(0)" ng-transclude></a></li>',
			replace : true,
			transclude : true,
            restrict : 'E',
            scope : false
		};
	})
	.factory('anpopupService', function($compile, $window, anmaskService) {
		return function() {
			// 弹出框的scope
			var scope;
			// 用于传递变量值
			var config;
			// body元素
			var body = document.getElementsByTagName('body')[0]; 
			// 创建弹出框
			var createPopup = function() {
				return document.createElement('anpopup');
			};
			// 对话框参数初始化
			var initDialog = function(options) {
				var defaults = {
					width : '500px',
					height : '500px',
					title : "对话框",
					template : "",
					buttons : []
				};
				config = angular.extend({}, defaults, options);
				config['top'] = ($window.innerHeight - parseInt(config.height))/2 + "px";
				config['left'] = ($window.innerWidth - parseInt(config.width))/2 + "px";
				config['type'] = "andialog";
				return createPopup();
			};
			var openDialog = function(options) {
				anmaskService.mask(body);
				var anbody = angular.element(body);
				anbody.append($compile(initDialog(options))(scope));
			};
			var closeDialog = function() {
				anmaskService.unmask(body);
				var anpopup = angular.element(document.getElementById(config['type']));
				anpopup.remove();
			};
			// 提示框参数初始化
			// alertType为提示框类型，content为显示的内容，callback是按钮的回调方法{confirm : function(){}, cancel : function(){}}
			var initAlert = function(alertType, content, callback) {
				var defaults = {
					width : '400px',
					height : '200px',
					template : '<div class="alert_top">'
					+				'<img src="images/alert_img.jpg">'
					+				'<span class="alert_content">'
					+					content
					+					'<div class="hr10"></div>'
					+				'</span>'
					+				'<div class="hr10"></div>'
					+			'</div>'
				};
				var alert = {
					warning : {
						title : "警告",
						buttons : [{
							label : "确定"
						}]
					},
					prompt : {
						title : "提示",
						buttons : [{
							label : "取消"
						},
						{
							label : "确定"
						}]
					},
					notice : {
						title : "通知",
						buttons : [{
							label : "确定"
						}]
					}
				};
				config = angular.extend({}, defaults, alert[alertType]);
				if(alertType == "warning" || alertType == "notice") {
					config.buttons[0]['clickFunc'] = callback && callback.confirm ? function() {closeAlert();callback.confirm();} : function() {closeAlert();};
				}
				if(alertType == "prompt") {
					config.buttons[0]['clickFunc'] = callback && callback.cancel ? function() {closeAlert();callback.cancel();} : function() {closeAlert();};
					config.buttons[1]['clickFunc'] = callback && callback.confirm ? function() {closeAlert();callback.confirm();} : function() {closeAlert();};
				}
				config['top'] = (window.innerHeight - parseInt(config.height))/2 + "px";
				config['left'] = (window.innerWidth - parseInt(config.width))/2 + "px";
				config['type'] = "analert";
				return createPopup();
			};
			var openAlert = function(alertType, content, callback) {
				anmaskService.mask(body);
				var anbody = angular.element(body);
				anbody.append($compile(initAlert(alertType, content, callback))(scope));
			};
			var closeAlert = function() {
				anmaskService.unmask(body);
				var anpopup = angular.element(document.getElementById(config['type']));
				anpopup.remove();
			};
			
			return {
				getConfig : function() {
					return config;
				},
				getClosePopup : function() {
					if(config.type == "andialog") {
						return function() {
							closeDialog();
						};
					}
					if(config.type == "analert") {
						return function() {
							closeAlert();
						};
					}
				},
				setScope : function(nowScope) {
					scope = nowScope;
				},
				openDialog : openDialog,
				closeDialog : closeDialog,
				openAlert : openAlert,
				closeAlert : closeAlert
			};
		}();
	})
	// 添加该属性的元素可以被拖动
	.directive('andrag', function() {
		return {
            restrict : 'A',
            link : function(scope, element, attrs) {
            	var anbody = angular.element(document.getElementsByTagName('body')[0]);
            	element.bind('mousedown', function(e) {
            		var oldTop = parseInt(scope.anpopup.top);
            		var oldLeft = parseInt(scope.anpopup.left);
            		var oldX = e.clientX;
            		var oldY = e.clientY;
            		anbody.bind('mousemove', function(e) {
            			scope.$apply(function() {
            				scope.anpopup.top = oldTop + e.clientY - oldY + "px";
                			scope.anpopup.left = oldLeft + e.clientX - oldX + "px";
                		});
            		});
            	});
            	element.bind('mouseup', function() {
            		anbody.unbind('mousemove');
            	});
            }
		};
	});