'use strict';

/* Filters */

var SalesFilters = angular.module('SALES.filters', []);
SalesFilters.filter('toTimestamp', function () {
    var toTimestampFilter = function (input) {
        if (input == null || angular.isUndefined(input)) {
            return '';
        }
        var ins = input.split('.');
        var d = Date.parse(ins[0]);
        if (isNaN(d)) {
            return '';
        }
        return Date.parse(ins[0]);
    };
    return toTimestampFilter;
});

SalesFilters.filter('toSexFormat', function () {
    var toSexFormatFilter = function (input) {
        if (input === 1) {
            return '男';
        }
        else if (input === 2) {
            return '女';
        }
        if (input === 3) {
            return '其他';
        }
        return '';
    };
    return toSexFormatFilter;
});
//文件大小转换器[B-->M,K]
SalesFilters.filter('sizeFormat', function () {
    //reserved : 保留位数
    var toSizeFormatFilter = function (input, reserved) {
        if (input == null) {
            return 0 + "M";
        } else {
            if (input < 1024) {
                return new Number(input).toFixed(reserved) + "B";
            }
            if (input >= 1024 && input < (1024 * 1024)) {
                return new Number(input / 1024).toFixed(reserved) + "KB";
            }
            else if (input > (1024 * 1024 * 1024)) {
                return new Number(input / (1024 * 1024 * 1024)).toFixed(reserved) + "GB";
            }
            else {
                return new Number(input / (1024 * 1024)).toFixed(reserved) + "MB";
            }

        }
    };
    return toSizeFormatFilter;
});

SalesFilters.filter('roleFormat', function () {
    var filter = function (input) {
        if (input == null) {
            return '';
        } else {
            if (input === 0) {
                return '管理员';
            }
            if (input === 1) {
                return '普通用户';
            }
        }
    };
    return filter;
});

SalesFilters.filter('sexFormat', function () {
    var filter = function (input) {
        if (input == null) {
            return '';
        } else {
            if (input === 1) {
                return '男';
            }
            if (input === 2) {
                return '女';
            }
            if (input === 3) {
                return '其他';
            }
        }
    };
    return filter;
});

SalesFilters.filter('processFormat', function () {
    var filter = function (input) {
        if (input == null) {
            return '';
        } else {
            if (input === 1) {
                return '已处理';
            }
            if (input === 0) {
                return '未处理';
            }
            if (input === -1) {
                return '无需处理';
            }
        }
    };
    return filter;
});




