package dev.ericksuarez.roomies.units.service.repository;

import dev.ericksuarez.roomies.units.service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.unit.id = :unitId")
    List<User> getUsersByUnitId(@Param("unitId") Long unitId);

}
