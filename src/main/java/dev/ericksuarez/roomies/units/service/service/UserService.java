package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.User;
import dev.ericksuarez.roomies.units.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersByUnitId(Long unitId) {
        log.info("event=getUsersByUnitIdInvokedService, unitId={}", unitId);
        return userRepository.getUsersByUnitId(unitId);
    }
}
