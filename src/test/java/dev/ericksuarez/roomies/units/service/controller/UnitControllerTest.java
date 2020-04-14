package dev.ericksuarez.roomies.units.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.roomies.units.service.facade.UnitFacade;
import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.service.UnitService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static dev.ericksuarez.roomies.units.service.config.UnitsUri.API;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.UNITS;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.USERS;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UnitControllerTest {

    @MockBean
    private UnitFacade unitFacade;

    @MockBean
    private UnitService unitService;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper =  new ObjectMapper();

    @Test
    public void getUnitById_unitIdExist_returnUnit() throws Exception {

        when(unitService.findUnit(1L))
                .thenReturn(Unit.builder().id(1L).build());

        mockMvc.perform(get(String.format("%s%s/%d", API, UNITS, 1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    public void createUnit_unitOk_returnUnit() throws Exception {
        val unit = Unit.builder()
                .id(1L)
                .build();

        when(unitFacade.createUnit(any(UUID.class), any(Unit.class)))
                .thenReturn(unit);

        mockMvc.perform(post(String.format("%s%s/%s%s", API, USERS, UUID.randomUUID(), UNITS))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unit)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    public void updateUnit_uuidExist_returnUser() throws Exception {
        val unit = Unit.builder()
                .id(1L)
                .build();

        when(unitService.saveOrUpdateUnit(any(Unit.class)))
                .thenReturn(unit);

        mockMvc.perform(put(String.format("%s%s/%d", API, UNITS, 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    public void deleteUnit_userOk_returnUser() throws Exception {
        when(unitService.deleteUnit(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete(String.format("%s%s/%d", API, UNITS, 1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
