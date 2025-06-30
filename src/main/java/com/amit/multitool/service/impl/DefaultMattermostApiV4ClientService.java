package com.amit.multitool.service.impl;

import com.amit.multitool.client.MattermostClient;
import com.amit.multitool.config.MattermostCredentials;
import com.amit.multitool.domain.web.request.PostRequest;
import com.amit.multitool.domain.web.response.*;
import com.amit.multitool.service.MattermostApiV4ClientService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public final class DefaultMattermostApiV4ClientService implements MattermostApiV4ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMattermostApiV4ClientService.class);

    private final MattermostClient mattermostClient;

    @Autowired
    public DefaultMattermostApiV4ClientService(final MattermostClient mattermostClient) {
        this.mattermostClient = mattermostClient;
    }

    @Override
    public void setMattermostCredentials(final MattermostCredentials mattermostCredentials) {
        this.mattermostClient.setMattermostCredentials(mattermostCredentials);
    }

    @Override
    public Optional<ChannelPostsResponse> getChannelPosts(final String channelId, final Long since) {
        return this.executeRequest(() -> this.mattermostClient.getApi().getChannelPosts(channelId, since));
    }

    @Override
    public Optional<Set<UserResponse>> getUsers(final String teamId, final int page) {
        return this.executeRequest(() -> this.mattermostClient.getApi().getUsers(teamId, page));
    }

    @Override
    public Optional<UserResponse> getMe() {
        return this.executeRequest(() -> this.mattermostClient.getApi().getMe());
    }

    @Override
    public Optional<ChannelResponse> createDirectMessageChannel(final String userId, final String otherUserId) {
        final List<String> userIds = List.of(userId, otherUserId);
        return this.executeRequest(() -> this.mattermostClient.getApi().createDirectMessageChannel(userIds));
    }

    @Override
    public Optional<PostResponse> createPost(final PostRequest postRequest) {
        return this.executeRequest(() -> this.mattermostClient.getApi().createPost(postRequest));
    }

    @Override
    public Optional<StatusResponse> deactivateUserAccount(final String userId) {
        return this.executeRequest(() -> this.mattermostClient.getApi().deactivateUserAccount(userId));
    }

    private <T> Optional<T> executeRequest(final Supplier<T> apiCall) {
        try {
            final T result = apiCall.get();
            return Optional.ofNullable(result);
        } catch (final FeignException exception) {
            LOGGER.error("Request error: {}", exception.getMessage());
            return Optional.empty();
        }
    }

    @FunctionalInterface
    private interface Supplier<T> {
        T get() throws FeignException;
    }

}
