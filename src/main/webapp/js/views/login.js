'use strict';
angular.module('loginApp', [])
	.controller('loginCtrl', function($scope, $http, $window) {
		$scope.usrAlert = {
			errorMsg : "",
			isShow : false
		};
		$scope.pwdAlert = {
			errorMsg : "",
			isShow : false	
		};
		$scope.login = function() {
			if($scope.username == null || $scope.username == undefined || $scope.username == "") {
				$scope.usrAlert.errorMsg = "请输入用户名";
				$scope.usrAlert.isShow = true;
				return;
			}else {
				$scope.usrAlert.isShow = false;
			}
			if($scope.password == null || $scope.password == undefined || $scope.password == "") {
				$scope.pwdAlert.errorMsg = "请输入密码";
				$scope.pwdAlert.isShow = true;
				return;
			}else {
				$scope.pwdAlert.isShow = false;
			}
			$http({
				method : 'POST',
                url : basePath + "/loginUser.do",
                data : {userName : $scope.username, password : $scope.password}
			}).success(function(data) {
				if(data.errorCode == 0) {
					$window.location = basePath + "/frame.jsp?loginUserId=" + data.user.userId;
				}else if(data.errorCode == 107) {
					$scope.usrAlert.errorMsg = lang["error."+data.errorCode];
					$scope.usrAlert.isShow = true;
				}else if(data.errorCode == 108) {
					$scope.pwdAlert.errorMsg = lang["error."+data.errorCode];
					$scope.pwdAlert.isShow = true;
				}
			});
		};
		$scope.enter = function(event) {
			if(event.keyCode == 13) {
				$scope.login();
			}
		};
	});