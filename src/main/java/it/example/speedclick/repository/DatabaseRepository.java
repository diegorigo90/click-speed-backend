package it.example.speedclick.repository;

import it.example.speedclick.dto.Time;
import it.example.speedclick.dto.TimesInputDto;
import it.example.speedclick.dto.User;
import it.example.speedclick.dto.UserBestTime;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository()
@Profile("database")
public class DatabaseRepository implements ClickRepository {

    Logger LOGGER = LoggerFactory.getLogger(DatabaseRepository.class);
    @Autowired
    TimesRepository timesRepository;
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initialize() {
        LOGGER.info("Initialized DatabaseRepository");
    }

    @Override
    public List<BigDecimal> getAllTimes() {
        return timesRepository.findAll().stream().map(times -> new BigDecimal(times.getTime())).toList();
    }

    @Override
    public void registerTimes(TimesInputDto dto) {
        String userId = dto.getUserId();
        timesRepository.saveAll(dto.getTimes().stream().map(time -> new Time(time.longValue(), userId)).toList());
    }

    @Override
    public User checkOrInsertUser(User user) {
        return userRepository.findUserByNameAndSurname(user.getName(), user.getSurname()).orElseGet(() -> {
            user.setId(UUID.randomUUID().toString());
            return userRepository.save(user);
        });
    }

    @Override
    public Map<String, BigDecimal> getMinMap() {
        Map<String, BigDecimal> minMap = new HashMap<>();

        Time times = timesRepository.findFirstByOrderByTime();
        if (times != null) {
            minMap.put(times.getUserId(), new BigDecimal(times.getTime()));
        }

        return minMap;
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException(
                "No user with id: " + userId));
    }

    @Override
    public List<UserBestTime> getClassification() {
        return timesRepository.getClassification().stream().map(item -> {
            UserBestTime dto = new UserBestTime();
            dto.setUser(item[0].toString() + " " + item[1].toString());
            dto.setUserId(item[2].toString());
            dto.setTime(Integer.valueOf(item[3].toString()));
            return dto;
        }).collect(Collectors.toList());
    }
}
