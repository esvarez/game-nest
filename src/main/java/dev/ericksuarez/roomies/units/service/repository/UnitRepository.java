package dev.ericksuarez.roomies.units.service.repository;

import dev.ericksuarez.roomies.units.service.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {

}
