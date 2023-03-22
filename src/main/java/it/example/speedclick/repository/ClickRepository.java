package it.example.speedclick.repository;

import it.example.speedclick.dto.Time;
import it.example.speedclick.dto.TimesInputDto;
import it.example.speedclick.dto.User;
import it.example.speedclick.dto.UserBestTime;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ClickRepository {

    List<BigDecimal> getAllTimes();
    void registerTimes(TimesInputDto dto);

    User checkOrInsertUser(User user);
    Map<String, BigDecimal> getMinMap();

    User getUserById(String userId);

    List<UserBestTime> getClassification();
}
