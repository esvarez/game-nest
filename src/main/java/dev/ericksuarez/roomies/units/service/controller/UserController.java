package dev.ericksuarez.roomies.units.service.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.client.AuthClient;
import dev.ericksuarez.roomies.units.service.model.User;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpClient;
import java.util.List;

import static dev.ericksuarez.roomies.units.service.config.UnitsUri.API;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.UNITS;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.USERS;

@RestController
@RequestMapping(API)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(UNITS + "/{unitId}" + USERS)
    public List<User> getUsersByUnitId(@PathVariable(value = "unitId") Long unitId) {
        log.info("event=getUsersByUnitIdInvoked, unitId={}", unitId);
        return userService.getUsersByUnitId(unitId);
    }

    @GetMapping("/test")
    public String test () {

        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);


        AuthClient authClient = new AuthClient(httpClient, objectMapper);

        RegisterUserDto user = RegisterUserDto.builder()
                .email("email@mail.com")
                .enabled(true)
                .username("newUsername")
                .build();
        authClient.registerUser(user);
        return "Done men";
    }

}
