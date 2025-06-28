package com.amit.multitool.usecase;

import java.util.List;

public interface SendOutMessagesUseCase {

    void sendOutMessages(List<String> receiverEmails, String messageTemplate);

}
