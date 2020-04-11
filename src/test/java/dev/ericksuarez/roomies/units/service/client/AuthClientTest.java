package dev.ericksuarez.roomies.units.service.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.http.HttpClient;
import java.util.HashMap;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
public class AuthClientTest {

    private AuthClient authClient;

    private HttpClient httpClient;

    private ObjectMapper objectMapper;

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
                .email("user3@mail.com")
                .enabled(true)
                .username("user3")
                .build();
        authClient.registerUser(user);
    }

    @Test
    public void doo() throws NoSuchFieldException, IllegalAccessException {
        Object user = RegisterUserDto.builder()
                .email("user3@mail.com")
                .enabled(true)
                .username("user3")
                .build();

        val obj = authClient.formJsonData(user);

        System.out.println(obj);

    }

    private HashMap<String, Object> getMemberFields(Object obj) throws IllegalAccessException,
            NoSuchFieldException
    {
        HashMap<String, Object> fieldValues = new HashMap<String, Object>();
        Class<?> objClass = obj.getClass();

        Field[] fields = objClass.getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            fieldValues.put(field.getName(), field.get(obj));
            if (!field.getType().isPrimitive() && !field.getType().getName().contains("java.lang"))
            {
                getMemberFields(field.get(obj));
            }
        }
        return fieldValues;
    }
}
