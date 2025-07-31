package com.amit.multitool.service.impl;

import com.amit.multitool.domain.model.User;
import com.amit.multitool.domain.web.response.UserResponse;
import com.amit.multitool.mapper.MattermostModelMapper;
import com.amit.multitool.service.MattermostApiV4ClientService;
import com.amit.multitool.service.UserUploaderService;
import com.amit.multitool.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class DefaultUserUploaderService implements UserUploaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserUploaderService.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    private final UserService userService;

    private final MattermostModelMapper mattermostModelMapper;

    @Autowired
    public DefaultUserUploaderService(final MattermostApiV4ClientService mattermostApiClientService,
                                      final UserService userService,
                                      final MattermostModelMapper mattermostModelMapper) {
        this.mattermostApiV4ClientService = mattermostApiClientService;
        this.userService = userService;
        this.mattermostModelMapper = mattermostModelMapper;
    }

    @Override
    public void loadAllTeamUsers(final String teamId) {
        this.userService.deleteAll();
        final int maxPages = 50;
        int page = 0;
        final Map<String, User> loadedUsers = new HashMap<>();
        while (page < maxPages) {
            final Optional<Set<UserResponse>> users = this.mattermostApiV4ClientService.getUsers(teamId, page);
            if (users.isEmpty()) {
                break;
            }
            users.get().stream()
                    .map(mattermostModelMapper::toUser)
                    .forEach(user -> loadedUsers.put(user.id(), user));
            page++;
        }
        if (!loadedUsers.isEmpty()) {
            this.userService.saveAll(loadedUsers);
            LOGGER.info("The number of uploaded users in the context: {}", loadedUsers.size());
        }
    }

}
