package com.amit.multitool.mapper;

import com.amit.multitool.domain.model.Channel;
import com.amit.multitool.domain.model.Post;
import com.amit.multitool.domain.model.User;
import com.amit.multitool.domain.web.response.ChannelResponse;
import com.amit.multitool.domain.web.response.PostResponse;
import com.amit.multitool.domain.web.response.UserResponse;

public interface MattermostModelMapper {

    Post toPost(PostResponse postResponse);

    User toUser(UserResponse userResponse);

    Channel toChannel(ChannelResponse channelResponse);

}
