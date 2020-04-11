package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.net.http.HttpClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
public class AuthClientTest {

    private static AuthClient authClient;

    private static HttpClient httpClient;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public void setUp(){
        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        this.authClient = new AuthClient(httpClient, objectMapper);
    }

    @Test
    public void foo() throws IOException, InterruptedException {
        //authClient.registerUser();

        RegisterUserDto user = RegisterUserDto.builder()
                .email("email@mail.com")
                .enabled(true)
                .username("newUsername")
                .build();
        authClient.registerUser(user);
    }
}
