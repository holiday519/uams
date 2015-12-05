basicApp.factory('httpService', function($http, anpopupService, $window) { //封装$http请求，统一抓取异常.
		return function(request) {
			$http(request.data)
				.success(function(data, status) {
					// 需要统一处理的异常
					if(data.errorCode == 108) {
						anpopupService.openAlert('warning', lang["error."+data.errorCode]);
						$window.location = basePath + "/login.jsp";
					}
					if(request.success) {
						request.success(data, status);
					}
				})
				.error(function(data, status) {
					// 需要统一处理的异常
					anpopupService.openAlert('warning', lang["error.ajax"]);
					if(request.error) {
						request.error(data, status);
					}
				});
		};
	})
	.factory('utilService', function() {
		return {
			propertiesFilter : function(entity, properties) { // 该方法只支持对象和对象数组的属性过滤
				if(typeof entity === "object") {
					if(this.isArray(entity)) { //array
						angular.forEach(entity, function(obj, idx1) {
							angular.forEach(properties, function(prop, idx2) {
								delete obj[prop];
							});
						});
					}else { //object
						angular.forEach(properties, function(prop, idx) {
							delete entity[prop];
						});
					}
				}
				return entity;
			},
			isArray : function(entity) {
				return entity && typeof entity === "object" &&
						typeof entity.length === "number" &&
						typeof entity.splice === "function" &&
						!(entity.propertyIsEnumerable("length"));
			}
		};
	});