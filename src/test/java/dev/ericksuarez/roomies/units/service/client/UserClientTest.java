package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.responses.TokenResponse;
import dev.ericksuarez.roomies.units.service.model.responses.UserRegister;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static dev.ericksuarez.roomies.units.service.util.TestUtil.buildResponseCreated;
import static dev.ericksuarez.roomies.units.service.util.TestUtil.buildResponseOk;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;


@RunWith(MockitoJUnitRunner.class)
public class UserClientTest {
/*
    private UserClient userClient;

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void registerUser_userRegisterSuccess_returnCreatedStatus() throws IOException, InterruptedException {
        userClient = new UserClient(httpClient, objectMapper);
        val userDto =  RegisterUserDto.builder()
                        .userRegister(UserRegister.builder()
                            .email("nuevo@mail.com")
                            .enabled(true)
                            .username("nuevo")
                            .build())
                        .build();
        val token = TokenResponse.builder()
                .accessToken("Fake_Token")
                .build();
        //userClient.setToken(token);

        doReturn(buildResponseCreated())
                .when(httpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
        doReturn("{\"dummy\":\"Dummy data\"}")
                .when(objectMapper).writeValueAsString(any(Object.class));

        val response = userClient.registerUser(userDto);

        assertEquals(201, response.statusCode());
    }

    // TODO Test error

    @Test
    public void getUserFromEmail_emailExist_returnAuthUser() throws IOException, InterruptedException {
        userClient = new UserClient(httpClient, objectMapper);
        val token = TokenResponse.builder()
                .accessToken("Fake_Token")
                .build();
        //userClient.setToken(token);
        val hash = new LinkedHashMap<>();
            hash.put("id", UUID.randomUUID());
        val response = new ArrayList<>();
            response.add(hash);

        doReturn(buildResponseOk())
                .when(httpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
        doReturn(response)
                .when(objectMapper).readValue(buildResponseOk().body().getBytes(), List.class);

        val maybeUser = userClient.getUserFromEmail("test@email");
        val user = maybeUser.get();
        assert(maybeUser).isPresent();
        assertNotNull(user.getId());
    }
 */
}
