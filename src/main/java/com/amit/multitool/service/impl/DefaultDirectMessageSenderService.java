package com.amit.multitool.service.impl;

import com.amit.multitool.domain.model.User;
import com.amit.multitool.domain.web.request.PostRequest;
import com.amit.multitool.domain.web.response.ChannelResponse;
import com.amit.multitool.domain.web.response.PostResponse;
import com.amit.multitool.service.DirectMessageSenderService;
import com.amit.multitool.service.MattermostApiV4ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class DefaultDirectMessageSenderService implements DirectMessageSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDirectMessageSenderService.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    @Autowired
    public DefaultDirectMessageSenderService(final MattermostApiV4ClientService mattermostApiV4ClientService) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
    }

    @Override
    public void sendMessageToUser(final String senderId, final User receiver, final String messageTemplate) {
        final Optional<ChannelResponse> channel = mattermostApiV4ClientService.createDirectMessageChannel(receiver.id(), senderId);
        if (channel.isEmpty()) {
            LOGGER.error("Failed to create DM channel for user: {}", receiver.email());
            return;
        }
        final Optional<PostResponse> postResponse = mattermostApiV4ClientService.createPost(new PostRequest(channel.get().id(), messageTemplate));
        if (postResponse.isPresent()) {
            LOGGER.info("Message sent to user: {}", receiver.email());
            return;
        }
        LOGGER.error("Failed to send message to user: {}", receiver.email());
    }

}
