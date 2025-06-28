package com.amit.multitool.service.impl;

import com.amit.multitool.domain.model.User;
import com.amit.multitool.repository.UserRepository;
import com.amit.multitool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public final class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private DefaultUserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(final String userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void saveAll(final Map<String, User> users) {
        this.userRepository.saveAll(users);
    }

    @Override
    public Map<String, User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Set<User> findAllByIsMfaActive(final boolean isMfaActive) {
        return this.userRepository.findAllByIsMfaActive(isMfaActive);
    }

    @Override
    public void deleteAll() {
        this.userRepository.deleteAll();
    }

}
