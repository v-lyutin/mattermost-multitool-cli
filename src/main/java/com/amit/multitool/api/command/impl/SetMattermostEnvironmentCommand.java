package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import com.amit.multitool.config.MattermostContext;
import com.amit.multitool.config.MattermostCredentials;
import com.amit.multitool.utils.ConsoleInputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SetMattermostEnvironmentCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Set the Mattermost environment";

    private static final String BASE_URL_REQUEST = "Enter the base url: ";

    private static final String ACCESS_TOKEN_REQUEST = "Enter the access token: ";

    private static final String TEAM_ID_REQUEST = "Enter the team ID: ";

    private final MattermostContext mattermostContext;

    @Autowired
    public SetMattermostEnvironmentCommand(final MattermostContext mattermostContext) {
        this.mattermostContext = mattermostContext;
    }

    @Override
    public void execute() {
        final String baseUrl = requestBaseUrl();
        final String teamId = requestTeamId();
        final String accessToken = requestAccessToken();;
        final MattermostCredentials mattermostCredentials = new MattermostCredentials(baseUrl, teamId, accessToken);
        this.mattermostContext.applyState(mattermostCredentials);
    }

    @Override
    public String getName() {
        return CommandOrder.SET_MATTERMOST_ENVIRONMENT_COMMAND.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    private String requestBaseUrl() {
        return ConsoleInputUtils.inputValue(BASE_URL_REQUEST);
    }

    private String requestAccessToken() {
        return ConsoleInputUtils.inputValue(ACCESS_TOKEN_REQUEST);
    }

    private String requestTeamId() {
        return ConsoleInputUtils.inputValue(TEAM_ID_REQUEST);
    }

}
