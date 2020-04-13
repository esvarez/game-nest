package dev.ericksuarez.roomies.units.service.controller;

import dev.ericksuarez.roomies.units.service.facade.UserFacade;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping(UNITS + "/{unitId}" + USERS)
    public List<User> getUsersByUnitId(@PathVariable(value = "unitId") Long unitId) {
        log.info("event=getUsersByUnitIdInvoked, unitId={}", unitId);
        return userService.getUsersByUnitId(unitId);
    }

    @GetMapping("/test")
    public String test () {
        RegisterUserDto user = RegisterUserDto.builder()
                .email("nuevo@mail.com")
                .enabled(true)
                .username("nuevo")
                .build();

        val z = userFacade.registerUser(user);

        return "Done men";
    }

}
