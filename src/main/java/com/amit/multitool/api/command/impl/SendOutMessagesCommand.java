package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.usecase.SendOutMessagesUseCase;
import com.amit.multitool.utils.ConsoleInputUtils;
import com.amit.multitool.utils.FileReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class SendOutMessagesCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Send out messages";

    private static final String RECEIVER_EMAILS_FILE_PATH_REQUEST = "Specify the path to the file with receivers' emails: ";

    private static final String MESSAGE_TEMPLATE_FILE_PATH_REQUEST = "Specify the path to the message template file: ";

    private final SendOutMessagesUseCase sendOutMessagesUseCase;

    @Autowired
    public SendOutMessagesCommand(final SendOutMessagesUseCase sendOutMessagesUseCase) {
        this.sendOutMessagesUseCase = sendOutMessagesUseCase;
    }

    @Override
    public void execute() {
        final String receiverEmailsFilePath = ConsoleInputUtils.inputValue(RECEIVER_EMAILS_FILE_PATH_REQUEST);
        final String messageTemplateFilePath = ConsoleInputUtils.inputValue(MESSAGE_TEMPLATE_FILE_PATH_REQUEST);
        final List<String> receiverEmails = FileReaderUtils.readEmails(receiverEmailsFilePath);
        if (receiverEmails.isEmpty()) {
            return;
        }
        final String messageTemplate = FileReaderUtils.readMessageTemplate(messageTemplateFilePath);
        if (messageTemplate.isBlank()) {
            return;
        }
        this.sendOutMessagesUseCase.sendOutMessages(receiverEmails, messageTemplate);
    }

    @Override
    public String getName() {
        return CommandOrder.SEND_OUT_MESSAGES_COMMAND.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
