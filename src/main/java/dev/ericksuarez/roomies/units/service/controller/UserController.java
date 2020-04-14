package dev.ericksuarez.roomies.units.service.controller;

import dev.ericksuarez.roomies.units.service.facade.UserFacade;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.service.UnitService;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static dev.ericksuarez.roomies.units.service.config.UnitsUri.API;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.UNITS;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.USERS;

@Slf4j
@RestController
@RequestMapping(API)
public class UserController {

    private UserService userService;

    private UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade, UserService userService) {
        this.userFacade = userFacade;
        this.userService = userService;
    }

    @GetMapping(UNITS + "/{unitId}" + USERS)
    public List<User> getUsersByUnitId(@PathVariable(value = "unitId") Long unitId) {
        log.info("event=getUsersByUnitIdInvoked, unitId={}", unitId);
        if (unitId == 1){
            log.info("event=unitIdDefault, unitId={}", unitId);
            return null;
        }
        return userService.getUsersByUnitId(unitId);
    }

    @GetMapping(USERS + "/{userUUID}")
    public User getUserById(@PathVariable(value = "userUUID") UUID userUUID) {
        log.info("event=getUserByIdInvoked, userUUID={}", userUUID);
        return userService.findUser(userUUID);
    }

    @PostMapping(USERS)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestParam(name = "unit") Optional<String> unitReference,
                           @Valid @RequestBody RegisterUserDto user){
        log.info("event=userInvoked, unitReference={}, unitId={}", unitReference, user);
        return userFacade.registerUser(user, unitReference);
    }

    @PutMapping(USERS + "/{userUUID}")
    public User updateUser(@PathVariable(value = "userUUID") UUID userUUID,
                           @Valid @RequestBody User user) {
        log.info("event=updateUserInvoked uuid={}, user={}", userUUID, user);
        return userService.saveOrUpdateUser(userUUID, user);
    }

    //TODO update password
    @PatchMapping(USERS + "/{userUUID}")
    public User patchUser(@PathVariable(value = "userUUID") UUID userUUID,
                          @Valid @RequestBody RegisterUserDto user) {
        return null;
    }

    @DeleteMapping(USERS + "/{userUUID}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userUUID") UUID userUUID) {
        log.info("event=deleteUserInvoked uuid={}", userUUID);
        return userService.deleteUser(userUUID);
    }

}
