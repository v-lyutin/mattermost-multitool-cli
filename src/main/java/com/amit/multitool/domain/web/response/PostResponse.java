package com.amit.multitool.domain.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PostResponse(
        @JsonProperty(value = "id") String id,
        @JsonProperty(value = "create_at") long createAt,
        @JsonProperty(value = "user_id") String userId,
        @JsonProperty(value = "root_id") String rootId,
        @JsonProperty(value = "message") String message) {
}
