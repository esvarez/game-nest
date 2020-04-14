package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import dev.ericksuarez.roomies.units.service.model.responses.Credentials;
import dev.ericksuarez.roomies.units.service.model.responses.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class UserClient extends AuthClient {
    //@Value("${application.authServer.path}")
    private String path = "http://localhost:8083";

    //@Value("${app.auth-server.endpoint.register-ser}")
    private String endpointUser = "/auth/admin/realms/esuarez/users";

    @Autowired
    public UserClient(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient, objectMapper);
    }

    public void setToken(TokenResponse token){
        this.token = token;
    }

    public HttpResponse<String> registerUser(RegisterUserDto userDto) {
        String json = formJsonData(userDto.getUserRegister());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path + endpointUser))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("bearer %s", token.getAccessToken()))
                .build();

        HttpResponse<String> response =  makeSafeTokenRequest(request);
        if (response.statusCode() == 201){
            log.warn("event=userCreated response={}", response);
            return response;
            //return getUserFromEmail(userDto.getEmail());
        } else if (response.statusCode() == 409) {
            throw new RuntimeException("Credential Already exist");
        } else if (response.statusCode() >= 300) {
            log.warn("event=userNotCreated response={}, statusCode={}", response, response.statusCode());
            throw new RuntimeException("Error " + response.body());
        }
        return response;
    }

    public Optional<AuthUserResponse> getUserFromEmail(String email) {
        log.info("event=getUserFromEmailInvoked token={}, email={}", token.getAccessToken(), email);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(
                        String.format("%s?email=%s",
                                path + endpointUser, email)))
                .setHeader("Authorization", String.format("bearer %s", token.getAccessToken()))
                .build();
        log.info("event=requestAuthUserBuilt request={}", request);
        List<LinkedHashMap> authUser = makeSafeTokenRequest(request, List.class);
        log.info("event=userFound authUser={}", authUser);
        return (authUser.isEmpty())
                ? Optional.empty()
                : castLinkedHashMapToAuthUserResponse(authUser);
    }

    public void setPassword(UUID userId, Credentials credentials) {
        String json = formJsonData(credentials);
        log.info("event=setPasswordInvoked userId={} credentials={}", userId, credentials);

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(String.format("%s%s/%s/reset-password", path, endpointUser, userId.toString())))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("bearer %s", token.getAccessToken()))
                .build();

        HttpResponse<String> response = makeSafeTokenRequest(request);
        System.out.println(response);
    }

    private Optional<AuthUserResponse> castLinkedHashMapToAuthUserResponse (List<LinkedHashMap> object) {
        return object.stream()
                .map((hashMap -> (
                        AuthUserResponse.builder()
                                .id(UUID.fromString(hashMap.get("id").toString()))
                                .build())
                ))
                .findAny();
    }

}
