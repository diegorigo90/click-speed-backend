var stompClient = null;

function setConnected(connected) {
    $("#connect").css("display", connected? "none": "block");
    $("#disconnect").css("display", connected? "block": "none");
    $("#start").prop("disabled", !connected);
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function send() {
    stompClient.send("/app/hello", {}, $("#message").val());
    $("#message").val('');
}

function showGreeting(message) {
    $("#greetings").prepend("<tr><td>" + message + "</td></tr>");
}

function start() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/cippa", true);
    xhr.send();
}

$(function () {
    $("#message").on('keypress', function (e) {

        if(e.which == 10 || e.which == 13) {
            send();
        }
        e.stopPropagation();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { send(); });
    $( "#start" ).click(function() { start(); });
});