package com.amit.multitool.domain.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChannelResponse(
        @JsonProperty(value = "id") String id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "display_name") String displayName,
        @JsonProperty(value = "create_at") long createAt,
        @JsonProperty(value = "update_at") long updateAt,
        @JsonProperty(value = "delete_at") long deleteAt,
        @JsonProperty(value = "creator_id") String creatorId,
        @JsonProperty(value = "team_display_name") String teamDisplayName,
        @JsonProperty(value = "total_msg_count") long totalPostCount,
        @JsonProperty(value = "total_msg_count_root") long totalThreadCount) {
}
