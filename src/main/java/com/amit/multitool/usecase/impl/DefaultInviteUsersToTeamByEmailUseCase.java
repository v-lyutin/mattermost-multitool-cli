package com.amit.multitool.usecase.impl;

import com.amit.multitool.domain.web.request.EmailsRequest;
import com.amit.multitool.domain.web.response.StatusResponse;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.usecase.InviteUsersToTeamByEmailUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultInviteUsersToTeamByEmailUseCase implements InviteUsersToTeamByEmailUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInviteUsersToTeamByEmailUseCase.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    @Autowired
    public DefaultInviteUsersToTeamByEmailUseCase(final MattermostApiV4ClientService mattermostApiV4ClientService) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
    }

    @Override
    public void inviteUsersToTeamByEmail(final List<String> emails) {
        if (emails == null || emails.isEmpty()) {
            LOGGER.info("No emails provided for team invitation");
            return;
        }
        emails.forEach(email -> {
                final EmailsRequest emailsRequest = new EmailsRequest(List.of(email));
                final Optional<StatusResponse> statusResponse = mattermostApiV4ClientService.inviteUsersToTeamByEmail(emailsRequest);
                statusResponse.ifPresentOrElse(
                    response -> LOGGER.info("Successfully invited '{}' to team with status: {}", email, response.status()),
                    () -> LOGGER.warn("Failed to invite user with email '{}' to team", email)
            );
        });
    }

}
