package com.amit.multitool.mapper.impl;

import com.amit.multitool.domain.model.Post;
import com.amit.multitool.domain.model.User;
import com.amit.multitool.domain.web.response.PostResponse;
import com.amit.multitool.domain.web.response.UserResponse;
import com.amit.multitool.mapper.MattermostModelMapper;
import com.amit.multitool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class DefaultMattermostModelMapper implements MattermostModelMapper {

    private final UserService userService;

    @Autowired
    public DefaultMattermostModelMapper(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public Post toPost(final PostResponse postResponse) {
        final Optional<User> user = userService.findById(postResponse.userId());
        return new Post(
                postResponse.id(),
                postResponse.createAt(),
                postResponse.rootId(),
                user.isPresent() ? user.get().email() : "N/A",
                postResponse.message()
        );
    }

    @Override
    public User toUser(final UserResponse userResponse) {
        return new User(
                userResponse.id(),
                userResponse.username(),
                userResponse.email(),
                userResponse.nickname(),
                userResponse.mfaActive(),
                userResponse.nickname().equals("nMFA"),
                userResponse.isActive()
        );
    }

}
