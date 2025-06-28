package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.domain.model.User;
import com.amit.multitool.usecase.GetUsersWithIncorrectEmailUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public final class GetUsersWithIncorrectEmailCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Get users with an incorrect username";

    private final GetUsersWithIncorrectEmailUseCase getUsersWithIncorrectEmailUseCase;

    @Autowired
    public GetUsersWithIncorrectEmailCommand(final GetUsersWithIncorrectEmailUseCase getUsersWithIncorrectEmailUseCase) {
        this.getUsersWithIncorrectEmailUseCase = getUsersWithIncorrectEmailUseCase;
    }

    @Override
    public void execute() {
        final Set<User> users = this.getUsersWithIncorrectEmailUseCase.getUsersWithIncorrectEmail();
        users.forEach(user -> System.out.printf("%s - %s%n", user.email(), user.username()));
    }

    @Override
    public String getName() {
        return CommandOrder.GET_USERS_WITH_INCORRECT_EMAIL_COMMAND.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
