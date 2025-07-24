package com.amit.multitool.usecase;

import com.amit.multitool.domain.model.User;
import com.amit.multitool.domain.web.request.PostRequest;
import com.amit.multitool.domain.web.response.ChannelResponse;
import com.amit.multitool.domain.web.response.PostResponse;
import com.amit.multitool.service.MattermostApiV4ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DirectMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectMessageSender.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    @Autowired
    public DirectMessageSender(final MattermostApiV4ClientService mattermostApiV4ClientService) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
    }

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
