var stompClient = null;
var ws = null;

function setConnected(connected) {
    $("#connect").css("display", connected? "none": "block");
    $("#disconnect").css("display", connected? "block": "none");
}

function connect() {
    ws = new WebSocket('ws://localhost:8080/clickspeed');
    ws.onmessage = function (evt) {
        var resp = JSON.parse(evt.data);
        console.log(resp);
        console.log(resp.payload.info);
        $("#responses").prepend("<tr><td>" + resp.payload.info['Top User'] + "</td></tr>");
    }
    console.log("Connected");
    setConnected(true);
}

function disconnect() {
    ws.close();
    setConnected(false);
    console.log("Disconnected");
}

function send() {
    ws.send($("#message").val());
    $("#message").val('');
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
});