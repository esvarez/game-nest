package dev.ericksuarez.roomies.units.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.facade.UnitFacade;
import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.repository.UnitRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UnitServiceTest {

    @Autowired
    private UnitService unitService;

    @MockBean
    private UnitRepository unitRepository;

    @Test
    public void findUnit_IdExist_returnUnit() {
        when(unitRepository.findById(anyLong()))
                .thenReturn(Optional.of(Unit.builder()
                        .id(1L)
                        .active(true)
                        .build()));

        val unit = unitService.findUnit(1L);

        assertThat(unit).isNotNull();
        assertThat(unit.getActive()).isTrue();
    }

    @Test
    public void saveOrUpdateUnit_unitOk_returnUnit() {
        when(unitRepository.save(any(Unit.class)))
                .thenReturn(Unit.builder()
                        .id(1L)
                        .active(true)
                        .build());

        val unit = unitService.saveOrUpdateUnit(Unit.builder().id(1L).active(true).build());

        assertThat(unit).isNotNull();
    }

    @Test
    public void deleteUnit_unitExist_ok() {
        when(unitRepository.findById(anyLong()))
                .thenReturn(Optional.of(Unit.builder().build()));

        ResponseEntity response = unitService.deleteUnit(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
