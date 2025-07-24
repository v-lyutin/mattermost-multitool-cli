package com.amit.multitool.service.impl;

import com.amit.multitool.domain.model.Post;
import com.amit.multitool.domain.web.response.ChannelPostsResponse;
import com.amit.multitool.domain.web.response.PostResponse;
import com.amit.multitool.mapper.MattermostModelMapper;
import com.amit.multitool.service.ChannelMessageUploaderService;
import com.amit.multitool.service.MattermostApiV4ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public final class DefaultChannelMessageUploaderService implements ChannelMessageUploaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultChannelMessageUploaderService.class);

    private final MattermostApiV4ClientService mattermostApiV4ClientService;

    private final MattermostModelMapper mattermostModelMapper;

    @Autowired
    public DefaultChannelMessageUploaderService(final MattermostApiV4ClientService mattermostApiV4ClientService,
                                                final MattermostModelMapper mattermostModelMapper) {
        this.mattermostApiV4ClientService = mattermostApiV4ClientService;
        this.mattermostModelMapper = mattermostModelMapper;
    }

    @Override
    public Set<Post> uploadChannelMessagesOverTimeRange(String channelId, long startTimestamp, long endTimestamp) {
        final Set<Post> sortedPostsStorage = new TreeSet<>();
        long since = startTimestamp;
        long previousSince = Long.MIN_VALUE;
        boolean hasMore = true;
        while (hasMore && since <= endTimestamp) {
            final Optional<ChannelPostsResponse> channelPostsResponse = this.mattermostApiV4ClientService.getChannelPosts(channelId, since);
            if (channelPostsResponse.isEmpty()) {
                break;
            }
            final Collection<PostResponse> posts = channelPostsResponse.get().posts().values();
            if (posts.isEmpty()) {
                break;
            }
            boolean foundInRange = false;
            for (final PostResponse postResponse : posts) {
                if (postResponse.createAt() >= startTimestamp && postResponse.createAt() <= endTimestamp) {
                    Post post = this.mattermostModelMapper.toPost(postResponse);
                    if (!post.message().isBlank()) {
                        sortedPostsStorage.add(post);
                        if (!foundInRange) {
                            foundInRange = true;
                        }
                    }
                }
            }
            final long lastCreateAt = posts.stream()
                    .mapToLong(PostResponse::createAt)
                    .max()
                    .orElse(endTimestamp);
            if (lastCreateAt == since && since == previousSince) {
                break;
            }
            previousSince = since;
            since = lastCreateAt;
            if (!foundInRange || lastCreateAt >= endTimestamp) {
                hasMore = false;
            }
        }
        LOGGER.info("Total posts: {}", sortedPostsStorage.size());
        LOGGER.info("Total threads: {}", sortedPostsStorage.stream().filter(Post::isRoot).count());
        return sortedPostsStorage;
    }

}
