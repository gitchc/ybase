/**
 * 求笛卡尔积组合
 * @returns {any|*[]|{next: function(): (undefined|[]), nth: function(*): [], index: number}}
 */
var CrossUtils = {
    cross: function () {
         var args =[];
        if (arguments.length <2) {
            if (arguments[0] instanceof Array) {
                args = arguments[0];
            }else {
                return arguments[0] || [];
            }
        }else {
            args = Array.prototype.slice.call(arguments);
        }

        var that = {
            index: 0,
            nth: function (n) {
                var result = [],
                    d = 0;
                for (; d < this.dim; d++) {
                    var l = this[d].length;
                    var i = n % l;
                    result.push(this[d][i]);
                    n -= i;
                    n /= l;
                }
                return result;
            },
            next: function () {
                if (this.index >= size) return;
                var result = this.nth(this.index);
                this.index++;
                return result;
            }
        };
        var size = 1;
        for (var i = 0; i < args.length; i++) {
            size = size * args[i].length;
            that[i] = args[i];
        }
        that.size = size;
        that.dim = args.length;
        return that;
    }, transpose: function (obj) {
        var arr = []
        var arr2 = []
        var arr3 = []
        for (var key in obj) {
            arr.push(obj[key])
        }
        arr.forEach(function (item) {
            arr2.push(item.length)
        })
        var maxLen = Math.max(...arr2)
        for (var i = 0; i < maxLen; i++) {
            arr3.push(undefined)
        }
        if (arr[0].length < maxLen) {
            arr[0] = arr[0].concat(arr3).slice(0, maxLen)
        }
        var result = arr[0].map(function (col, i) {
            return arr.map(function (row) {
                return row[i]
            })
        });
        result = result.map(function (item) {
            return item.filter(function (i) {
                return i != undefined
            })
        })
        return result
    }
}
