/*
 * @Date: 2021-08-23 14:31:01
 * @LastEditors: CHEN SHENGWEI
 * @LastEditTime: 2021-09-02 16:13:23
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
        localStorage.setItem("jsonData", res);
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

/**
 * @description: 呼出笔记创建页面
 * @param {*}
 * @return {*}
 */
function createNote() {
    layx.iframe('shadow-color', 'ノート作成', 'createNote', {
        shadable: 0.8
    });
    layx.setSize('shadow-color', { width: 700, height: 600 });
}

function getNote(page, status) {
    localStorage.setItem("pageNo", page);
    var getNoteObject = new Object();
    getNoteObject.userMobile = localStorage.getItem("userMobile");
    getNoteObject.status = status;
    getNoteObject.page = page;
    var jsonGetNote = JSON.stringify(getNoteObject);
    var res = JSON.parse(javaService("/getNote", jsonGetNote));
    if (JSON.stringify(res) == '{}') {
        $("#noteView").empty();
        $("#noteView").append("<button type='button' id='createNote' class='btn btn-primary' onclick='createNote()'>ノート作成</button>");
    } else {
        noteView(page, res, status);
    }
}

/**
 * @description: 显示笔记
 * @param {*} page
 * @param {*} res
 * @return {*}
 */
function noteView(page, res, status) {
    var count = res.count;
    delete res.count;
    $("#noteView").empty();
    var innerHtml = "";
    for (var index in res) {
        var content = JSON.parse(res[index]);
        innerHtml += "<div class='panel panel-default'>";
        innerHtml += "<div class='panel-heading'>";
        innerHtml += "<h4>" + content.title;
        innerHtml += "<button type='button' class='btn btn-default' aria-label='Left Align' onclick='deleteNote(" + content.id + ")' style='float:right'>";
        innerHtml += "<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>";
        innerHtml += "</button>";
        innerHtml += "<button type='button' class='btn btn-default' aria-label='Left Align' onclick='editNote(" + content.id + ")' style='float:right'>";
        innerHtml += "<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span>";
        innerHtml += "</button>";
        innerHtml += "<button type='button' class='btn btn-default' style='float:right'>";
        innerHtml += "<span class='glyphicon glyphicon-star-empty' aria-hidden='true'></span> Star";
        innerHtml += "</button>";
        innerHtml += "</h4>";
        innerHtml += "<span class='label label-success' style=' margin-right: 3px;'>" + content.category_name + "</span>";
        innerHtml += "<span class='label label-info' style=' margin-right: 3px;'>star&nbsp;<span class='badge'>" + content.star + "</span></span>";
        innerHtml += "<span class='label label-warning' style=' margin-right: 3px;'>更新時間&nbsp;" + getTime(content.update_date) + "</span>";
        innerHtml += "</div>";
        innerHtml += "<div class='panel-body'>";
        innerHtml += "<pre>" + html2Escape(content.content) + "</pre>";
        innerHtml += "</div></div>";
    }
    innerHtml += "<nav aria-label='Page navigation'>";
    innerHtml += "<ul class='pagination'>";
    innerHtml += "<li>";
    innerHtml += "<a href='#' aria-label='Previous' onclick='getPrePage(" + status + ")'>";
    innerHtml += "<span aria-hidden='true'>&laquo;</span>";
    innerHtml += "</a>";
    innerHtml += "</li>";
    var allPage;
    if (count % 3 != 0) {
        allPage = Math.ceil(count / 3);
    } else {
        allPage = count / 3;
    }
    localStorage.setItem("allPage", allPage);
    var endPage;
    var startPage;
    //最多显示七页
    if (allPage < 7) {
        startPage = 0;
        endPage = allPage;
    } else {
        if (page <= 3) {
            startPage = 0;
            endPage = 6;        
        } else {
            startPage = Number(page)- 3;
            endPage = Number(page)+ 3;
        }
    }
    if(endPage>allPage){
        endPage=allPage;
        startPage=Number(allPage)- 6;
    }
    for (var i = startPage; i < endPage; i++) {
        if (i == localStorage.getItem("pageNo")) {
            innerHtml += "<li><a href='#' style='pointer-events: none;background-color:#D3D3D3'>" + parseInt(i + 1) + "</a></li>";
        } else {
            innerHtml += "<li><a href='#' onclick='getNote(" + i + "," + status + ")'>" + parseInt(i + 1) + "</a></li>";
        }

    }
    innerHtml += "<li>";
    innerHtml += "<a href='#' aria-label='Next'  onclick='getNextPage(" + status + ")'>";
    innerHtml += "<span aria-hidden='true'>&raquo;</span>";
    innerHtml += "</a>";
    innerHtml += "</li>";
    innerHtml += "</ul>";
    innerHtml += "</nav>";
    $("#noteView").append(innerHtml);
}

function getTime(date) {
    return date.substring(0, 4) + "年" + date.substring(4, 6) + "月" + date.substring(6, 8) + "日 " + date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14)
}


function deleteNote(noteId) {
    layx.confirm('WARN', 'このノートを削除しますか', function (id) {
        var jsonObject = new Object();
        jsonObject.id = noteId;
        jsonObject.userMobile = localStorage.getItem("userMobile");
        var jsonData = JSON.stringify(jsonObject);
        var res = javaService("/deleteNote", jsonData);
        var json = JSON.parse(res);
        if (json.result) {
            layx.msg('ノート作成成功しました。', { dialogIcon: 'success' });
        } else {
            layx.msg(json.errorMsg, { dialogIcon: 'error' });
        }
        layx.destroy('loadId');
        parent.location.reload();
    }, { dialogIcon: 'warn' });
}
function getNextPage(status) {
    var pageNo = localStorage.getItem("pageNo");
    var allPage = localStorage.getItem("allPage");
    var newPage=Number(pageNo)+ 1;
    if (newPage >= allPage) {
        getNote(pageNo, status);
    } else {
        getNote(newPage, status);
    }

}

function getPrePage(status) {
    var pageNo = localStorage.getItem("pageNo");
    if (pageNo == 0) {
        getNote(parseInt(pageNo), status);
    } else {
        getNote((Number(pageNo)-1), status);
    }
}

function editNote(noteId) {
    localStorage.setItem("editNoteId", noteId);
    layx.iframe('shadow-color', 'ノート作成', 'editNote', {
        shadable: 0.8
    });
    layx.setSize('shadow-color', { width: 700, height: 600 });
}

/**
 * @description: HTML标签转义（< -> &lt;）
 * @param {*} sHtml
 * @return {*}
 */
function html2Escape(sHtml) {
    return sHtml.replace(/[<>&"]/g, function (c) {
        return { '<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;' }[c];
    });
}

