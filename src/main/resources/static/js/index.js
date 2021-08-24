/*
 * @Date: 2021-08-23 14:31:01
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-08-24 14:24:47
 * @FilePath: \note\src\main\resources\static\js\index.js
 */
/**
 * @description: 更新头像页面呼出
 * @param {*} function
 * @return {*}
 */
$("#changeProfilePhoto").click(function () {
    layx.iframe('shadow-color', 'アバター更新', 'changeProfilePhoto', {
        shadable: 0.8
    });
    layx.setSize('shadow-color', { width: 950, height: 600 });
});
/**
 * @description: 创建科目
 * @param {*} value
 * @param {*} a
 * @return {*}
 */
function createCategory(value, a) {
    var headerObject = new Object();
    headerObject.token = localStorage.getItem("token");
    headerObject.userMobile = localStorage.getItem("userMobile");
    var headerInfo = JSON.stringify(headerObject);
    $.ajax({
        type: "POST",
        url: "/createCategory",
        beforeSend: function (request) {
            request.setRequestHeader("header", headerInfo);
        },
        data: {
            'userMobile': localStorage.getItem("userMobile"),
            'categoryName': value,
            'status': a
        },
        success: function (result) {
            var json = JSON.parse(result);
            if (json.result) {
                layx.alert('科目作成', '科目: ' + value + ' 作成成功', null, { dialogIcon: 'success' });
            } else {
                layx.alert('科目作成', '科目: ' + value + ' 作成失敗,' + json.errorMsg, null, { dialogIcon: 'error' });
            }
        },
        error: function (result) {
            layx.msg('サーバー異常、科目作成失敗しました', { dialogIcon: 'error' });
        }
    });
}
/**
 * @description: 登出
 * @param {*}
 * @return {*}
 */
function loginOut() {
    layx.confirm('ログアウト', 'ログアウトしますか？', null, {
        buttons: [
            {
                label: 'YSE',
                callback: function (id, button, event) {
                    localStorage.clear();
                    window.location.href = "login";
                }
            },
            {
                label: 'NO',
                callback: function (id, button, event) {
                    layx.destroy(id);
                }
            }
        ]
    });
}

/**
 * @description: 选择笔记的作者
 * @param {*}
 * @return {*}
 */
$("#authorUl li").click(function () {
    $("#subjectUl").empty();
    $("#subject").text("ノート科目");
    var v = $(this).text();
    $("#author").text(v);
    if (v == '自分') {
        var jsonObject = new Object();
        jsonObject.userMobile = localStorage.getItem("userMobile");
        var jsonData = JSON.stringify(jsonObject);
        var res = javaService("/getCategoryName", jsonData);
        var json = JSON.parse(res);
        if (JSON.stringify(res) !== '{}') {
            for (var subject in json) {
                $("#subjectUl").append("<li><a href='#'>" + json[subject] + "</a></li>");
            }
        }
    } else {
        $("#subjectUl").append("<li>科目名：<input type='text' id='newSubject' placeholder='入力してください。'</li >");
    }
})
$('#subjectUl').on('click', 'li', function () {
    var v = $(this).text();
    $("#subject").text(v);
})
$('#subjectUl').on('blur', '#newSubject', function () {
    var input = $('#subjectUl #newSubject').val();
    if (input == '') {
        layx.msg('科目名を入力してください。', { dialogIcon: 'warn' });
    } else {
        $("#subject").text(input);
    }
})
$("#subject").click(function () {
    var author = $("#author").text();
    if (author == 'ノート作成者') {
        layx.msg('ノート作成者を選んでください。', { dialogIcon: 'warn' });
    }
})

/**
 * @description: ajax同步
 * @param {*} url
 * @param {*} jsonData
 * @return {*}
 */
function javaService(url, jsonData) {
    var headerObject = new Object();
    headerObject.token = localStorage.getItem("token");
    headerObject.userMobile = localStorage.getItem("userMobile");
    var headerInfo = JSON.stringify(headerObject);
    var res;
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        beforeSend: function (request) {
            request.setRequestHeader("header", headerInfo);
        },
        data: {
            'jsonData': jsonData
        },
        success: function (result) {
            res = result;
        },
        error: function () {
            layx.msg('サーバー通信失敗しました。', { dialogIcon: 'error' });
        }
    });
    return res;
}