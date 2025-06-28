package com.amit.multitool.config;

import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.usecase.LoadAllTeamUsersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class MattermostContext {

    private final MattermostApiV4ClientService mattermostApiService;

    private final LoadAllTeamUsersUseCase loadAllTeamUsersUseCase;


    @Autowired
    private MattermostContext(final MattermostApiV4ClientService mattermostApiService,
                              final LoadAllTeamUsersUseCase loadAllTeamUsersUseCase) {
        this.mattermostApiService = mattermostApiService;
        this.loadAllTeamUsersUseCase = loadAllTeamUsersUseCase;
    }

    public void applyState(final MattermostCredentials mattermostCredentials) {
        this.mattermostApiService.setMattermostCredentials(mattermostCredentials);
        this.loadAllTeamUsersUseCase.loadAllTeamUsers(mattermostCredentials.teamId());
    }

}
