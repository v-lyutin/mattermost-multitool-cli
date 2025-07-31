package com.amit.multitool.service.impl;

import com.amit.multitool.domain.model.Channel;
import com.amit.multitool.domain.web.response.ChannelResponse;
import com.amit.multitool.domain.web.response.ChannelSearchResponse;
import com.amit.multitool.mapper.MattermostModelMapper;
import com.amit.multitool.service.ChannelService;
import com.amit.multitool.service.ChannelUploaderService;
import com.amit.multitool.service.MattermostApiV4ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public final class DefaultChannelUploaderService implements ChannelUploaderService {

    private static final int START_PAGE = 0;

    private static final int MAX_PAGE = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultChannelUploaderService.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    private final ChannelService channelService;

    private final MattermostModelMapper mattermostModelMapper;

    @Autowired
    public DefaultChannelUploaderService(final MattermostApiV4ClientService mattermostApiV4ClientService,
                                         final ChannelService channelService,
                                         final MattermostModelMapper mattermostModelMapper) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
        this.channelService = channelService;
        this.mattermostModelMapper = mattermostModelMapper;
    }

    @Override
    public void loadAllTeamChannels(final String teamId) {
        this.channelService.deleteAll();
        final Map<String, Channel> loadedChannels = this.fetchAllChannels(teamId);
        if (!loadedChannels.isEmpty()) {
            this.channelService.saveAll(loadedChannels);
            LOGGER.info("The number of uploaded channels in the context: {}", loadedChannels.size());
        } else {
            LOGGER.warn("No channels found for team {}", teamId);
        }
    }

    private Map<String, Channel> fetchAllChannels(final String teamId) {
        final Map<String, Channel> loadedChannels = new HashMap<>();
        for (int page = START_PAGE; page < MAX_PAGE; page++) {
            final List<ChannelResponse> responses = this.mattermostApiV4ClientService.searchChannels(teamId, page)
                    .map(ChannelSearchResponse::channels)
                    .orElse(List.of());
            if (responses.isEmpty()) {
                break;
            }
            final Map<String, Channel> channels = responses.stream()
                    .map(mattermostModelMapper::toChannel)
                    .collect(Collectors.toMap(Channel::id, Function.identity()));
            loadedChannels.putAll(channels);
        }
        return loadedChannels;
    }

}
