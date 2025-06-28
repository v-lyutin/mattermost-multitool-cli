package com.amit.multitool.domain.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponse(
        @JsonProperty(value = "id") String id,
        @JsonProperty(value = "username") String username,
        @JsonProperty(value = "email") String email,
        @JsonProperty(value = "nickname") String nickname,
        @JsonProperty(value = "mfa_active") boolean mfaActive,
        @JsonProperty(value = "delete_at") long deleteAt) {

    public boolean isActive() {
        return deleteAt == 0;
    }

}
