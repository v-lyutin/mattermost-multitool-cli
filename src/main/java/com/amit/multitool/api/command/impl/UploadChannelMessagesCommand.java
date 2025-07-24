package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.usecase.UploadChannelMessagesUseCase;
import com.amit.multitool.utils.ConsoleInputUtils;
import com.amit.multitool.utils.DateTimeUtils;
import com.amit.multitool.utils.RequireMattermostCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadChannelMessagesCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Receive channel messages included in the time range";

    private static final String CHANNEL_ID_REQUEST_PROMPT = "Enter the channel ID: ";

    private static final String START_TIMESTAMP_REQUEST = "Enter the start date and time (yyyy-MM-dd HH:mm): ";

    private static final String END_TIMESTAMP_REQUEST = "Enter the end date and time (yyyy-MM-dd HH:mm): ";

    private final UploadChannelMessagesUseCase uploadChannelMessagesUseCase;

    @Autowired
    public UploadChannelMessagesCommand(final UploadChannelMessagesUseCase uploadChannelMessagesUseCase) {
        this.uploadChannelMessagesUseCase = uploadChannelMessagesUseCase;
    }

    @Override
    @RequireMattermostCredentials
    public void execute() {
        final String channelId = ConsoleInputUtils.inputValue(CHANNEL_ID_REQUEST_PROMPT);
        final long startTimestamp = DateTimeUtils.getDateTimeFromUser(START_TIMESTAMP_REQUEST);
        final long endTimestamp = DateTimeUtils.getDateTimeFromUser(END_TIMESTAMP_REQUEST);
        this.uploadChannelMessagesUseCase.createReportOnChannelMessages(channelId, startTimestamp, endTimestamp);
    }

    @Override
    public String getName() {
        return CommandOrder.UPLOAD_CHANNEL_MESSAGES_COMMAND.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
