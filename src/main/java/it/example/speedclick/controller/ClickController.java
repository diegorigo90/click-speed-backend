package it.example.speedclick.controller;

import it.example.speedclick.configuration.MyWebSocketHandler;
import it.example.speedclick.dto.TimesInputDto;
import it.example.speedclick.dto.User;
import it.example.speedclick.repository.ClickRepository;
import it.example.speedclick.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Add Value S.p.A. by diego.rigo
 * @version Dec  15, 2022
 * @since 8.3.0
 */
@RestController
@RequestMapping("/clicks")
public class ClickController {
    @Autowired
    ClickService service;

    @Autowired
    MyWebSocketHandler webSocketHandler;

    @PostMapping()
    public void addTimes(@RequestBody TimesInputDto dto) {
        service.registerTimes(dto);
        webSocketHandler.sendBroadcast();
    }

    @PostMapping("/user")
    @ResponseBody
    public User checkOrInsertUser(@RequestBody User user) {
        return service.checkOrInsertUser(user);
    }
}
