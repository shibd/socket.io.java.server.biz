var socket = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var token = $("#token").val();
    var projectId = $("#project").val()

    // var url = 'http://139.217.99.53:9092/' + projectId;
    var url = 'http://localhost:9092/' + projectId;
    socket = io.connect(url, { forceNew: true })

    // 1. 连接，认证，订阅
    var _subscribes = [$("#topic_1").val(), $("#topic_2").val()];
    var _authData = {projectId: projectId, token: token};
    socket.on('connect', function () {
        // 2. 认证
        socket.emit('auth', _authData, function (authCode) {
            console.log("auth ack code:" + authCode);
            // 1.1 认证成功发起订阅
            if (authCode === 'auth_success') {
                socket.emit('subscribe', _subscribes, function (subscribeCode) {
                    console.info('sub ack code:' + subscribeCode);
                    if (subscribeCode === "sub_success") {
                        setConnected(true);
                    } else {
                        disconnect();
                    }
                });
            } else {
                // 1.2 认证失败关闭连接
                disconnect();
            }
        });
    });

    // 其他事件socket-io.js会保证重连
    //     "connect" "connecting" "disconnect" "error" "message" "connect_error"
    //     "connect_timeout" "reconnect" "reconnect_error" "reconnect_failed";
    //     "reconnect_attempt" "reconnecting" "ping" "pong";
    socket.on('disconnect', function (data) {
        setConnected(false);
        console.info(data)
    });

    // 普通事件
    socket.on('event_1', function (data) {
        var data = JSON.parse(data);
        showGreeting('event_1:' + data.playLoad + ' topic:' + data.topic)
    });

    // 普通事件
    socket.on('event_2', function (data) {
        showGreeting('event_2:' + data.playLoad + ' topic:' + data.topic)
    });

}


function disconnect() {
    socket.close();
    setConnected(false);
    console.log("Disconnected");
}


function sendName() {

    var reqData = JSON.stringify({
        'projectId': $("#project").val(),
        'topic': $("#topic_1").val(),
        'event': 'event_1',
        'playLoad': '{"content":' + '"' + $("#data").val() + '"}',
        'updateTime': 1557676800000
    });

    $.ajax({
        url: '/msg-center/ws_message/topic',
        // url: 'http://139.217.99.53:8080/msg-center/ws_message/topic',
        method: 'post',
        data: reqData,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (e) {
            console.log(e)
        },
        error: function (e) {
            console.log(e)
        }
    })
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});