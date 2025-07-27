package com.amit.multitool.usecase.impl;

import com.amit.multitool.domain.model.User;
import com.amit.multitool.domain.web.response.UserResponse;
import com.amit.multitool.service.DirectMessageSenderService;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.UserService;
import com.amit.multitool.usecase.SendOutMessagesToUsersWithoutMfaUseCase;
import com.amit.multitool.utils.BatchSchedulerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Consumer;

@Component
public final class DefaultSendOutMessagesToUsersWithoutMfaUseCase implements SendOutMessagesToUsersWithoutMfaUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSendOutMessagesToUsersWithoutMfaUseCase.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    private final DirectMessageSenderService directMessageSenderService;

    private final UserService userService;

    @Autowired
    public DefaultSendOutMessagesToUsersWithoutMfaUseCase(final MattermostApiV4ClientService mattermostApiClientService,
                                                          final DirectMessageSenderService directMessageSenderService,
                                                          final UserService userService) {
        this.mattermostApiV4ClientService = mattermostApiClientService;
        this.directMessageSenderService = directMessageSenderService;
        this.userService = userService;
    }

    @Override
    public void sendOutMessagesToUsersWithoutMfa(final String messageTemplate) {
        final Set<User> users = this.userService.findAllByIsMfaActive(false);
        if (users.isEmpty()) {
            LOGGER.info("There are no active users without MFA in the system");
            return;
        }
        this.mattermostApiV4ClientService.getMe().ifPresentOrElse(
                sender -> {
                    final Consumer<User> sendMessageConsumer = this.buildSendMessageConsumer(sender, messageTemplate);
                    BatchSchedulerUtils.scheduleBatch(users.stream().toList(), sendMessageConsumer);
                },
                () -> LOGGER.error("Your profile was not found")
        );
    }

    private Consumer<User> buildSendMessageConsumer(final UserResponse sender, final String messageTemplate) {
        return user -> {
            this.directMessageSenderService.sendMessageToUser(sender.id(), user, messageTemplate);
        };
    }

}
