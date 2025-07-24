package com.amit.multitool.usecase.impl;

import com.amit.multitool.domain.web.response.StatusResponse;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.UserService;
import com.amit.multitool.usecase.DeactivateUserAccountsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class DefaultDeactivateUserAccountsUseCase implements DeactivateUserAccountsUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDeactivateUserAccountsUseCase.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    private final UserService userService;

    @Autowired
    public DefaultDeactivateUserAccountsUseCase(final MattermostApiV4ClientService mattermostApiV4ClientService,
                                                final UserService userService) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
        this.userService = userService;
    }


    @Override
    public void deactivateUserAccounts(final List<String> emails) {
        emails.forEach(email -> {
            this.userService.findByEmail(email)
                    .ifPresentOrElse(
                            user -> {
                                final Optional<StatusResponse> statusResponse = this.mattermostApiV4ClientService.deactivateUserAccount(user.id());
                                statusResponse.ifPresentOrElse(
                                        status -> LOGGER.info("User with email '{}' deactivated with status: {}", email, status.status()),
                                        () -> LOGGER.warn("Failed to deactivate user with email '{}'", email)
                                );
                            },
                            () -> LOGGER.warn("User with email '{}' not found", email)
                    );
        });
    }

}
