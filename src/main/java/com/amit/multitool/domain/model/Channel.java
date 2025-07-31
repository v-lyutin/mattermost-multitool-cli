package com.amit.multitool.domain.model;

public record Channel(
        String id,
        String name,
        String displayName,
        long createAt,
        long updateAt,
        String creatorUsername,
        String teamDisplayName,
        long totalPostCount,
        long totalThreadCount,
        boolean isArchived) {
}
