var stompClient = null;

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
    var project = $("#project").val();
    var topic = $("#topic").val();

    var socket = new SockJS('/msg-center/websocket?token=' + token + '&projectId=' + project);

    stompClient = Stomp.over(socket);

    stompClient.connect({
        userId: 'xxx' // 该参数的作用是?
    }, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        // 订阅广播消息
        stompClient.subscribe('/topic/' + project + '/' + topic, function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });

        // 订阅单用户消息
        stompClient.subscribe('/user/queue/' + project + '/', function (greeting) {
            showGreeting('UserMessage: ' + JSON.parse(greeting.body).content);
        });

    }, function (res) {
        // 错误取消重试
        console.log(res)
        stompClient.abort()
    });
}


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

// function sendName() {
//     stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
// }

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

