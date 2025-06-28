package com.amit.multitool.usecase.impl;

import com.amit.multitool.domain.model.Post;
import com.amit.multitool.usecase.ChannelMessagesUploader;
import com.amit.multitool.usecase.UploadChannelMessagesUseCase;
import com.amit.multitool.utils.PostCsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public final class DefaultUploadChannelMessagesUseCase implements UploadChannelMessagesUseCase {

    private final ChannelMessagesUploader channelMessagesUploader;

    @Autowired
    public DefaultUploadChannelMessagesUseCase(final ChannelMessagesUploader channelMessagesUploader) {
        this.channelMessagesUploader = channelMessagesUploader;
    }

    @Override
    public void createReportOnChannelMessages(final String channelId, final long startTimestamp, final long endTimestamp) {
        final Set<Post> posts = this.channelMessagesUploader.uploadChannelMessagesOverTimeRange(channelId, startTimestamp, endTimestamp);
        PostCsvWriter.writePostsToCsv(posts, channelId);
    }

}
