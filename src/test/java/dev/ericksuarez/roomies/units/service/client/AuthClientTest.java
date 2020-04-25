package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.model.responses.TokenResponse;
import lombok.val;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static dev.ericksuarez.roomies.units.service.util.TestUtil.buildResponseOk;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.Silent.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
public class AuthClientTest {
/*
    private AuthClient authClient;

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void generateToken_getTokenSuccess_returnToken() throws IOException, InterruptedException {
        authClient = new AuthClient(httpClient, objectMapper);
        val token = TokenResponse.builder()
                .accessToken("Fake_Token")
                .build();

        doReturn(buildResponseOk())
                .when(httpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
        doReturn(token)
                .when(objectMapper).readValue("fakeString".getBytes(), TokenResponse.class);
        authClient.generateToken();
    }
*/
}
