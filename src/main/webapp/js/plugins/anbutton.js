var opvButtonGroup = angular.module('opvButtonGroup', []);
    opvButtonGroup.directive('opvButtonGroup', function() {
        return {
            template : '<div class="table_operate">'
            +               '<div class="content_button">'
            +                   '<ul>'
            +                       '<opv-buttons ng-repeat="button in data"></opv-buttons>'
            +                   '</ul>'
            +               '</div>'
            +          '</div>',
            replace : true,
            restrict : 'E',
            scope : false,
            transclude : true,
            controller : function($scope, $element, $attrs)
            {
            	$scope.data = $scope.$eval($attrs.opvmodel).buttonGroup;
            }
        };
    })
    .directive('opvButtons', function() {
        return {
            template : '<li>'
            +              '<a id={{button.id}} ng-click="button.click()" href="javascript:void(0)">'
            +              '<div class="left"></div>'
            +              '<div class="center"><div class={{button.cls}}></div><div class="word">{{button.val}}</div></div>'
            +              '<div class="right"></div>'
            +              '</a>'
            +          '</li>',
            replace : true,
            restrict : 'E',
            scope : false,
            transclude : true
        };
    });
    
    