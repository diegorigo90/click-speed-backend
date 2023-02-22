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

    private final Map<String, BigDecimal> maxMap = new HashMap<>();

    public List<User> getUsers() {
        return users;
    }

    public List<BigDecimal> getAllTimes() {
        return allTimes;
    }

    public void registerTimes(TimesInputDto dto) {
        List<BigDecimal> times = dto.getTimes();
        String userId = dto.getUserId();

        handleMaximum(times, userId);
        handleMinimum(times, userId);

        allTimes.addAll(times);
    }

    private void handleMaximum(List<BigDecimal> times, String userId) {
        BigDecimal max = times.stream().max(Comparator.naturalOrder()).orElseThrow();
        BigDecimal lastMax = maxMap.get(userId);
        if (Objects.isNull(lastMax) || max.compareTo(lastMax) < 0) {
            maxMap.put(userId, max);
        }
    }

    private void handleMinimum(List<BigDecimal> times, String userId) {
        BigDecimal min = times.stream().min(Comparator.naturalOrder()).orElseThrow();
        BigDecimal lastMin = minMap.get(userId);
        if (Objects.isNull(lastMin) || min.compareTo(lastMin) < 0) {
            minMap.put(userId, min);
        }
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

    public Map<String, BigDecimal> getMinMap() {
        return minMap;
    }

    public Map<String, BigDecimal> getMaxMap() {
        return maxMap;
    }
}
