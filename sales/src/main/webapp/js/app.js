'use strict';
// Declare app level module which depends on filters, and services
var SALES = angular.module('SALES', [
    'ngRoute',
    'ngResource',
    'ngCookies',
    'SALES.controllers',
    'SALES.services',
    'SALES.filters',
    'SALES.directives',
    'angular-md5'
]);
SALES.constant('SERVER_URL', 'http://localhost:8080/sales');

SALES.value('http_defaults', {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8' }
//    headers: { 'Content-Type': 'application/json;charset=utf-8' },
//    timeout: 3000
});
SALES.config(['$sceProvider', function ($sceProvider) {
    $sceProvider.enabled(false);
}]);
SALES.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {templateUrl: 'login.html', controller: 'LoginController'});
    $routeProvider.when('/logout', {templateUrl: 'logout.html', controller: 'LogoutController'});

    $routeProvider.when('/sale/create', {templateUrl: 'views/sales/create.html', controller: 'SalesCreateController'});
    $routeProvider.when('/sale/list', {templateUrl: 'views/sales/list.html', controller: 'SalesListController'});

    $routeProvider.when('/sale/visit/list/:id', {templateUrl: 'views/sales/visit/list.html', controller: 'SalesVisitListController'});
    $routeProvider.when('/sale/repair/list/:id', {templateUrl: 'views/sales/repair/list.html', controller: 'SalesRepairListController'});

    $routeProvider.when('/sale/visit/create/:id', {templateUrl: 'views/sales/visit/create.html', controller: 'SalesVisitCreateController'});
    $routeProvider.when('/sale/repair/create/:id', {templateUrl: 'views/sales/repair/create.html', controller: 'SalesRepairCreateController'});

    $routeProvider.when('/sale/update/:id', {templateUrl: 'views/sales/update.html', controller: 'SalesUpdateController'});

    $routeProvider.when('/user/create', {templateUrl: 'views/user/create.html', controller: 'UserCreateController'});
    $routeProvider.when('/user/update/:id', {templateUrl: 'views/user/update.html', controller: 'UserUpdateController'});
    $routeProvider.when('/user/list', {templateUrl: 'views/user/list.html', controller: 'UserListController'});

    $routeProvider.when('/customer/create', {templateUrl: 'views/customer/create.html', controller: 'CustomerCreateController'});
    $routeProvider.when('/customer/update/:id', {templateUrl: 'views/customer/update.html', controller: 'CustomerUpdateController'});
    $routeProvider.when('/customer/list', {templateUrl: 'views/customer/list.html', controller: 'CustomerListController'});

    $routeProvider.when('/detail', {templateUrl: 'views/user/detail.html', controller: 'UserDetailController'});
    $routeProvider.when('/resetpwd', {templateUrl: 'views/user/resetpwd.html', controller: 'UserResetPwdController'});

    $routeProvider.otherwise({redirectTo: '/login'});
}]);

SALES.run(['$rootScope', '$log', '$location', 'AuthService', function ($rootScope, $log, $location, AuthService) {

    $rootScope.CONSTANTS = {
        AUTO_LOGIN_INFO: 'auto_login_info',
        AUTO_LOGIN_FLAG: 'auto_login_flag',
        LOCAL_MEMORY_USERNAME: 'local_memory_username',
        LOCAL_MEMORY_PASSWORD: 'local_memory_password'
    };
    $rootScope.$log = $log;
    $rootScope.$location = $location;
    $rootScope.AuthService = AuthService;

    var firstPath = $location.path();
    var autoLoginFlag = window.localStorage.getItem($rootScope.CONSTANTS.AUTO_LOGIN_FLAG);

    $rootScope.forwardErrorPage = function (message) {
        $location.path("/error/" + message);
    }
    $rootScope.$on('$routeChangeStart', function () {
        $log.debug("current path: " + $location.path() + " AuthService.fromPage:" + AuthService.getFromPage());
        AuthService.setMsg(null);
        if ($location.path() in {'/logout': '', '/login': ''}) {
            $log.debug('Free-0');
            return;
        }

        if (!AuthService.isLoggedIn()) {
            $log.debug('DENY');
            event.preventDefault();
            if ($location.path() === '/logout') {
                AuthService.setFromPage('/login');
            } else {
                $location.path('/login');
            }

        }
        else {
            $log.debug('ALLOW');
        }
    });
}]);

var SalesControllers = angular.module('SALES.controllers', []);
