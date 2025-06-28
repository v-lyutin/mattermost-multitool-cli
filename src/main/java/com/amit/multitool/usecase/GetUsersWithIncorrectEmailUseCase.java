package com.amit.multitool.usecase;

import com.amit.multitool.domain.model.User;

import java.util.Set;

public interface GetUsersWithIncorrectEmailUseCase {

    Set<User> getUsersWithIncorrectEmail();

}
