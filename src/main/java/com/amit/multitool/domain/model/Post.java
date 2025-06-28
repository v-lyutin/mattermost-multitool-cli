package com.amit.multitool.domain.model;

import org.jetbrains.annotations.NotNull;

public record Post(String id,
                   long createAt,
                   String rootId,
                   String senderEmail,
                   String message) implements Comparable<Post> {

    @Override
    public int compareTo(final @NotNull Post otherPost) {
        return Long.compare(this.createAt, otherPost.createAt);
    }

    public boolean isRoot() {
        return this.rootId == null || this.rootId.trim().isEmpty();
    }

}
