package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.usecase.SendOutMessagesToUsersWithoutMfaUseCase;
import com.amit.multitool.utils.ConsoleInputUtils;
import com.amit.multitool.utils.FileReaderUtils;
import com.amit.multitool.utils.RequireMattermostCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendOutMessagesToUsersWithoutMfaCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Send out messages to users without MFA";

    private static final String MESSAGE_TEMPLATE_FILE_PATH_REQUEST = "Specify the path to the message template file: ";

    private final SendOutMessagesToUsersWithoutMfaUseCase sendOutMessagesToUsersWithoutMfaUseCase;

    @Autowired
    public SendOutMessagesToUsersWithoutMfaCommand(final SendOutMessagesToUsersWithoutMfaUseCase sendOutMessagesToUsersWithoutMfaUseCase) {
        this.sendOutMessagesToUsersWithoutMfaUseCase = sendOutMessagesToUsersWithoutMfaUseCase;
    }

    @Override
    @RequireMattermostCredentials
    public void execute() {
        final String messageTemplateFilePath = ConsoleInputUtils.inputValue(MESSAGE_TEMPLATE_FILE_PATH_REQUEST);
        final String messageTemplate = FileReaderUtils.readMessageTemplate(messageTemplateFilePath);
        this.sendOutMessagesToUsersWithoutMfaUseCase.sendOutMessagesToUsersWithoutMfa(messageTemplate);
    }

    @Override
    public String getName() {
        return CommandOrder.SEND_OUT_MESSAGES_TO_USERS_WITHOUT_MFA_COMMAND.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
