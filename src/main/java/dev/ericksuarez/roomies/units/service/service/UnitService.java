package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.repository.UnitRepository;
import dev.ericksuarez.roomies.units.service.util.ReferenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Slf4j
public class UnitService {

    private UnitRepository unitRepository;

    private ReferenceGenerator referenceGenerator;

    @Autowired
    public UnitService(UnitRepository unitRepository, ReferenceGenerator referenceGenerator) {
        this.unitRepository = unitRepository;
        this.referenceGenerator = referenceGenerator;
    }

    public Unit findUnit(Long id) {
        log.info("event=findUnitInvoked, id={}", id);
        return unitRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Unit not found"));
    }

    public Unit findUnitByReference(String reference) {
        log.info("event=findUnitByReferenceInvoke reference={}", reference);
        return unitRepository.findByReference(reference)
                .orElseThrow(()-> new RuntimeException("Unit not found"));
    }

    public Unit saveOrUpdateUnit(Unit unit) {
        log.info("event=saveOrUpdateUserInvoked, unit={}", unit);
        return unitRepository.save(unit);
    }

    public Unit setUnitReference(Long unitId, Map<String, String> update) {
        log.info("event=setUnitReferenceInvoked unitId={} update={}", unitId, update);
        return unitRepository.findById(unitId)
                .map(unit -> {
                    String reference = update.get("reference");
                    if (!StringUtils.isEmpty(reference)) {
                        unit.setReference(reference);
                        log.info("event=unitFound, unitId={}, unit={}", unitId, unit);
                        return unitRepository.save(unit);
                    }
                    throw new RuntimeException("UserUnsupportedFieldPatchException");
                })
                .orElseGet(() -> {
                    throw new RuntimeException("UnitNotFoundException");
                });
    }

    public ResponseEntity<?> deleteUnit(Long id) {
        log.info("event=deleteUserInvoked, id={}", id);
        return unitRepository.findById(id)
                .map(unit -> {
                    unit.setActive(false);
                    unitRepository.save(unit);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
