package dev.ericksuarez.roomies.units.service.facade;

import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import dev.ericksuarez.roomies.units.service.service.AuthClientService;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserFacade {

    private AuthClientService authClientService;

    private UserService userService;

    @Autowired
    public UserFacade(AuthClientService authClientService, UserService userService) {
        this.authClientService = authClientService;
        this.userService = userService;
    }

    public User registerUser(RegisterUserDto userDto) {
        log.info("event=registerUserFacadeInvoked, userDto={}", userDto);
        AuthUserResponse userResponse = authClientService.registerServerUser(userDto);
        log.info("event=userRegisterOnAuthServer, userResponse={}", userResponse);
        User user = User.builder()
                .id(userResponse.getId())
                .username(userDto.getUsername())
                .build();
        log.info("event=userBuilt, user={}", user);
        return userService.registerUser(user);
    }
}