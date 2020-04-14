package dev.ericksuarez.roomies.units.service.facade;

import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import dev.ericksuarez.roomies.units.service.model.responses.UserRegister;
import dev.ericksuarez.roomies.units.service.service.AuthClientService;
import dev.ericksuarez.roomies.units.service.service.UserClientService;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserFacade {

    private AuthClientService authClientService;

    private UserClientService userClientService;

    private UserService userService;

    private final long DEFAULT_UNIT = 1L;

    @Autowired
    public UserFacade(AuthClientService authClientService, UserClientService userClientService, UserService userService) {
        this.userService = userService;
        this.authClientService = authClientService;
        this.userClientService = userClientService;
    }

    public User registerUser(RegisterUserDto userDto, Optional<String> unitReference) {
        log.info("event=registerUserFacadeInvoked, userDto={}", userDto);
        UserRegister userRegister = userDto.getUserRegister();
        userRegister.setEnabled(true);
        User user = buildUser(unitReference);
        AuthUserResponse userResponse = userClientService.registerServerUser(userDto);
        log.info("event=userRegisterOnAuthServer, userResponse={}", userResponse);
        User userBuilt = user.toBuilder()
                .id(userResponse.getId())
                .username(userRegister.getUsername())
                .active(true)
                .build();
        log.info("event=userBuilt, userBuilt={}", userBuilt);
        return userService.registerUser(userBuilt);
    }

    private User buildUser(Optional<String> unitReference) {
        if (unitReference.isPresent()){
            // find unit
            return null;
        } else {
            return User.builder()
                    .unit(Unit.builder()
                        .id(DEFAULT_UNIT)
                        .build())
                    .build();
        }
    }

}
