'use strict';

SalesControllers.controller('LoginController', ['$rootScope', '$scope', '$log', '$location', '$routeParams', 'AuthService', 'md5','HttpUtilService', function ($rootScope, $scope, $log, $location, $routeParams, AuthService, md5,HttpUtilService) {

    //$scope.username = window.localStorage.getItem($scope.CONSTANTS.LOCAL_MEMORY_USERNAME);

    $scope.notLogin = true;
    if (AuthService.isLoggedIn()) {
        $scope.notLogin = false;
        return;
    }
    else {
        $scope.$emit("event:selectUser", true);
    }

    $scope.autoLogin = false;
    $scope.messageInfo = {};
    $scope.result = {
        data: {},
        status: '',
        successCallback: function (data) {
            if (data != null && data.code === '200') {
                $log.debug(data);
                AuthService.setUser(data.data);
                $scope.messageInfo.isLogin = true;
                $scope.messageInfo.user = data.data;

                $scope.$emit("LoginStateChange", $scope.messageInfo);
                $log.debug("login success and go to " + AuthService.getFromPage());
                //$location.path((AuthService.getFromPage() !== '/login' && AuthService.getFromPage() !== '/logout' && AuthService.getFromPage() !== '/register' && AuthService.getFromPage() !== '/' && AuthService.getFromPage() !== '') ? AuthService.getFromPage() : '/sale/list');
                $location.path('/sale/list');
                $rootScope.isSaleActive = true;
                $rootScope.isCusActive = false;
                $rootScope.isUserActive = false;
                $rootScope.isLogining = true;
                $rootScope.sessionUser = data.data;
            } else {
                AuthService.setMsg(data.code + data.info);
                $scope.messageInfo.isLogin = false;
                $scope.messageInfo.msg = data.info;
            }
        },
        errorCallback: function (status) {
            $rootScope.isLogining = false;
        }
    };
    $scope.login = function () {
        $log.debug("I am  login ~" + $rootScope.isLogining);
        if ($scope.loginName == null || angular.isUndefined($scope.loginName) || $scope.loginName.trim().length == 0) {
            return;
        }
        if ($scope.password == null || angular.isUndefined($scope.password) || $scope.password.trim().length == 0) {
            return;
        }

        var md5pass = md5.createHash(utf16to8($scope.password.trim()) || '');
        var url = '/user/login';
        var newParams = {"loginName":$scope.loginName, "password": md5pass};
        HttpUtilService.sendPost(url, newParams, $scope.result);
    };
}]);
SalesControllers.controller('LogoutController', ['$rootScope','$location', '$scope', '$http', '$log', 'AuthService','HttpUtilService', function ($rootScope,$location, $scope, $http, $log, AuthService,HttpUtilService) {
    var isLoggedIn = AuthService.isLoggedIn();
    var user = AuthService.getUser();
    $scope.result = {
        data: '',
        status: '',
        successCallback: function (data) {
            if (data != null && data.code === '200') {
                $scope.msg = "logout success!";
                //MessageWebSocketService.close();
            }
            AuthService.setUser(null);
            $location.path('/login');
            window.localStorage.removeItem($scope.CONSTANTS.AUTO_LOGIN_FLAG);
            window.localStorage.removeItem($scope.CONSTANTS.AUTO_LOGIN_INFO);
            $rootScope.isLogining = false;
            $rootScope.sessionUser = null;
        }
    };
    if (isLoggedIn){
        var url = '/user/logout';
        HttpUtilService.sendGet(url, null, $scope.result);
    } else{
        $scope.msg = "You are not login.";
    }
}]);


