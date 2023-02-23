package it.example.speedclick.repository;

import it.example.speedclick.dto.Times;
import it.example.speedclick.dto.TimesInputDto;
import it.example.speedclick.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository()
@Profile("database")
public class DatabaseRepository implements ClickRepository {
    @Autowired
    TimesRepository timesRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<BigDecimal> getAllTimes() {
        return timesRepository.findAll().stream().map(times -> new BigDecimal(times.getTime())).toList();
    }

    @Override
    public void registerTimes(TimesInputDto dto) {
        String userId = dto.getUserId();
        timesRepository.saveAll(dto.getTimes().stream().filter(bigDecimal -> bigDecimal.compareTo(new BigDecimal(30)) > 0 ).map(time -> new Times(time.longValue(), userId)).toList());
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

        Times times = timesRepository.findFirstByOrderByTime();
        if(times != null) {
            minMap.put(times.getUserId(), new BigDecimal(times.getTime()));
        }

        return minMap;
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException(
                "No user with id: " + userId));

    }
}
