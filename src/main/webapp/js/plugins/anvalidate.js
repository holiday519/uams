/**
 * 表单验证插件
 * 默认验证：required,maxlength,minlength,equals
 */
angular.module('anValidate', [])
	.directive('validateform', function(formService) {
		return {
			//在这里要创建一个新的scope
			scope : true,
			link : function(scope, element, attrs) {
				formService.setForm(element);
				formService.setScope(scope);
			}
		};
	})
	.directive('anvalidate', function(validateService, promptService) {
		return {
			require : "ngModel",
			scope : false,
			link : function(scope, element, attrs, ngModelCtrl) {
				if(!attrs.anvalidate || attrs.anvalidate=="") {
					return;
				}
				var name = attrs.name;
				var options = validateService.parseOpts(attrs.anvalidate);
				// 验证操作
				var validate = function() {
					var value = ngModelCtrl.$viewValue;
					if(value == undefined || value == null) {
						value = "";
					}
					var errorMsg = validateService.validate(options, value.trim());
					if(errorMsg != undefined) { // 未通过验证，提示
						if(!promptService.isPromptExists(name)) { // 如果该提示存在，则不再提示
							promptService
								.setPromptId(name)
								.setPromptPosition(element.position().top, element.position().left, element.width(), element.height())
								.setErrorMsg(errorMsg)
								.createPrompt();
						}
					}else { // 通过了验证，删除提示
						promptService.removePrompt(name);
					}
				};
				element.bind('blur', function() {
					scope.$apply(function() {
						validate();
					});
				});
				scope.$on('anValidate.validate', function() {
					if(!element.is(':hidden') && !scope.$eval(attrs.ngDisabled)) {
						validate();
					}
				});
			}
		};
	})
	.directive('validateprompt', function(promptService) { // 验证插件的错误提示框
		return {
			template : '<div id="{{validateprompt.promptId}}" class="formError" style="top: {{validateprompt.promptTop}}px;left: {{validateprompt.promptLeft}}px;" ng-click="validateprompt.closePrompt(validateprompt.promptId)">'
        	+				'<div class="formErrorContent">{{validateprompt.errorMsg}}</div>'
        	+				'<div class="formErrorArrow">'
        	+					'<div class="line10"></div>'
        	+					'<div class="line9"></div>'
        	+					'<div class="line8"></div>'
        	+					'<div class="line7"></div>'
        	+					'<div class="line6"></div>'
        	+					'<div class="line5"></div>'
        	+					'<div class="line4"></div>'
        	+					'<div class="line3"></div>'
        	+					'<div class="line2"></div>'
        	+					'<div class="line1"></div>'
        	+				'</div>'
        	+			'</div>',
            replace : true,
            restrict : 'E',
            scope : true,
            link : function(scope, element, attrs) {
            	scope.validateprompt = {
        			promptId : promptService.promptId,
            		promptTop : promptService.promptPosition.top,
            		promptLeft : promptService.promptPosition.left,
            		errorMsg : promptService.errorMsg,
            		closePrompt : promptService.removePrompt
            	};
            }
        };
	})
	// 返回页面上的form元素
	.factory('formService', function() {
		return {
			form : {},
			scope : {},
			setScope : function(scope) {
				this.scope = scope;
			},
			getScope : function() {
				return this.scope;
			},
			setForm : function(form) {
				this.form = form;
			},
			getForm : function() {
				return this.form;
			},
			getPrompt : function(id) {
				return this.form.find("#"+id);
			},
			getPrompts : function() {
				return this.form.find(".formError");
			},
			getInput : function(name) {
				return this.form.find("[name='" + name + "']");
			}
		};
	})
	.factory('promptService', function($compile, formService) {
		return {
			promptId : "",
			promptPosition : {
				top : 0,
				left : 0
			},
			errorMsg : "",
			setPromptId : function(id) {
				this.promptId = id;
				return this;
			},
			setPromptPosition : function(fieldTop, fieldLeft, fieldWidth, fieldHeight) { // 由输入框的位置判定验证框的位置
				this.promptPosition.left = fieldLeft + fieldWidth - 30;
				this.promptPosition.top = fieldTop - 18 * 2;
				return this;
			},
			setErrorMsg : function(errorMsg) {
				this.errorMsg = errorMsg;
				return this;
			},
			createPrompt : function() {
				formService.getForm().append($compile("<validateprompt></validateprompt>")(formService.getScope()));
			},
			removePrompt : function(id) {
				var anprompt = formService.getPrompt(id);
				anprompt.remove();
			},
			isPromptExists : function(id) {
				var anprompt = formService.getPrompt(id);
				if(anprompt.length > 0) {
					return true;
				}
				return false;
			}
		};
	})
	.factory('validateService', function(formService) {
		return {
			parseOpts : function(optStr) {
				var opts = [];
				angular.forEach(optStr.split(','), function(opt, idx) {
					var obj = {};
					if(opt.indexOf("maxlength[") == 0 || opt.indexOf("minlength[") == 0) {
						obj['name'] = opt.substring(0, 9);
						obj['value'] = opt.substring(opt.indexOf('[')+1, opt.indexOf(']'));
					} else if(opt.indexOf("equals[") == 0) {
						obj['name'] = opt.substring(0, 6);
						obj['value'] = opt.substring(opt.indexOf('[')+1, opt.indexOf(']'));
					} else {
						obj['name'] = opt;
					}
					opts.push(obj);
				});
				return opts;
			},
			validateVal : function(opt, val) {
				var msg;
				switch (opt.name) {
				case 'required':
					if(val == "") {
						msg = validateConfig[opt.name].alertText;
					}
					break;
				case 'maxlength':
					if(val.length > opt.value) {
						msg = validateConfig[opt.name].alertText;
					}
					break;
				case 'minlength':
					if(val.length < opt.value) {
						msg = validateConfig[opt.name].alertText;
					}
					break;
				case 'equals':
					if(val != formService.getInput(opt.value).val()) {
						msg = validateConfig[opt.name].alertText;
					}
					break;
				default:
					if(!validateConfig[opt.name].regex.test(val)) {
						msg = validateConfig[opt.name].alertText;
					}
					break;
				}
				return msg;
			},
			validate : function(opts, val) {
				var that = this;
				var errorMsg;
				for(var i=0; i < opts.length; i++) {
					errorMsg = that.validateVal(opts[i], val);
					if(errorMsg != undefined) {
						break;
					}
				}
				// 返回错误码
				return errorMsg;
			},
			// 调用该方法时，所有添加了anvalidate的表单都进行验证，全部验证通过返回true，否则为false
			validateForm : function() {
				formService.getScope().$broadcast('anValidate.validate');
				if(formService.getPrompts().length > 0) {
					return false;
				}
				return true;
			}
		};
	});