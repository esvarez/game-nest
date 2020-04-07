package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.User;
import dev.ericksuarez.roomies.units.service.repository.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void getUsersByUnitId_unitIdExist_returnUsers() {
        val unitId = 1L;

        Mockito.when(userRepository.getUsersByUnitId(unitId))
                .thenReturn(Arrays.asList(
                        User.builder().id(1L).build(),
                        User.builder().id(2L).build()
                ));
        val users = userService.getUsersByUnitId(unitId);

        assertThat(users).isNotNull();
        assertThat(users).hasSize(2);
    }
}
