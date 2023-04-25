package com.example.moneyTracker.service;

import com.example.moneyTracker.model.User;
import com.example.moneyTracker.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(AccountService.class);
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        user = userRepository.save(user);
        logger.debug("Creating new user with ID: {}", user.getId());
        return user;
    }

    public void deleteUser(Long id) {
        logger.debug("User with ID: {} deleted", id);
        userRepository.deleteById(id);
    }
}
