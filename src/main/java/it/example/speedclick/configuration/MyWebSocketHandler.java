package it.example.speedclick.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.example.speedclick.dto.Data;
import it.example.speedclick.dto.MyMessage;
import it.example.speedclick.dto.Point;
import it.example.speedclick.dto.User;
import it.example.speedclick.repository.ClickRepository;
import it.example.speedclick.utility.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, ConcurrentWebSocketSessionDecorator> sessions = new ConcurrentHashMap<>();

    @Autowired
//    @Qualifier("InMemoryRepository")
    @Qualifier("DatabaseRepository")
    ClickRepository repository;

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable throwable) throws Exception {
        System.out.println("error occured at sender " + session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        System.out.println(String.format("Session %s closed",
                                         session.getId()));
        try(ConcurrentWebSocketSessionDecorator decorator = sessions.remove(session.getId())) {
            System.out.println("Rimossa sessione");
        }


    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected ... " + session.getId());
        sessions.put(session.getId(), new ConcurrentWebSocketSessionDecorator (session, 2000, 4096));
        sendBroadcast();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage jsonTextMessage) throws Exception {
        System.out.println("message received: " + jsonTextMessage.getPayload());
        sendBroadcast();
    }

    public void sendBroadcast() {

        sessions.forEach((key, value) -> {
            if (value.isOpen()) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    TextMessage message = new TextMessage(mapper.writeValueAsString(
                            toMessage()));

                    value.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
        System.out.println(MessageFormat.format(
                "Broadcast message: {0} values registered",
                values.size()));
        return MathUtils.computeHistogram(values,
                                          new BigDecimal(30),
                                          new BigDecimal(300),
                                          20);
    }

    private Map<String, String> generateInfoMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Top User", getTopUserInfo());
        return map;
    }

    private String getTopUserInfo() {
        return repository.getMinMap()
                         .entrySet()
                         .stream()
                         .sorted(Map.Entry.comparingByValue())
                         .findFirst()
                         .map(item -> toString(item.getKey(),
                                               item.getValue()))
                         .orElse("...");
    }

    private String toString(String userId,
                            BigDecimal time) {
        User user = repository.getUserById(userId);
        return MessageFormat.format("{0} - {1} ms",
                                    user.toString(),
                                    time);
    }
}