SalesControllers.controller('UserListController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5) {
    var user = AuthService.getUser();
    if(user == null){
        return;
    }
    if(user.role == 1){
        return;
    }

    $scope.hasData = 2;
    //下一页
    $scope.goToNext = function () {
        $scope.paginator = {};
        $scope.showLoading = true;
        $scope.page.next();
    };
    //上一页
    $scope.goToPrev = function () {
        $scope.paginator = {};
        $scope.showLoading = true;
        $scope.page.previous();
    };
    //指定页
    $scope.goToAppoint = function (index) {
        $scope.paginator = {};
        $scope.showLoading = true;
        $scope.page.go(index);
    };

    var getList = function (result, params, page, count, callback) {
        var url = '/user/list';
        var newParams = {};
        newParams.page = page + '';
        newParams.count = count + '';
        HttpUtilService.sendGet(url, newParams, result, callback);
    };
    $scope.paginator = {};
    $scope.result = {
        successCallback: function (data) {
            if (data.code == '201') {
                $scope.hasData = 2;
            }
            if (data.code === '200') {
                $scope.hasData = 1;
                $scope.paginator.currentPageItems = data.data.list;
            }
            $log.debug($scope.page.pageCount);

        }
    };
    $scope.page = PaginatorService(getList, {'page': '1', 'count': '10'}, $scope.result);
    $scope.page._load();

    var rs_del = {
        successCallback : function(data){
            if(data.code === '200'){
                alert('删除成功！');
                $scope.page._load();
            }else{
                alert('删除失败！');
            }
        }
    };
    var deleteUser = function(id){
        var url = '/user/delete';
        var params = {};
        params.id = id;
        $log.debug(angular.toJson(params));
        HttpUtilService.sendGet(url,params,rs_del);
    };

    $scope.confirmDel = function(user){
        var msg = '确定要删除' + user.userName + '吗？';;
        var isDel = window.confirm(msg);
        if(isDel){
            deleteUser(user.id);
        }
    };



}]);
SalesControllers.controller('UserCreateController', ['$scope', '$log', 'HttpUtilService', 'AuthService', 'md5', '$location', '$routeParams', function ($scope, $log, HttpUtilService, AuthService, md5, $location, $routeParams) {
    $scope.user ={};
    $scope.user.loginName = '';
    $scope.user.password= '';
    $scope.user.role = 1;
    var result = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            $location.path("/user/list");
        }
    }
    $scope.createUser = function () {

        var url = '/user/create';
        var md5pass = md5.createHash(utf16to8($scope.user.password.trim()) || '');
        var newParams = {};
        //newParams.user = {};
        newParams.loginName = $scope.user.loginName;
        newParams.userName = $scope.user.userName;
        newParams.password = md5pass;
        newParams.email = $scope.user.email;
        newParams.role = $scope.user.role;
        HttpUtilService.sendPost(url, newParams, result);
    }
}]);
SalesControllers.controller('UserUpdateController', ['$scope', '$log', 'HttpUtilService', 'AuthService', 'md5', '$location', '$routeParams', function ($scope, $log, HttpUtilService, AuthService, md5, $location, $routeParams) {
    var user = AuthService.getUser();
    if(user == null){
        return;
    }
    $scope.user = {};
    var getRs = {
        successCallback:function(data){
            var user = data.data;
            $scope.user.id = user.id;
            $scope.user.loginName = user.loginName;
            $scope.user.userName = user.userName;
            $scope.user.email = user.email;
            $scope.user.role = user.role;
        }
    }
    var url = "/user/get";
    var params = {};
    params.id = $routeParams.id;
    HttpUtilService.sendGet(url, params, getRs);

    var result = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            $location.path("/user/list");
        }
    }
    $scope.updateUser = function () {
        var url = '/user/update';
        var newParams = {};
        newParams.id = $scope.user.id;
        newParams.userName = $scope.user.userName;
        newParams.email = $scope.user.email;
        newParams.role = $scope.user.role;
        HttpUtilService.sendPost(url, newParams, result);
    }
}]);
SalesControllers.controller('UserDetailController', ['$scope', '$log', 'HttpUtilService', 'AuthService', 'md5', '$location', '$routeParams', function ($scope, $log, HttpUtilService, AuthService, md5, $location, $routeParams) {
    var user = AuthService.getUser();
    if(user == null){
        return;
    }
    $scope.user = {};
    var getRs = {
        successCallback:function(data){
            var user = data.data;
            $scope.user.id = user.id;
            $scope.user.loginName = user.loginName;
            $scope.user.userName = user.userName;
            $scope.user.email = user.email;
        }
    }
    var url = "/user/get";
    var params = {};
    params.id = user.id;
    HttpUtilService.sendGet(url, params, getRs);
    $scope.modifyRet ={};
    var result = {
        successCallback: function (data) {
            $log.debug('data:'+angular.toJson(data));
            if(data.code === '200'){
                $scope.modifyRet.success = true;
            }else{
                $scope.modifyRet.success = false;
            }
        }
    };
    $scope.updateUser = function () {
        var url = '/user/update';
        var newParams = {};
        newParams.id = $scope.user.id;
        newParams.userName = $scope.user.userName;
        newParams.email = $scope.user.email;
        HttpUtilService.sendPost(url, newParams, result);
    }
}]);
SalesControllers.controller('UserResetPwdController', ['$scope', '$log', 'HttpUtilService', 'AuthService', 'md5', '$location', '$routeParams', function ($scope, $log, HttpUtilService, AuthService, md5, $location, $routeParams) {
    var user = AuthService.getUser();
    if(user == null){
        return;
    }
    $scope.user = {};
    $scope.user.userName = user.userName;
    $scope.modifyRet = {};
    var result = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            if(data.code === '200'){
                $scope.modifyRet.success = true;
            }else if(data.code === '403'){
                $scope.modifyRet.info = data.info;
            }else{
                $scope.modifyRet.success = false;
            }
        }
    }
    $scope.updateUser = function () {
        var url = '/user/resetpwd';
        var newParams = {};
        newParams.id = user.id;
        var md5pass = md5.createHash(utf16to8($scope.user.password.trim()) || '');
        newParams.password = md5pass;
        var md5pass_new = md5.createHash(utf16to8($scope.user.newPassword.trim()) || '');
        newParams.newPassword = md5pass_new;
        HttpUtilService.sendPost(url, newParams, result);
    }
}]);


