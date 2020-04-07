package dev.ericksuarez.roomies.units.service.repository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DataJpaTest
public class UnitRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFail(){
        assertThat(false).isTrue();
    }
}
