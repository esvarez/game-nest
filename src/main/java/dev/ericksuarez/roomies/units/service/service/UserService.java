package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        List<User> users = userRepository.getUsersByUnitId(unitId);
        log.info("event=usersByUnitIdReturned, users={}", users);
        return users;
    }

    public User findUser(UUID uuid) {
        log.info("event=registerUserInvoked, user={}", uuid);
        return userRepository.findById(uuid)
                .orElseThrow(()-> new RuntimeException("User not found"));

    }

    public User registerUser(User user) {
        log.info("event=registerUserInvoked, user={}", user);
        return userRepository.save(user);
    }

    public User saveOrUpdateUser(UUID uuid, User user) {
        log.info("event=saveOrUpdateUserInvoked, uuid={},user={}", uuid, user);
        user.setId(uuid);
        return userRepository.save(user);
    }

    public ResponseEntity<?> deleteUser(UUID uuid) {
        log.info("event=deleteUserInvoked, uuid={}", uuid);
        return userRepository.findById(uuid)
                .map(user -> {
                    user.setActive(false);
                    userRepository.save(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
