package com.amit.multitool.service.impl;

import com.amit.multitool.client.MattermostClient;
import com.amit.multitool.config.MattermostCredentials;
import com.amit.multitool.domain.web.request.ChannelSearchRequest;
import com.amit.multitool.domain.web.request.EmailsRequest;
import com.amit.multitool.domain.web.request.PostRequest;
import com.amit.multitool.domain.web.response.*;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.RequestExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public final class DefaultMattermostApiV4ClientService implements MattermostApiV4ClientService {

    private final MattermostClient mattermostClient;

    private final RequestExecutorService requestExecutorService;

    @Autowired
    public DefaultMattermostApiV4ClientService(final MattermostClient mattermostClient, final RequestExecutorService requestExecutorService) {
        this.mattermostClient = mattermostClient;
        this.requestExecutorService = requestExecutorService;
    }

    @Override
    public void setMattermostCredentials(final MattermostCredentials mattermostCredentials) {
        this.mattermostClient.setMattermostCredentials(mattermostCredentials);
    }

    @Override
    public Optional<ChannelPostsResponse> getChannelPosts(final String channelId, final Long since) {
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().getChannelPosts(channelId, since));
    }

    @Override
    public Optional<Set<UserResponse>> getUsers(final String teamId, final int page) {
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().getUsers(teamId, page));
    }

    @Override
    public Optional<ChannelSearchResponse> searchChannels(final String teamId, final int page) {
        final ChannelSearchRequest channelSearchRequest = ChannelSearchRequest.getDefaultChannelSearchRequest(teamId, page);
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().searchChannels(channelSearchRequest));
    }

    @Override
    public Optional<UserResponse> getMe() {
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().getMe());
    }

    @Override
    public Optional<ChannelResponse> createDirectMessageChannel(final String userId, final String otherUserId) {
        final List<String> userIds = List.of(userId, otherUserId);
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().createDirectMessageChannel(userIds));
    }

    @Override
    public Optional<PostResponse> createPost(final PostRequest postRequest) {
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().createPost(postRequest));
    }

    @Override
    public Optional<StatusResponse> deactivateUserAccount(final String userId) {
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().deactivateUserAccount(userId));
    }

    @Override
    public Optional<StatusResponse> inviteUsersToTeamByEmail(final EmailsRequest emailsRequest) {
        final String teamId = this.mattermostClient.getTeamId();
        return this.requestExecutorService.executeRequest(() -> this.mattermostClient.getApi().inviteUsersToTeamByEmail(teamId, emailsRequest));
    }

}
