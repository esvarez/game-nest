package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.client.AuthClient;
import dev.ericksuarez.roomies.units.service.client.UserClient;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import lombok.val;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static dev.ericksuarez.roomies.units.service.util.TestUtil.buildResponseCreated;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserClientServiceTest {

    private UserClientService userClientService;

    @Mock
    private UserClient userClient;

    @Mock
    private AuthClient authClient;

    @Before
    public void setUp(){
        userClientService = new UserClientService(authClient, userClient);
    }

    @Test
    public void registerServerUser_userOk_returnUser(){
        val userDto =  RegisterUserDto.builder()
                .email("nuevo@mail.com")
                .enabled(true)
                .username("nuevo")
                .build();
        val user = AuthUserResponse.builder()
                .id(UUID.randomUUID())
                .build();

        doReturn(buildResponseCreated())
                .when(userClient).registerUser(any(RegisterUserDto.class));
        doReturn(Optional.of(user))
                .when(userClient).getUserFromEmail(anyString());

        val userResponse = userClientService.registerServerUser(userDto);

        assertNotNull(userResponse.getId());
    }
}
