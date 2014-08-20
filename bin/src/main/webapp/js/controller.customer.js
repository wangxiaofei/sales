'use strict';

SalesControllers.controller('CustomerListController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5) {

    var user = AuthService.getUser();
    if (user == null) {
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
        var url = '/customer/list';
        var newParams = {};
        newParams.page = page + '';
        newParams.count = count + '';
        newParams.userId =
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
        successCallback: function (data) {
            if (data.code === '200') {
                alert('删除成功！');
                $scope.page._load();
            } else {
                alert('删除失败！');
            }
        }
    };
    var deleteCustomer = function (id) {
        var url = '/customer/delete';
        var params = {};
        params.id = id;
        $log.debug(angular.toJson(params));
        HttpUtilService.sendGet(url, params, rs_del);
    };

    $scope.confirmDel = function (customer) {
        var msg = '确定要删除' + customer.userName + '吗？';
        ;
        var isDel = window.confirm(msg);
        if (isDel) {
            deleteCustomer(customer.id);
        }
    };


}]);
SalesControllers.controller('CustomerCreateController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', '$location', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5, $location) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }

    $scope.customer = {};
    $scope.customer.sex = 1;
    var createRs = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            if (data.code === '200') {
                $location.path("/customer/list");
            }
        }
    }
    $scope.createCustomer = function () {
        var url = '/customer/create';
        var newParams = {};
        newParams.userName = $scope.customer.userName;
        newParams.age = $scope.customer.age;
        newParams.sex = $scope.customer.sex;
        newParams.phone = $scope.customer.phone;
        newParams.address = $scope.customer.address;
        newParams.userId = user.id;
        HttpUtilService.sendPost(url, newParams, createRs);
    }
}]);
SalesControllers.controller('CustomerUpdateController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', '$routeParams', '$location', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5, $routeParams, $location) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }

    $scope.customer = {};
    var getRs = {
        successCallback: function (data) {
            var customer = data.data;
            $scope.customer.id = customer.id;
            $scope.customer.age = customer.age;
            $scope.customer.userName = customer.userName;
            $scope.customer.sex = customer.sex;
            $scope.customer.phone = customer.phone;
            $scope.customer.address = customer.address;
        }
    }
    var url = "/customer/get";
    var params = {};
    params.id = $routeParams.id;
    HttpUtilService.sendGet(url, params, getRs);

    var updateRs = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            if (data.code === '200') {
                $location.path("/customer/list");
            }
        }
    }
    $scope.updateCustomer = function () {
        var url = '/customer/update';
        var newParams = {};
        newParams.id = $scope.customer.id;
        newParams.userName = $scope.customer.userName;
        newParams.age = $scope.customer.age;
        newParams.sex = $scope.customer.sex;
        newParams.phone = $scope.customer.phone;
        newParams.address = $scope.customer.address;
        HttpUtilService.sendPost(url, newParams, updateRs);
    }
}]);


