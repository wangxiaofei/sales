'use strict';

/* Services */

var SalesServices = angular.module('SALES.services', []);

SalesServices.factory('ErrorService', ['$location', '$rootScope', function ($location, $rootScope) {

    return {
        errorMessage: null,
        setMessage: function (errMsg) {
            this.errorMessage = errMsg;
        },
        clear: function () {
            this.errorMessage = null;
        }
    };
}]);

SalesServices.factory('AuthService', ['$location', '$rootScope', '$log', function ($location, $rootScope, $log) {

    var user;
    var fromPage = $location.path();
    var msg;
    return {
        setUser: function (aUser) {
            user = aUser;
            $rootScope.$broadcast('event:authChanged');
            $rootScope.$broadcast('LoginStateChange', user);
            /*
             if(user == null) {
             angular.fromJson(window.localStorage.getItem('user'));
             window.localStorage.removeItem('user');
             }
             else {
             $log.debug('set user , user = '+user);
             window.localStorage.setItem('user', angular.toJson(user));
             }*/
        },
        isLoggedIn: function () {
            return (user) ? true : false;
        },
        getUser: function () {
            return user;
        },
        setFromPage: function (sourcePath) {
            fromPage = sourcePath;
        },
        getFromPage: function () {
            return fromPage;
        },
        getMsg: function (message) {
            return msg;
        },
        setMsg: function (message) {
            msg = message;
            $rootScope.$broadcast('event:msgChanged');
        }
    }
}]);

SalesServices.factory('PaginatorService', ['$rootScope', 'SafeApply', '$log', function ($rootScope, SafeApply, $log) {
    return function (fetchFunction, params, result) {
        var paginator = {
            total: 0,
            count: parseInt(params.count),
            pageCount: 0,
            page: parseInt(params.page),

            hasNext: function () {
                console.log("hasNext:" + (this.page + 1));
                return this.page + 1 <= this.pageCount;
            },
            hasPrevious: function () {
                console.log("hasPrevious:" + (this.page - 1));
                return this.page - 1 >= 1;
            },
            next: function () {
                if (this.hasNext()) {
                    ++this.page;
                    this._load();
                }
            },
            previous: function () {
                if (this.hasPrevious()) {
                    --this.page;
                    this._load();
                }
            },
            hasTargetPage: function (targetPage) {
                return (targetPage > 0 && targetPage <= this.pageCount);
            },
            go: function (targetPage) {
                if (targetPage > 0 && targetPage <= this.pageCount) {
                    //console.log("goto:"+targetPage);
                    var pageIndex = parseInt(targetPage);
                    //console.log("goto1:"+pageIndex);
                    this.page = pageIndex;
                    this._load();
                }
            },
            _load: function () {
                var self = this;
                fetchFunction(result, params, this.page + '', this.count + '', function (data) {
                    //$log.debug("page data : "+data);
                    if (data.data != null) {
                        self.total = data.data.totalResults == null || data.data.totalResults == "" ? 0 : parseInt(data.data.totalResults);
                        self.count = data.data.pageSize == null || data.data.pageSize == "" ? self.count : parseInt(data.data.pageSize);
                        self.page = data.data.pageIndex == null || data.data.pageIndex == "" ? 1 : parseInt(data.data.pageIndex);
                        self.pageCount = (self.total === 0 || self.count === 0) ? 0 : Math.ceil(self.total / self.count);
                    }
                });
            }
        };
        //SafeApply();
        return paginator;
    };
}]);


/*
 safeApply
 */

SalesServices.factory('SafeApply', function ($rootScope) {
    return function (scope, fn) {
        var phase = scope.$root.$$phase;
        if (phase == '$apply' || phase == '$digest') {
            if (fn && ( typeof (fn) === 'function')) {
                fn();
            }
        } else {
            scope.$apply(fn);
        }
    }
});
/*
 http request service
 */
SalesServices.factory('HttpUtilService', ['$http', '$log', 'http_defaults', 'SERVER_URL', '$filter', function ($http, $log, http_defaults, SERVER_URL, $filter) {
    return {
        sendGet: function (url, params, result, pageCallback) {
            var curtime = new Date().getTime();
            $log.debug("current time : " + curtime);
            if (angular.isDefined(result) && angular.isDefined(result.waiting) && result.waiting) {
                $log.debug("repeat request");
                if (angular.isDefined(result.starttime)) {
                    if ((curtime - result.starttime) < 1500) {
                        return;
                    }
                }
            }
            result.waiting = true;
            result.starttime = curtime;
            var sendData = params;
            var config = http_defaults;
            config.params = sendData;
            var serverUrl = SERVER_URL + url;
            $log.debug('url: ' + serverUrl + "?methos=GET,params=" + angular.toJson(sendData));
            $http.get(serverUrl, config).success(function (data, status, headers, config) {
                $log.debug('result: ' + angular.toJson(data, false));
                result.status = status;
                result.data = data;
                if (angular.isDefined(pageCallback)) {
                    pageCallback(data);
                }
                if (angular.isDefined(result.successCallback)) {
                    result.successCallback(data);
                }
                result.waiting = false;
            }).error(function (data, status, headers, config) {
                $log.debug('error: ' + status + data.code + data.info);
                result.status = status;
                if (angular.isDefined(result.errorCallback)) {
                    result.errorCallback(status);
                }
                result.waiting = false;
            });
        },
        sendPost: function (url, params, result, pageCallback) {

            var curtime = new Date().getTime();
            $log.debug("current time : " + curtime);
            if (angular.isDefined(result) && angular.isDefined(result.waiting) && result.waiting) {
                $log.debug("repeat request");
                if (angular.isDefined(result.starttime)) {
                    if ((curtime - result.starttime) < 1500) {
                        return;
                    }
                }
            }
            result.waiting = true;
            result.starttime = curtime;
            var sendData = params;
            var config = http_defaults;
            var serverUrl = SERVER_URL + url;
            $log.debug('url: ' + serverUrl + "?methos=POST,params=" + angular.toJson(sendData));
            $http.post(serverUrl, sendData).success(function (data, status, headers, config) {
                $log.debug('result: ' + angular.toJson(data, false));
                result.status = status;
                result.data = data;
                if (angular.isDefined(pageCallback)) {
                    pageCallback(data);
                }
                if (angular.isDefined(result.successCallback)) {
                    result.successCallback(data);
                }
                result.waiting = false;
            }).error(function (data, status, headers, config) {
                $log.debug('error: ' + status + data.code + data.info);
                result.status = status;
                if (angular.isDefined(result.errorCallback)) {
                    result.errorCallback(status);
                }
                result.waiting = false;
            });
        }
    }
}]);

/*SalesServices.service('fileUpload', ['$http', function ($http) {
 this.uploadFileToUrl = function(file, uploadUrl){
 //提交的数据
 var fd = new FormData();
 fd.append('file', file);
 console.log(angular.fromJson(file));

 $http.post(uploadUrl, fd, {
 transformRequest: angular.identity,
 headers: {'Content-Type': 'application/octet-stream'}
 })
 .success(function(){
 alert('success');
 })
 .error(function(){
 alert('error');
 });
 }
 }]);
 */

