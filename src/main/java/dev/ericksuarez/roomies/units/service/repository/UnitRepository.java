package dev.ericksuarez.roomies.units.service.repository;

import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByReference(String reference);
}
