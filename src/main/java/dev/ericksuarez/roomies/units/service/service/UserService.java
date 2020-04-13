package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsersByUnitId(Long unitId) {
        log.info("event=getUsersByUnitIdInvokedService, unitId={}", unitId);
        return userRepository.getUsersByUnitId(unitId);
    }

    public User registerUser(User user) {
        log.info("event=registerUserInvoked, user={}", user);
        return userRepository.save(user);
    }
}
