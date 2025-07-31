package com.amit.multitool.service;

import com.amit.multitool.domain.model.Channel;

import java.util.Map;
import java.util.Optional;

public interface ChannelService {

    Optional<Channel> findById(String channelId);

    void saveAll(Map<String, Channel> channels);

    Map<String, Channel> findAll();

    void deleteAll();

}
