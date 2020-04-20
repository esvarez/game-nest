package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.client.AuthClient;
import dev.ericksuarez.roomies.units.service.client.UserClient;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
@Slf4j
public class UserClientService {
    private AuthClient authClient;

    private UserClient userClient;

    @Autowired
    public UserClientService(AuthClient authClient, UserClient userClient){
        this.authClient = authClient;
        this.userClient = userClient;
    }

    public AuthUserResponse registerServerUser(RegisterUserDto userDto) {
        log.info("event=registerServerUserInvoked userDto={}", userDto);

        HttpResponse<String> response = userClient.registerUser(userDto);
        log.info("event=registerServerUserInvoked userDto={}", userDto);
        AuthUserResponse user = null;
        if (response.statusCode() == 201){
            user = userClient.getUserFromEmail(userDto.getUserRegister().getEmail())
                    .orElseThrow(() -> new RuntimeException("Error to create user"));
            log.info("event=userRetrieve user={}", userClient);
            userClient.setPassword(user.getId(), userDto.getCredentials());
        }

        log.info("event=registerUserCalled user={}", user);
        return user;
    }



}
