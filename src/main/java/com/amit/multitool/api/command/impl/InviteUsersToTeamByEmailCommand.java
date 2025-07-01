package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.usecase.InviteUsersToTeamByEmailUseCase;
import com.amit.multitool.utils.ConsoleInputUtils;
import com.amit.multitool.utils.FileReaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InviteUsersToTeamByEmailCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Invite users to the team by email";

    private static final String USER_EMAILS_FILE_PATH_REQUEST = "Specify the path to the file with user emails: ";

    private final InviteUsersToTeamByEmailUseCase inviteUsersToTeamByEmailUseCase;

    @Autowired
    public InviteUsersToTeamByEmailCommand(final InviteUsersToTeamByEmailUseCase inviteUsersToTeamByEmailUseCase) {
        this.inviteUsersToTeamByEmailUseCase = inviteUsersToTeamByEmailUseCase;
    }

    @Override
    public void execute() {
        final String emailsFilePath = ConsoleInputUtils.inputValue(USER_EMAILS_FILE_PATH_REQUEST);
        final List<String> emails = FileReaderUtils.readEmails(emailsFilePath);
        this.inviteUsersToTeamByEmailUseCase.inviteUsersToTeamByEmail(emails);
    }

    @Override
    public String getName() {
        return CommandOrder.INVITE_USERS_TO_TEAM_BY_EMAIL.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
