package it.example.speedclick.dto;

import org.springframework.web.socket.WebSocketMessage;

public class MyMessage implements WebSocketMessage<Data> {

    private Data data;

    public MyMessage(Data data) {
        this.data = data;
    }

    @Override
    public Data getPayload() {
        return data;
    }

    @Override
    public int getPayloadLength() {
        return 1;
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
