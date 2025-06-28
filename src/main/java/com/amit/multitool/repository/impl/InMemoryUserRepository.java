package com.amit.multitool.repository.impl;

import com.amit.multitool.domain.model.User;
import com.amit.multitool.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public final class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> usersStorage;

    public InMemoryUserRepository() {
        this.usersStorage = new HashMap<>();
    }

    @Override
    public Optional<User> findById(final String userId) {
        return Optional.ofNullable(this.usersStorage.get(userId));
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return this.usersStorage.values().stream()
                .filter(user -> user.email().equals(email))
                .findFirst();
    }

    @Override
    public void saveAll(final Map<String, User> users) {
        this.usersStorage.putAll(users);
    }

    @Override
    public Map<String, User> findAll() {
        return new HashMap<>(this.usersStorage);
    }

    @Override
    public Set<User> findAllByIsMfaActive(final boolean isMfaActive) {
        return this.usersStorage.values().stream()
                .filter(user -> !user.isServiceAccount() && user.isMfaActive() == isMfaActive && user.isActive())
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteAll() {
        this.usersStorage.clear();
    }

}
