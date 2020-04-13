package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.client.AuthClient;
import dev.ericksuarez.roomies.units.service.client.UserClient;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthClientService {

    private AuthClient authClient;

    private UserClient userClient;

    @Autowired
    public AuthClientService(AuthClient authClient, UserClient userClient){
        this.authClient = authClient;
        this.userClient = userClient;
    }

    public AuthUserResponse registerServerUser(RegisterUserDto userDto) {
        log.info("event=registerServerUserInvoked userDto={}", userDto);

        /*AuthUserResponse user = userClient.registerUser(userDto)
                .orElseThrow(() -> new RuntimeException("UserNotCreated"));*/
        //log.info("event=registerUserCalled user={}", user);
        //return user;
        return null;
    }
}
