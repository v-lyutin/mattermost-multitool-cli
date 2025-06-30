package com.amit.multitool.client;

import com.amit.multitool.domain.web.request.PostRequest;
import com.amit.multitool.domain.web.response.*;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Set;

public interface MattermostApiV4 {

    @RequestLine(value = "GET /api/v4/channels/{channelId}/posts?since={since}")
    ChannelPostsResponse getChannelPosts(@Param(value = "channelId") String channelId, @Param(value = "since") Long since);

    @RequestLine(value = "GET /api/v4/users?in_team={teamId}&page={page}")
    Set<UserResponse> getUsers(@Param(value = "teamId") String teamId, @Param(value = "page") Integer page);

    @RequestLine(value = "GET /api/v4/users/me")
    UserResponse getMe();

    @RequestLine(value = "POST /api/v4/channels/direct")
    ChannelResponse createDirectMessageChannel(List<String> userIds);

    @RequestLine(value = "POST /api/v4/posts")
    PostResponse createPost(PostRequest postRequest);

    @RequestLine(value = "DELETE /api/v4/users/{userId}")
    StatusResponse deactivateUserAccount(@Param(value = "userId") String userId);

}
