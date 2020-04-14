package dev.ericksuarez.roomies.units.service.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUsersByUnitId_unitIdExist_returnUsers(){
        val users = userRepository.getUsersByUnitId(300L);
        assertThat(users).isNotNull();
        assertThat(users).hasSize(3);
    }

    @Test
    public void getUsersByUnitId_unitIdNotExist_returnEmptyList(){
        val users = userRepository.getUsersByUnitId(404L);
        assertThat(users).isEmpty();
    }

}
