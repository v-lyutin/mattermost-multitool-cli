package com.amit.multitool.service.impl;

import com.amit.multitool.domain.model.Channel;
import com.amit.multitool.repository.ChannelRepository;
import com.amit.multitool.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public final class DefaultChannelService implements ChannelService {

    private final ChannelRepository channelRepository;

    @Autowired
    public DefaultChannelService(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Optional<Channel> findById(final String channelId) {
        return this.channelRepository.findById(channelId);
    }

    @Override
    public void saveAll(final Map<String, Channel> channels) {
        this.channelRepository.saveAll(channels);
    }

    @Override
    public Map<String, Channel> findAll() {
        return this.channelRepository.findAll();
    }

    @Override
    public void deleteAll() {
        this.channelRepository.deleteAll();
    }

}
