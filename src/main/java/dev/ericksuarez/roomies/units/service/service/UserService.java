package dev.ericksuarez.roomies.units.service.service;

import dev.ericksuarez.roomies.units.service.model.User;
import dev.ericksuarez.roomies.units.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersByUnitId(Long id) {
        return userRepository.getUsersByUnitId(id);
    }
}
