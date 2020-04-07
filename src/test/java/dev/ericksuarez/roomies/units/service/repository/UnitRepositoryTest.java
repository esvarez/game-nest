package dev.ericksuarez.roomies.units.service.repository;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UnitRepositoryTest {

    @Test
    public void testFail(){
        assertThat(true).isTrue();
    }
}
