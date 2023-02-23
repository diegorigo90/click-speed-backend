package it.example.speedclick.repository;

import it.example.speedclick.dto.TimesInputDto;
import it.example.speedclick.dto.User;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class ClickRepository {
    private final List<User> users = new ArrayList<>();

    private final List<BigDecimal> allTimes = new ArrayList<>();

    private final Map<String, BigDecimal> minMap = new HashMap<>();

    public List<User> getUsers() {
        return users;
    }

    public List<BigDecimal> getAllTimes() {
        return allTimes;
    }

    public void registerTimes(TimesInputDto dto) {
        List<BigDecimal> times = dto.getTimes();
        String userId = dto.getUserId();

        allTimes.addAll(times);

        handleMaximum(userId);
    }

    private void handleMaximum(String userId) {
        BigDecimal min = allTimes.stream()
                .min(Comparator.naturalOrder())
                .orElseThrow();
        BigDecimal lastMin = minMap.get(userId);
        if (Objects.isNull(lastMin) || min.compareTo(lastMin) < 0) {
            minMap.put(userId, min);
        }
    }

    public User checkOrInsertUser(User user) {
        return users.stream()
                .filter(item -> item.getName()
                        .equals(user.getName()) && item.getSurname()
                        .equals(user.getSurname()))
                .findFirst()
                .orElseGet(() -> {
                    user.setId(UUID.randomUUID().toString());
                    users.add(user);
                    System.out.println("Inserting new user: " + user);
                    return user;
                });
    }

    public Map<String, BigDecimal> getMinMap() {
        return minMap;
    }

    public User getUserById(String userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "No user with id: " + userId));
    }
}
