package com.amit.multitool.config;

public record MattermostCredentials(
        String baseUrl,
        String teamId,
        String accessToken) {
}
