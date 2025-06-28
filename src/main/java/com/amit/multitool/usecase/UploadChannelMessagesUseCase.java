package com.amit.multitool.usecase;

public interface UploadChannelMessagesUseCase {

    void createReportOnChannelMessages(String channelId, long startTimestamp, long endTimestamp);

}
