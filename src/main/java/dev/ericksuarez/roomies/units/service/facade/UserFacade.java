package dev.ericksuarez.roomies.units.service.facade;

import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import dev.ericksuarez.roomies.units.service.model.responses.UserRegister;
import dev.ericksuarez.roomies.units.service.service.AuthClientService;
import dev.ericksuarez.roomies.units.service.service.UnitService;
import dev.ericksuarez.roomies.units.service.service.UserClientService;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserFacade {

    private AuthClientService authClientService;

    private UserClientService userClientService;

    private UserService userService;

    private UnitService unitService;

    private final Long DEFAULT_UNIT = 1L;

    @Autowired
    public UserFacade(AuthClientService authClientService, UserClientService userClientService, UserService userService,
                      UnitService unitService) {
        this.userService = userService;
        this.unitService = unitService;
        this.authClientService = authClientService;
        this.userClientService = userClientService;
    }

    public User registerUser(RegisterUserDto userDto, Optional<String> unitReference) {
        log.info("event=registerUserFacadeInvoked, userDto={}", userDto);
        UserRegister userRegister = userDto.getUserRegister();
        userRegister.setEnabled(true);
        //AuthUserResponse userResponse = userClientService.registerServerUser(userDto);
        //log.info("event=userRegisterOnAuthServer, userResponse={}", userResponse);
        User user = User.builder()
                //.id(userResponse.getId())
                .id(UUID.randomUUID())
                .username(userRegister.getUsername())
                .unit(retrieveUnit(unitReference))
                .active(true)
                .build();
        log.info("event=userBuilt, userBuilt={}", user);
        return userService.registerUser(user);
    }

    private Unit retrieveUnit(Optional<String> unitReference) {
        log.info("event=retrieveUnit, unitReference={}", unitReference);
        return unitReference.isPresent()
               ? unitService.findUnitByReference(unitReference.get())
               : Unit.builder().id(DEFAULT_UNIT).build();
    }

}
