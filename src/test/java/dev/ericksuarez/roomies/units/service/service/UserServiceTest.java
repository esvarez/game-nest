package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.repository.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void getUsersByUnitId_unitIdExist_returnUsers() {
        val unitId = 1L;

        when(userRepository.getUsersByUnitId(unitId))
                .thenReturn(Arrays.asList(
                        User.builder().id(UUID.randomUUID()).build(),
                        User.builder().id(UUID.randomUUID()).build()
                ));
        val users = userService.getUsersByUnitId(unitId);

        assertThat(users).isNotNull();
        assertThat(users).hasSize(2);
    }
}
