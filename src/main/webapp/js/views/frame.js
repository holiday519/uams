'use strict';
$(document).bind("selectstart", function(){return false;});
$(document).ready(function() {
    $('body').layout({
        north__paneSelector : ".outer-north",
        center__paneSelector : ".outer-center",
        north__size : 100,
        spacing_open : 8,
        spacing_closed : 12,
        north__spacing_open : 0
    });
    $('.outer-center').layout({
        west__paneSelector : ".inner-west",
        center__paneSelector : ".inner-center",
        west__size : 223,
        spacing_open : 8,
        spacing_closed : 12,
        west__spacing_open : 0
    });
});

basicApp.controller('frame.ctrl', function($scope, httpService, $window, anpopupService) {
	httpService({
		data : {
	        method : 'POST',
	        url : basePath + "/listUsers.do",
	        data : {user : {userId : loginUserId}, page : {}}
	    },
	    success : function(data) {
	    	if(data.errorCode == 0) {
	    		$scope.loginUser = data.users[0];
	    	}
	    }
	});
    $scope.menudata = (loginUserId == 1 ? adminData : userData);
    $scope.toggleContent = function(dom) {
        $(dom).nextAll().slideToggle("fast");
    };
    $scope.logout = function() {
    	anpopupService.openAlert('prompt', "确定要退出uams系统？", {
    		confirm : function() {
    			httpService({
    				data : {
                        method : 'POST',
                        url : basePath + "/logoutUser.do"
                    },
                    success : function(data) {
        				if(data.errorCode == 0) {
        					$window.location = basePath + "/login.jsp";
        				}else {
        					anpopupService.openAlert('warning', lang["error."+data.errorCode]);
        				}
                    }
    			});
    		}
    	});
    };
})
.directive('frame.menu', function() { //绘制menu
    return {
        template : '<div ng-repeat="data in menudata" class="nav_box">' //$event.target获取绑定事件的元素
        +               '<h1 ng-click=toggleContent($event.target)>{{data.title}}</h1>'
        +               '<h2 ng-repeat="cont in data.content">'
        +               	'<a href="{{cont.url}}">'
        +                   	'<span>{{cont.title}}</span>'
        +                   '</a>'
        +               '</h2>'
        +           '</div>',
        replace : false,
        restrict : 'A',
        scope : true
    };
}).config(function($routeProvider) { //配置menu的跳转
    $routeProvider
    .when('/user', {
        templateUrl: 'views/viewUser.jsp'
    })
    .when('/license', {
        templateUrl: 'views/viewLicense.jsp'
    })
    .when('/product', {
        templateUrl: 'views/viewProduct.jsp'
    })
    .when('/info', {
        templateUrl: 'views/viewInfo.jsp'
    })
    .when('/cluster', {
        templateUrl: 'views/viewClusterUKey.jsp'
    })
    .when('/vdi', {
        templateUrl: 'views/viewVDIUKey.jsp'
    })
    .otherwise({
      	redirectTo: '/license'
    });
});