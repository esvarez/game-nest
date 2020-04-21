package dev.ericksuarez.roomies.units.service.integration.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.client.AuthClient;
import dev.ericksuarez.roomies.units.service.client.UserClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpClient;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
public class AuthServerTest {

    private AuthClient authClient;

    private UserClient userClient;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void tokenConnection() {
        userClient = new UserClient(httpClient, objectMapper);
        authClient = new AuthClient(httpClient, objectMapper);

        assertNull(userClient.getToken());
        assertNull(authClient.getToken());

        authClient.generateToken();

        assertNotNull(authClient.getToken());
        //userClient.setToken(authClient.getToken());
        assertNotNull(userClient.getToken());

    }
}
