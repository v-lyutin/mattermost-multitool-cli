package com.amit.multitool.service;

import com.amit.multitool.domain.model.User;

public interface DirectMessageSenderService {

    void sendMessageToUser(String senderId, User receiver, String messageTemplate);

}
