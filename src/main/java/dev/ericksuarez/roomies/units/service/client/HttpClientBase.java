package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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
            HttpResponse<String> response = makeRequest(request);
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

    protected HttpResponse<String> makeRequest(HttpRequest request) throws IOException, InterruptedException {
        return  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * @param data
     * @return
     */
    protected HttpRequest.BodyPublisher formUrlEncodedData(Map<Object, Object> data) {
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

    /**
     * @param obj
     * @return
     */
    protected StringBuilder formJsonData(Object obj) {
        StringBuilder json = new StringBuilder().append("{");
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        char prefix = 0;
        for(int i = 0; i < fields.length; i++){
            fields[i].setAccessible(true);
            Object value = null;

            try {
                if (!fields[i].getType().isPrimitive() && !fields[i].getType().getName().contains("java.lang")){
                    value = formJsonData(fields[i].get(obj));
                } else {
                    value = fields[i].get(obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (value != null){
                json.append(prefix);
                prefix = ',';
                json.append(String.format("\"%s\":\"%s\"",fields[i].getName(), value));
            }

        }
        json.append("}");
        return json;
    }
}
