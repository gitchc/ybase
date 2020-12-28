/****************************************
 layer弹窗控件的基础封装
 @author chenghch
 *********************************************/

/**
 * 显示一个提示窗口。参数： title： 显示在头部面板的标题文字。 msg： 显示的消息文字。 icon： 显示图标的图片。可用的值是：
 * error、question、info、warning。 fn： 窗口关闭时触发的回调函数。
 */
// 提示信息
function showInfo(msg, title) {
    if (!title)
        title = '提示信息';
    layer.alert(msg, {
        icon: 1,
        title: title,
        shade: false,
        zIndex: 2147483647

    });
}

// 错误提示
function showError(msg, title) {
    if (!title)
        title = '错误信息';
    layer.alert(msg, {
        icon: 2,
        title: title,
        shade: false,
        zIndex: 2147483647

    });
}

// 警告提示
function showWarn(msg, title) {
    if (!title)
        title = '警告信息';
    layer.alert(msg, {
        icon: 0,
        title: title,
        shade: false,
        zIndex: 2147483647
    });
}


// 保存
function tipsInfo(msg, time) {
    if (!time) {
        time = 1500
    }
    layer.msg(msg, {
        icon: 1,
        time: time
    });
}

// 警告提示层
function tipsWarn(msg, time) {
    if (!time) {
        time = 1500
    }
    layer.msg(msg, {
        icon: 0,
        time: time
    });
}

// 错误提示
function tipsError(msg, time) {
    if (!time) {
        time = 1500
    }
    layer.msg(msg, {
        icon: 2,
        time: time
    });
}

// 绑定元素周围的tips,type:1,2,3,4分别对应上右下左,obj可以是过滤器也可以是元素对象例如:"#id"或者(document.getelemetbyid('id'))
function showTips(msg, obj, type) {
    if (!type)
        type = 3;
    layer.tips(msg, obj, {
        tips: [type, '#3595CC'],
        time: 2500
    });
}


// 打开进度条
function showLoading(loadtips) {
    if (!loadtips) {
        loadtips = "正在执行...";
    }
    var index = layer.load(2, {shade: [0.1, '#fff']}); //又换了种风格，并且设定最长等待30秒
}

function closeLoading() {
    layer.closeAll("loading");
}

// prompt,对话框
function showPrompt(title, fn, value, type) {
    layer.prompt({
        title: title,
        formType: (type === null || type === undefined) ? 0 : type,
        value: !value ? "" : value
    }, function (value, index) {
        // 执行完毕，关闭窗体
        fn(value);
        layer.close(index);
    });
}

/*******************************************************************************
 * ***询问框 title:弹窗title msg:弹窗信息 yes:点确定后执行的方法 no:点取消后执行的方法 yestext：按钮文字，默认：确定
 * notext:按钮文字，默认：取消
 ******************************************************************************/
function showConfirm(title, msg, yes, no, yestext, notext) {
    if (!yestext) {
        yestext = '确定';
    }
    if (!notext) {
        notext = '取消';
    }
    layer.confirm(
        msg, {btn: [yestext, notext], title: title}, function (index) {
            if (yes)
                yes();
            layer.close(index);
        }, function (index) {
            if (no)
                no();
            layer.close(index);
        }
    );

}


function openContentDialog(title, width, height, content, yesFn) {// div组件打开弹窗
    layer.open({
        type: 1, // page层
        area: [width, height],
        title: title,
        shade: 0.3, // 遮罩透明度
        moveType: 1, // 拖拽风格，0是默认，1是传统拖动
        maxmin: false,
        // skin: 'layui-layer-prompt',
        btn: ['确定', '取消'],
        yes: function (index) {
            if (yesFn)
                yesFn();
            layer.close(index);
        },
        no: function (index) {
            layer.close(index);
        },
        content: content
    });
    return layer;
}

/*******************************************************************************
 * ***********URL弹窗********* *************************** title:标题 url:地址
 * width:宽度 height:高度 fn:点确定之后的回调函数
 ******************************************************************************/
function openUrlDialog(title, url, width, height, fn) {
    layer.open({
        type: 2, // page层
        area: [width, height],
        title: false,
        shade: 0.3, // 遮罩透明度
        maxmin: false,
        moveType: 0, // 拖拽风格，0是默认，1是传统拖动
        btn: ['确定', '取消'],
        yes: function (index) {
            if (fn)
                fn();
            layer.close(index);
        },
        no: function (index) {
            layer.close(index);
        },
        content: url
    });
}

/*******************************************************************************
 * ***********打开全屏窗口********* *************************** title:标题 url:地址
 * width:宽度 height:高度 fn:点确定之后的回调函数
 ******************************************************************************/
function openFullDialog(title, url, width, height) {
    var index = layer.open({
        type: 2, // page层
        area: [width, height],
        title: title,
        shade: 0.3, // 遮罩透明度
        moveType: 1, // 拖拽风格，0是默认，1是传统拖动
        maxmin: true,
        content: url
    });
    layer.full(index);
}

function fetchGet(url, successFn) {
    $.ajax({
        url: url,
        type: "Get",
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                if (successFn) {
                    successFn(data.data);
                }
            } else {
                showWarn(data.message);
            }
        },
        error: function (data) {
            showError('错误码:' + data.code + ",错误信息:" + data.message);
        }
    });
}

function fetchPost(url, data, successFn) {
    $.ajax({
        url: url,
        data: data,
        type: "Post",
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                if (successFn) {
                    successFn(data.data);
                }
            } else {
                showWarn(data.message);
            }
        },
        error: function (data) {
            showError('错误码:' + data.code + ",错误信息:" + data.message);
        }
    });

}

//解析url参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
