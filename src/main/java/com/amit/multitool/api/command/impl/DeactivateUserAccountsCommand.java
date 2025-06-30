package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.usecase.DeactivateUserAccountsUseCase;
import com.amit.multitool.utils.ConsoleInputUtils;
import com.amit.multitool.utils.FileReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class DeactivateUserAccountsCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Deactivate user accounts";

    private static final String USER_EMAILS_FILE_PATH_REQUEST = "Specify the path to the file with user emails: ";

    private final DeactivateUserAccountsUseCase deactivateUserAccountsUseCase;

    @Autowired
    public DeactivateUserAccountsCommand(final DeactivateUserAccountsUseCase deactivateUserAccountsUseCase) {
        this.deactivateUserAccountsUseCase = deactivateUserAccountsUseCase;
    }

    @Override
    public void execute() {
        final String receiverEmailsFilePath = ConsoleInputUtils.inputValue(USER_EMAILS_FILE_PATH_REQUEST);
        final List<String> emails = FileReaderUtils.readEmails(receiverEmailsFilePath);
        if (emails.isEmpty()) {
            return;
        }
        this.deactivateUserAccountsUseCase.deactivateUserAccounts(emails);
    }

    @Override
    public String getName() {
        return CommandOrder.DEACTIVATE_USER_ACCOUNTS.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
