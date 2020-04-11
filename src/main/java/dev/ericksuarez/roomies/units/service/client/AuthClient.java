package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.responses.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class AuthClient extends HttpClientBase {

    //@Value("${application.authServer.path}")
    private String path = "http://localhost:8083";

    //@Value("${app.auth-server.endpoint.auth}")
    private String endpointAuth = "/auth/realms/master/protocol/openid-connect/token";

    //@Value("${app.auth-server.endpoint.register-ser}")
    private String endpointRegisterUser = "/auth/admin/realms/esuarez/users";

    //@Value("${app.auth-server.user}")
    private String user = "admin";

    //@Value("${app.auth-server.password}")
    private String password = "pass";

    public AuthClient(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient, objectMapper);
    }

    public void registerUser(RegisterUserDto userDto) {
        String json = new StringBuilder()
                .append("{")
                .append(String.format("\"email\":\"%s\",", userDto.getEmail()))
                .append(String.format("\"enabled\":\"%s\",", userDto.isEnabled()))
                .append(String.format("\"username\":\"%s\"", userDto.getUsername()))
                .append("}").toString();
        //data.put("firstName", userDto.getFirstName());

        TokenResponse token = getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path + endpointRegisterUser))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("bearer %s", token.getAccessToken()))
                .build();

        makeRequest(request, TokenResponse.class);

    }

    private TokenResponse getToken(){
        Map<Object, Object> data = new HashMap<>();

        data.put("username", user);
        data.put("password", password);
        data.put("grant_type", "password");
        data.put("client_id", "admin-cli");

        HttpRequest request = buildAuthRequest(data);

        return makeRequest(request, TokenResponse.class);
    }

    private TokenResponse getTokenFromRefresh(TokenResponse token){
        Map<Object, Object> data = new HashMap<>();

        data.put("grant_type", "refresh_token");
        data.put("client_id", "admin-cli");
        data.put("refresh_token", token.getRefreshToken());

        HttpRequest request = buildAuthRequest(data);

        return makeRequest(request, TokenResponse.class);
    }

    private HttpRequest.BodyPublisher formData(Map<Object, Object> data) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }

        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private HttpRequest buildAuthRequest(Map<Object, Object> data){
        System.out.println((URI.create(path + endpointAuth)));
        return HttpRequest.newBuilder()
                .uri(URI.create(path + endpointAuth))
                .POST(formData(data))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }

}
