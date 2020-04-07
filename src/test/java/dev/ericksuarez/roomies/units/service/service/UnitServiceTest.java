package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.repository.UnitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UnitServiceTest {

    @MockBean
    private UnitRepository unitRepository;

    @Test
    public void getUsersByUnitId_unitIdExist_returnUsers() {


    }
}
