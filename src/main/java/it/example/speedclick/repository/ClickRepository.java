package it.example.speedclick.repository;

import it.example.speedclick.dto.TimesInputDto;
import it.example.speedclick.dto.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ClickRepository {

    List<BigDecimal> getAllTimes();
    void registerTimes(TimesInputDto dto);

    User checkOrInsertUser(User user);
    Map<String, BigDecimal> getMinMap();

    User getUserById(String userId);

}
