package com.amit.multitool.service;

import com.amit.multitool.domain.model.Post;

import java.util.Set;

public interface ChannelMessageUploaderService {

    Set<Post> uploadChannelMessagesOverTimeRange(String channelId, long startTimestamp, long endTimestamp);

}
