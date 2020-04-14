package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.repository.UnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnitService {

    private UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public Unit findUnit(Long id) {
        log.info("event=findUnitInvoked, id={}", id);
        return unitRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Unit not found"));

    }

    public Unit saveOrUpdateUnit(Unit unit) {
        log.info("event=saveOrUpdateUserInvoked, unit={}", unit);
        return unitRepository.save(unit);
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
