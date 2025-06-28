package com.amit.multitool.usecase.impl;

import com.amit.multitool.domain.model.User;
import com.amit.multitool.service.UserService;
import com.amit.multitool.usecase.GetUsersWithIncorrectEmailUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class DefaultGetUsersWithIncorrectEmailUseCase implements GetUsersWithIncorrectEmailUseCase {

    private final UserService userService;

    @Autowired
    public DefaultGetUsersWithIncorrectEmailUseCase(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Set<User> getUsersWithIncorrectEmail() {
        return this.userService.findAll().values().stream()
                .filter(user -> user.isActive() && user.isMfaActive() && !user.isUsernameCorrect())
                .collect(Collectors.toSet());
    }

}
