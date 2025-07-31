package com.amit.multitool.config;

import com.amit.multitool.service.ChannelUploaderService;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.UserUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class MattermostContext {

    private final MattermostApiV4ClientService mattermostApiService;

    private final UserUploaderService userUploaderService;

    private final ChannelUploaderService channelUploaderService;

    @Autowired
    private MattermostContext(final MattermostApiV4ClientService mattermostApiService,
                              final UserUploaderService userUploaderService,
                              final ChannelUploaderService channelUploaderService) {
        this.mattermostApiService = mattermostApiService;
        this.userUploaderService = userUploaderService;
        this.channelUploaderService = channelUploaderService;
    }

    public void applyState(final MattermostCredentials mattermostCredentials) {
        final String teamId = mattermostCredentials.teamId();
        this.mattermostApiService.setMattermostCredentials(mattermostCredentials);
        this.userUploaderService.loadAllTeamUsers(teamId);
        this.channelUploaderService.loadAllTeamChannels(teamId);
    }

}
