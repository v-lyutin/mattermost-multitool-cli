package com.amit.multitool.usecase;

import java.util.List;

public interface UploadDirectChannelMessagesUseCase {

    void createReportsOnDirectChannelMessages(String subjectEmail, List<String> recipientEmails, long startTimestamp, long endTimestamp);

}
