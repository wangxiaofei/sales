'use strict';

SalesControllers.controller('SalesListController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', function ($scope, $log, PaginatorService, HttpUtilService, AuthService) {
    var user = AuthService.getUser();
    $log.debug("user =====" + angular.toJson(user));
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
        var url = '/sale/list';
        var newParams = {};
        newParams.page = page + '';
        newParams.count = count + '';
        HttpUtilService.sendGet(url, newParams, result, callback);
    };
    $scope.paginator = {};
    $scope.result = {
        successCallback: function (data) {
            if (data.code == '201') {
                $scope.paginator = {};
                $scope.hasData = 2;
            }
            if (data.code === '200') {

                $scope.hasData = 1;
                $scope.paginator.currentPageItems = data.data.list;
            }

        }
    };
    $scope.page = PaginatorService(getList, {'page': '1', 'count': '10'}, $scope.result);
    $scope.page._load();


    var searchList = function (result, params, page, count, callback) {
        var url = '/sale/search';
        var newParams = {};
        newParams.page = page + '';
        newParams.count = count + '';
        newParams.keywords = $scope.keywords;
        newParams.dateWords = $scope.dateWords;
        HttpUtilService.sendGet(url, newParams, result, callback);
    };

    $scope.searchSales = function () {
        $scope.page = PaginatorService(searchList, {'page': '1', 'count': '10'}, $scope.result);

        $scope.page._load();


    };

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
    var deleteSale = function (id) {
        var url = '/sale/delete';
        var params = {};
        params.id = id;
        $log.debug(angular.toJson(params));
        HttpUtilService.sendGet(url, params, rs_del);
    };

    $scope.confirmDel = function (sale) {
        var msg = '确定要删除' + sale.id + '吗？';
        var isDel = window.confirm(msg);
        if (isDel) {
            deleteSale(sale.id);
        }
    };

    var rs_pro = {
        successCallback: function (data) {
            if (data.code === '200') {
                $scope.page._load();
            }
        }
    };
    $scope.processSale = function (sale) {
        var url = '/sale/process';
        var params = {};
        params.id = sale.id;
        HttpUtilService.sendGet(url, params, rs_pro);
    };

}]);

SalesControllers.controller('SalesCreateController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', '$location', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5, $location) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }

    $scope.customerList = {};
    var result_customerList = {
        successCallback: function (data) {
            if (data.code == '201') {
                $scope.hasData = 2;
            }
            if (data.code === '200') {
                $scope.hasData = 1;
                $scope.customerList = data.data;
            }

        }
    };
    var getCustomerList = function () {
        var url = '/customer/list/all';
        var newParams = {};
        newParams.userId = user.id;
        HttpUtilService.sendGet(url, newParams, result_customerList);
    };
    //getCustomerList();

    $scope.sale = {};
    $scope.sale.sex = 1;
    var createRs = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            if (data.code === '200') {
                $location.path('/sale/list');
            }
        }
    }
    $scope.createSale = function () {
        var url = '/sale/create';
        var newParams = {};
        newParams.customerName = $scope.sale.customerName;
        newParams.customerPhone = $scope.sale.customerPhone;
        newParams.customerAddress = $scope.sale.customerAddress;
        newParams.machineModel = $scope.sale.machineModel;
        newParams.machineNumber = $scope.sale.machineNumber;
        newParams.saleTime = $scope.sale.saleTime;
        newParams.userId = user.id;
        HttpUtilService.sendPost(url, newParams, createRs);
    }
}]);
SalesControllers.controller('SalesUpdateController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', '$routeParams', '$location', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5, $routeParams, $location) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }

    $scope.sale = {};
    var getRs = {
        successCallback: function (data) {
            var sale = data.data;
            $scope.sale.id = sale.id;
            $scope.sale.customerName = sale.customerName;
            $scope.sale.customerPhone = sale.customerPhone;
            $scope.sale.customerAddress = sale.customerAddress;
            $scope.sale.machineModel = sale.machineModel;
            $scope.sale.machineNumber = sale.machineNumber;
            var date = new Date(sale.saleTime);
            $scope.sale.saleTime = date.Format("yyyy-MM-dd");
        }
    }
    var url = "/sale/get";
    var params = {};
    params.id = $routeParams.id;
    HttpUtilService.sendGet(url, params, getRs);

    var updateRs = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            if (data.code === '200') {
                $location.path('/sale/list');
            }
        }
    };

    $scope.customerList = {};
    var result_customerList = {
        successCallback: function (data) {
            if (data.code == '201') {
                $scope.hasData = 2;
            }
            if (data.code === '200') {
                $scope.hasData = 1;
                $scope.customerList = data.data;
            }

        }
    };
    var getCustomerList = function () {
        var url = '/customer/list/all';
        var newParams = {};
        newParams.userId = user.id;
        HttpUtilService.sendGet(url, newParams, result_customerList);
    };
    //getCustomerList();


    $scope.updateSale = function () {
        var url = '/sale/update';
        var newParams = {};
        newParams.id = $scope.sale.id;
        newParams.customerName = $scope.sale.customerName;
        newParams.customerPhone = $scope.sale.customerPhone;
        newParams.customerAddress = $scope.sale.customerAddress;
        newParams.machineModel = $scope.sale.machineModel;
        newParams.machineNumber = $scope.sale.machineNumber;
        newParams.saleTime = $scope.sale.saleTime;
        HttpUtilService.sendPost(url, newParams, updateRs);
    }
}]);
SalesControllers.controller('SalesVisitListController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', '$routeParams', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, $routeParams) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }
    $scope.saleId = $routeParams.id;
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
        var url = '/sale/visit/list';
        var newParams = {};
        newParams.saleId = $scope.saleId;
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
    var deleteVisit = function (id) {
        var url = '/sale/visit/delete';
        var params = {};
        params.id = id;
        $log.debug(angular.toJson(params));
        HttpUtilService.sendGet(url, params, rs_del);
    };

    $scope.confirmDel = function (visit) {
        var msg = '确定要删除' + visit.id + '吗？';
        ;
        var isDel = window.confirm(msg);
        if (isDel) {
            deleteVisit(visit.id);
        }
    };

}]);

