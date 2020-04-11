package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.model.responses.Test;
import dev.ericksuarez.roomies.units.service.model.responses.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class HttpClientBase {

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    @Autowired
    public HttpClientBase(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //HttpClient.newBuilder()                .version(HttpClient.Version.HTTP_2)                .build();
    }

    protected <T> T makeRequest(HttpRequest request, Class<T> tClass) {
        T responseEntity;
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 300){
                log.error("Error");
            }
            return objectMapper.readValue(response.body().getBytes(), tClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO Exception");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
    }


    //Trash to test
    //protected void makeRequest(HttpRequest request) {
    protected void makeRequest() throws IOException, InterruptedException {
        /*
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://httpbin.org/get"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();
        */
        Map<Object, Object> data = new HashMap<>();
        data.put("username", "admin");
        data.put("password", "pass");
        data.put("grant_type", "password");
        data.put("client_id", "admin-cli");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/auth/realms/master/protocol/openid-connect/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")

                //.setHeader("content-type", "application/json")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TokenResponse a = objectMapper.readValue(response.body().getBytes(), TokenResponse.class);

        System.out.println("Done");
    }

    private boolean isOkResponse(int responseCode){
        return responseCode >= 200 && responseCode < 300;
    }




}
