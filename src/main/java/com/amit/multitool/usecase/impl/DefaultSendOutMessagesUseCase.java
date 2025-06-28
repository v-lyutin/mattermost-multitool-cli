package com.amit.multitool.usecase.impl;

import com.amit.multitool.domain.web.response.UserResponse;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.UserService;
import com.amit.multitool.usecase.DirectMessageSender;
import com.amit.multitool.usecase.SendOutMessagesUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class DefaultSendOutMessagesUseCase implements SendOutMessagesUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSendOutMessagesUseCase.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    private final DirectMessageSender directMessageSender;

    private final UserService userService;

    @Autowired
    public DefaultSendOutMessagesUseCase(final MattermostApiV4ClientService mattermostApiV4ClientService,
                                         final DirectMessageSender directMessageSender,
                                         final UserService userService) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
        this.directMessageSender = directMessageSender;
        this.userService = userService;
    }

    @Override
    public void sendOutMessages(final List<String> receiverEmails, final String messageTemplate) {
        this.mattermostApiV4ClientService.getMe()
                .ifPresentOrElse(
                        sender -> this.processReceivers(receiverEmails, messageTemplate, sender),
                        () -> LOGGER.error("Your profile was not found")
                );
    }

    private void processReceivers(final List<String> receiverEmails, final String messageTemplate, final UserResponse sender) {
        receiverEmails.stream()
                .map(userService::findByEmail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(receiver -> this.directMessageSender.sendMessageToUser(sender.id(), receiver, messageTemplate));
    }

}
