package it.example.speedclick.service;

import it.example.speedclick.dto.TimesInputDto;
import it.example.speedclick.dto.User;
import it.example.speedclick.repository.ClickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ClickService {

    @Autowired
    ClickRepository repository;

    public void registerTimes(TimesInputDto dto) {
        repository.registerTimes(dto);
    }

    public User checkOrInsertUser(User user) {
        return repository.checkOrInsertUser(user);
    }
}
