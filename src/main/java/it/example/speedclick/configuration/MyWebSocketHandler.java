package it.example.speedclick.configuration;

import it.example.speedclick.dto.Data;
import it.example.speedclick.dto.MyMessage;
import it.example.speedclick.dto.Point;
import it.example.speedclick.repository.ClickRepository;
import it.example.speedclick.utility.MathUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    ClickRepository repository;

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        System.out.println("error occured at sender " + session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(String.format("Session %s closed because of %s", session.getId(), status.getReason()));
        sessions.remove(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected ... " + session.getId());
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage jsonTextMessage) throws Exception {
        System.out.println("message received: " + jsonTextMessage.getPayload());
    }

    public void sendBroadcast() {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    ObjectMapper mapper = new ObjectMapper();
                    TextMessage message = new TextMessage(mapper.writeValueAsString(toMessage()));
                    session.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private MyMessage toMessage() {

        List<Point> points = generateHistogramPoints();

        Data data = new Data();
        data.setX(points.stream().map(Point::getX).toList());
        data.setY(points.stream().map(Point::getY).toList());
        data.setInfo(generateInfoMap());

        return new MyMessage(data);
    }

    private List<Point> generateHistogramPoints() {
        List<BigDecimal> values = repository.getAllTimes();
        System.out.println(MessageFormat.format("Broadcast message: {0} values registered", values.size()));
        List<Point> points = MathUtils.computeHistogram(values, BigDecimal.ZERO, new BigDecimal(500), 20);
        return points;
    }

    private Map<String, String> generateInfoMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Top User",getTopUserInfo());
        return map;
    }

    private String getTopUserInfo() {
        return repository.getMaxMap()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(item -> item.getValue()))
                .findFirst()
                .map(item -> item.getKey() + " - " + item.getValue()).orElse("...");
    }
}
