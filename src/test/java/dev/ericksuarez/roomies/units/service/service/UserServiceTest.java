package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.repository.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getUsersByUnitId_unitIdExist_returnUsers() {
        val unitId = 1L;

        when(userRepository.getUsersByUnitId(anyLong()))
                .thenReturn(Arrays.asList(
                        User.builder().id(UUID.randomUUID()).build(),
                        User.builder().id(UUID.randomUUID()).build()
                ));

        val users = userService.getUsersByUnitId(unitId);

        assertThat(users).isNotNull();
        assertThat(users).hasSize(2);
    }

    @Test
    public void findUser_uuidExist_returnUser() {
        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(User.builder().id(UUID.randomUUID()).username("").build()));

        val user = userService.findUser(UUID.randomUUID());
        assertThat(user).isNotNull();
    }

    @Test
    public void findUser_uuidNotExist_returnUser() {
        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            userService.findUser(UUID.randomUUID());
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    public void registerUser_userOk_returnUser() {
        val user = User.builder()
                .id(UUID.randomUUID())
                .username("")
                .active(true)
                .build();
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        val userSaved = userService.registerUser(user);

        assertThat(userSaved).isNotNull();
        assertThat(userSaved.isActive()).isTrue();
    }

    @Test
    public void saveOrUpdateUser_userOk_returnUser() {
        val user = User.builder()
                .id(UUID.randomUUID())
                .username("")
                .active(true)
                .build();
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        val userSaved = userService.saveOrUpdateUser(UUID.randomUUID() ,user);

        assertThat(userSaved).isNotNull();
        assertThat(userSaved.isActive()).isTrue();
    }

    @Test
    public void deleteAccount_idExist_ok(){
        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(User.builder().build()));

        ResponseEntity response = userService.deleteUser(UUID.randomUUID());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
