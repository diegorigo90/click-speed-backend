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

        allTimes.addAll(times);
    }

    private void handleMaximum(List<BigDecimal> times,
                               String userId) {
        BigDecimal max = times.stream()
                              .max(Comparator.naturalOrder())
                              .orElseThrow();
        BigDecimal lastMax = maxMap.get(userId);
        if (Objects.isNull(lastMax) || max.compareTo(lastMax) < 0) {
            maxMap.put(userId, max);
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

    public Map<String, BigDecimal> getMaxMap() {
        return maxMap;
    }

    public User getUserById(String userId) {
        return users.stream()
                    .filter(user -> user.getId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "No user with id: " + userId));
    }
}
