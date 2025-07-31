package com.amit.multitool.repository.impl;

import com.amit.multitool.domain.model.Channel;
import com.amit.multitool.repository.ChannelRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public final class InMemoryChannelRepository implements ChannelRepository {

    private final Map<String, Channel> channelsStorage;

    public InMemoryChannelRepository() {
        this.channelsStorage = new HashMap<>();
    }

    @Override
    public Optional<Channel> findById(final String channelId) {
        return Optional.ofNullable(this.channelsStorage.get(channelId));
    }

    @Override
    public void saveAll(final Map<String, Channel> channels) {
        this.channelsStorage.putAll(channels);
    }

    @Override
    public Map<String, Channel> findAll() {
        return new HashMap<>(this.channelsStorage);
    }

    @Override
    public void deleteAll() {
        this.channelsStorage.clear();
    }

}
