package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.usecase.UploadDirectChannelMessagesUseCase;
import com.amit.multitool.utils.ConsoleInputUtils;
import com.amit.multitool.utils.DateTimeUtils;
import com.amit.multitool.utils.FileReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class UploadDirectChannelMessagesCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Receive direct channel messages included in the time range";

    private static final String SUBJECT_EMAIL_REQUEST = "Enter the subject's email: ";

    private static final String RECIPIENT_EMAILS_FILE_PATH_REQUEST = "Enter the path to the recipient emails file: ";

    private static final String START_TIMESTAMP_REQUEST = "Enter the start date and time (yyyy-MM-dd HH:mm): ";

    private static final String END_TIMESTAMP_REQUEST = "Enter the end date and time (yyyy-MM-dd HH:mm): ";

    private final UploadDirectChannelMessagesUseCase uploadDirectChannelMessagesUseCase;

    @Autowired
    public UploadDirectChannelMessagesCommand(final UploadDirectChannelMessagesUseCase uploadDirectChannelMessagesUseCase) {
        this.uploadDirectChannelMessagesUseCase = uploadDirectChannelMessagesUseCase;
    }

    @Override
    public void execute() {
        final String subjectEmail = ConsoleInputUtils.inputValue(SUBJECT_EMAIL_REQUEST);
        final String recipientEmailsFilePath = ConsoleInputUtils.inputValue(RECIPIENT_EMAILS_FILE_PATH_REQUEST);
        final List<String> recipientEmails = FileReaderUtils.readEmails(recipientEmailsFilePath);
        if (recipientEmails.isEmpty()) {
            return;
        }
        final long startTimestamp = DateTimeUtils.getDateTimeFromUser(START_TIMESTAMP_REQUEST);
        final long endTimestamp = DateTimeUtils.getDateTimeFromUser(END_TIMESTAMP_REQUEST);
        this.uploadDirectChannelMessagesUseCase.createReportsOnDirectChannelMessages(
                subjectEmail,
                recipientEmails,
                startTimestamp,
                endTimestamp
        );
    }

    @Override
    public String getName() {
        return CommandOrder.UPLOAD_DIRECT_CHANNEL_MESSAGES_COMMAND.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
