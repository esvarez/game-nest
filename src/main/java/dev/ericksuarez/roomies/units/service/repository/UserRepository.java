package dev.ericksuarez.roomies.units.service.repository;

import dev.ericksuarez.roomies.units.service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

/*    @Query("SELECT u FROM User u WHERE u.unit.id = :unitId")
    List<User> getUsersByUnitId(@Param("unitId") Long unitId);*/

    @Query("SELECT u FROM User u JOIN FETCH u.unit WHERE u.unit.id = :unitId AND u.active = TRUE")
    List<User> getUsersByUnitId(@Param("unitId") Long unitId);

}
