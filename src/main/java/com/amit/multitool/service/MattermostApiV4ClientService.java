package com.amit.multitool.service;

import com.amit.multitool.config.MattermostCredentials;
import com.amit.multitool.domain.web.request.PostRequest;
import com.amit.multitool.domain.web.response.*;

import java.util.Optional;
import java.util.Set;

public interface MattermostApiV4ClientService {

    void setMattermostCredentials(MattermostCredentials mattermostCredentials);

    Optional<ChannelPostsResponse> getChannelPosts(String channelId, Long since);

    Optional<Set<UserResponse>> getUsers(String teamId, int page);

    Optional<UserResponse> getMe();

    Optional<ChannelResponse> createDirectMessageChannel(String userId, String otherUserId);

    Optional<PostResponse> createPost(PostRequest postRequest);

    Optional<StatusResponse> deactivateUserAccount(String userId);

}
