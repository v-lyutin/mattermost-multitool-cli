package com.amit.multitool.domain.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PostRequest(
        @JsonProperty(value = "channel_id") String channelId,
        @JsonProperty(value = "message") String message) {
}
