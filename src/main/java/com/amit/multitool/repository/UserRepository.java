package com.amit.multitool.repository;

import com.amit.multitool.domain.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);

    void saveAll(Map<String, User> users);

    Map<String, User> findAll();

    Set<User> findAllByIsMfaActive(boolean isMfaActive);

    void deleteAll();

}
