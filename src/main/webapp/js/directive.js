/**
 * Created by shawn on 14-6-15.
 */
var SalesDirectives = angular.module('SALES.directives', []);
/**
 * 验证用户名是否唯一
 */
SalesDirectives.directive('ensureUnique', ['$http' , 'SERVER_URL', function ($http, SERVER_URL) {
    return { require: 'ngModel', link: function (scope, ele, attrs, c) {
        scope.$watch(attrs.ngModel, function () {
            $http({ method: 'GET', url: SERVER_URL + '/user/checkLoginName?loginName=' + attrs.ensureUnique })
                .success(function (data, status, headers, cfg) {
                    if (data.code === '200') {
                        c.$setValidity('unique', data.data);
                    }
                }).error(function (data, status, headers, cfg) {
                    c.$setValidity('unique', false);
                });
        });
    } }
}]);