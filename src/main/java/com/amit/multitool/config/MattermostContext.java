package com.amit.multitool.config;

import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.UserUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class MattermostContext {

    private final MattermostApiV4ClientService mattermostApiService;

    private final UserUploaderService userUploaderService;


    @Autowired
    private MattermostContext(final MattermostApiV4ClientService mattermostApiService,
                              final UserUploaderService userUploaderService) {
        this.mattermostApiService = mattermostApiService;
        this.userUploaderService = userUploaderService;
    }

    public void applyState(final MattermostCredentials mattermostCredentials) {
        this.mattermostApiService.setMattermostCredentials(mattermostCredentials);
        this.userUploaderService.loadAllTeamUsers(mattermostCredentials.teamId());
    }

}
