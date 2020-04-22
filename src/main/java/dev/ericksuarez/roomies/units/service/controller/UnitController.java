package dev.ericksuarez.roomies.units.service.controller;

import dev.ericksuarez.roomies.units.service.facade.UnitFacade;
import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.UUID;

import static dev.ericksuarez.roomies.units.service.config.UnitsUri.API;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.UNITS;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.USERS;

@Slf4j
@RestController
@RequestMapping(API)
public class UnitController {

    private UnitFacade unitFacade;

    private UnitService unitService;

    @Autowired
    public UnitController(UnitFacade unitFacade, UnitService unitService) {
        this.unitFacade = unitFacade;
        this.unitService = unitService;
    }

    @GetMapping(UNITS + "/{unitId}")
    public Unit getUnitById(@PathVariable("unitId") Long unitId) {
        log.info("event=getUnitByIdInvoked, unitId={}", unitId);
        return unitService.findUnit(unitId);
    }

    @PostMapping(USERS + "/{uuid}" +UNITS)
    @ResponseStatus(HttpStatus.CREATED)
    public Unit createUnit(@PathVariable("uuid") UUID uuid,
                           @Valid @RequestBody Unit unit){
        log.info("event=createUnitInvoked, unit={}", unit);
        return unitFacade.createUnit(uuid, unit);
    }

    @PutMapping(UNITS + "/{unitId}")
    public Unit updateUnit(@PathVariable("unitId") Long unitId,
                           @Valid @RequestBody Unit unit) {
        log.info("event=updateUnitInvoked unitId={}, unit={}", unitId, unit);
        return unitService.saveOrUpdateUnit(unit);
    }

    @PatchMapping(USERS + "/{userId}" + UNITS + "/{unitId}")
    public Unit createReference(@PathVariable("userId") UUID userId,
                             @PathVariable("unitId") @Min(1) Long unitId){
        log.info("event=patchReferenceInvoked userId={}, unitId={}, unit={}", userId, unitId);
        return unitFacade.createUnitReference(userId, unitId);
    }

    @DeleteMapping(UNITS + "/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable("unitId") Long unitId) {
        log.info("event=deleteUnitInvoked unitId={}", unitId);
        return unitService.deleteUnit(unitId);
    }

}
