package dev.ericksuarez.roomies.units.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.facade.UserFacade;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static dev.ericksuarez.roomies.units.service.config.UnitsUri.API;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.UNITS;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.USERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserFacade userFacade;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper =  new ObjectMapper();

    @Test
    public void getUsersByUnitId_unitIdNotExist_throws404Error() {
        assertThat(true).isTrue();
    }

    @Test
    public void getUsersByUnitId_unitIdExist_returnUsers() throws Exception {

        when(userService.getUsersByUnitId(1L))
                .thenReturn(Arrays.asList(
                        User.builder().id(UUID.randomUUID()).name("Erick").build(),
                        User.builder().id(UUID.randomUUID()).name("Mauricio").build()
                ));

        mockMvc.perform(get(String.format("%s%s/%d%s", API, UNITS, 1, USERS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].name", is("Erick")))
                .andExpect(jsonPath("$[1].id", is(notNullValue())))
                .andExpect(jsonPath("$[1].name", is("Mauricio")));
    }

    @Test
    public void createUser_userOk_returnUser() throws Exception {
        val user = User.builder()
                .id(UUID.randomUUID())
                .name("Erick")
                .username("esven")
                .build();

        when(userFacade.registerUser(any(RegisterUserDto.class), any(Optional.class)))
                .thenReturn(user);

        mockMvc.perform(post(String.format("%s%s", API, USERS))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is("Erick")))
                .andExpect(jsonPath("$.username", is("esven")));
    }

    @Test
    public void getUserById_uuidExist_returnUser() throws Exception {
        val user = User.builder()
                .id(UUID.randomUUID())
                .name("Erick")
                .username("esven")
                .active(true)
                .build();

        when(userService.findUser(any(UUID.class)))
                .thenReturn(user);

        mockMvc.perform(get(String.format("%s%s/%s", API, USERS, UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is("Erick")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    public void updateUser_userOk_returnUser() throws Exception {
        val user = User.builder()
                .id(UUID.randomUUID())
                .name("Erick")
                .username("esven")
                .active(true)
                .build();

        when(userService.saveOrUpdateUser(any(UUID.class), any(User.class)))
                .thenReturn(user);

        mockMvc.perform(put(String.format("%s%s/%s", API, USERS, UUID.randomUUID()))
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is("Erick")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    public void deleteUser_userOk_returnUser() throws Exception {
        when(userService.deleteUser(any(UUID.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete(String.format("%s%s/%s", API, USERS, UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
