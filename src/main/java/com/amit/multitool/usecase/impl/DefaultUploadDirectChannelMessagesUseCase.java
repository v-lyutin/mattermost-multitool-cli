package com.amit.multitool.usecase.impl;

import com.amit.multitool.domain.model.Post;
import com.amit.multitool.domain.model.User;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.UserService;
import com.amit.multitool.usecase.ChannelMessagesUploader;
import com.amit.multitool.usecase.UploadDirectChannelMessagesUseCase;
import com.amit.multitool.utils.PostCsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public final class DefaultUploadDirectChannelMessagesUseCase implements UploadDirectChannelMessagesUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUploadDirectChannelMessagesUseCase.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    private final ChannelMessagesUploader channelMessagesUploader;

    private final UserService userService;

    @Autowired
    public DefaultUploadDirectChannelMessagesUseCase(final MattermostApiV4ClientService mattermostApiV4ClientService,
                                                     final ChannelMessagesUploader channelMessagesUploader,
                                                     final UserService userService) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
        this.channelMessagesUploader = channelMessagesUploader;
        this.userService = userService;
    }

    @Override
    public void createReportsOnDirectChannelMessages(final String subjectEmail,
                                                     final List<String> recipientEmails,
                                                     final long startTimestamp,
                                                     final long endTimestamp) {
        this.userService.findByEmail(subjectEmail)
                .ifPresentOrElse(
                        subject -> getRecipients(recipientEmails).forEach(recipient -> createChannelAndGenerateReport(subject, recipient, startTimestamp, endTimestamp)),
                        () -> LOGGER.error("Subject with email '{}' not found", subjectEmail)
                );
    }

    private List<User> getRecipients(final List<String> recipientEmails) {
        return recipientEmails.stream()
                .map(email -> {
                    final Optional<User> user = userService.findByEmail(email);
                    if (user.isEmpty()) {
                        LOGGER.warn("User with email '{}' not found", email);
                    }
                    return user;
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private void createChannelAndGenerateReport(final User subject,
                                                final User recipient,
                                                final long startTimestamp,
                                                final long endTimestamp) {
        this.mattermostApiV4ClientService.createDirectMessageChannel(subject.id(), recipient.id())
                .ifPresentOrElse(
                        channel -> {
                            final Set<Post> posts = this.channelMessagesUploader.uploadChannelMessagesOverTimeRange(channel.id(), startTimestamp, endTimestamp);
                            final String fileName = String.format("%s_%s", subject.username(), recipient.username());
                            PostCsvWriter.writePostsToCsv(posts, fileName);
                        },
                        () -> LOGGER.error("Error when creating a dialog with the user: {}", recipient.email())
                );
    }

}
