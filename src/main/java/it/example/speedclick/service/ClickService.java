package it.example.speedclick.service;

import it.example.speedclick.dto.Point;
import it.example.speedclick.dto.User;
import it.example.speedclick.utility.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Add Value S.p.A. by diego.rigo
 * @version Dec  15, 2022
 * @since 8.3.0
 */
@Service
public class ClickService {

    private final List<User> users = new ArrayList<>();

    private final List<BigDecimal> allTimes = new ArrayList<>();
    @Autowired
    public SimpMessageSendingOperations messagingTemplate;

    public void sendBroadcastMessageWithUpdates() {
        System.out.println(MessageFormat.format("Broadcast message: {0} values registered",allTimes.size()));
        List<Point> points = MathUtils.computeHistogram(allTimes, BigDecimal.ZERO, BigDecimal.TEN, 50);
        messagingTemplate.convertAndSend("/topic/greetings", points);
    }

    public void addTimes(List<BigDecimal> times) {
        allTimes.addAll(times);
        sendBroadcastMessageWithUpdates();
    }

    public User checkOrInsertUser(User user) {
        return users.stream()
                .filter(item -> item.getName().equals(user.getName()) && item.getSurname().equals(user.getSurname()))
                .findFirst()
                .orElseGet(() -> {
                    user.setId(computeUserId());
                    users.add(user);
                    System.out.println("Inserting new user: " + user);
                    return user;
                });
    }

    private static String computeUserId() {
        return UUID.randomUUID().toString();
    }
}