SalesControllers.controller('SalesRepairListController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', '$routeParams', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, $routeParams) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }
    $scope.saleId = $routeParams.id;
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
        var url = '/sale/repair/list';
        var newParams = {};
        newParams.saleId = $scope.saleId;
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
    var deleteRepair = function (id) {
        var url = '/sale/repair/delete';
        var params = {};
        params.id = id;
        $log.debug(angular.toJson(params));
        HttpUtilService.sendGet(url, params, rs_del);
    };

    $scope.confirmDel = function (repair) {
        var msg = '确定要删除' + repair.id + '吗？';
        ;
        var isDel = window.confirm(msg);
        if (isDel) {
            deleteRepair(repair.id);
        }
    };

}]);

SalesControllers.controller('SalesVisitCreateController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', '$location', '$routeParams', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5, $location, $routeParams) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }
    $scope.saleId = $routeParams.id;
    $scope.visit = {};
    var createRs = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            if (data.code === '200') {
                $location.path('/sale/visit/list/' + $scope.saleId);
            }
        }
    };
    $scope.createVisit = function () {
        var url = '/sale/visit/create';
        var newParams = {};
        newParams.content = $scope.visit.content;
        newParams.visitTime = $scope.visit.visitTime;
        newParams.saleId = $scope.saleId;
        HttpUtilService.sendPost(url, newParams, createRs);
    }
}]);

SalesControllers.controller('SalesRepairCreateController', ['$scope', '$log', 'PaginatorService', 'HttpUtilService', 'AuthService', 'md5', '$location', '$routeParams', function ($scope, $log, PaginatorService, HttpUtilService, AuthService, md5, $location, $routeParams) {
    var user = AuthService.getUser();
    if (user == null) {
        return;
    }
    $scope.saleId = $routeParams.id;
    $scope.repair = {};
    var createRs = {
        successCallback: function (data) {
            $log.debug(angular.toJson(data));
            if (data.code === '200') {
                $location.path('/sale/repair/list/' + $scope.saleId);
            }
        }
    }
    $scope.createRepair = function () {
        var url = '/sale/repair/create';
        var newParams = {};
        newParams.content = $scope.repair.content;
        newParams.repairTime = $scope.repair.repairTime;
        newParams.saleId = $scope.saleId;
        HttpUtilService.sendPost(url, newParams, createRs);
    }
}]);

