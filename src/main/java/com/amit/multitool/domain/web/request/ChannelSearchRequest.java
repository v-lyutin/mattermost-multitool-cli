package com.amit.multitool.domain.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChannelSearchRequest(
        @JsonProperty(value = "term") String term,
        @JsonProperty(value = "public") boolean isPublic,
        @JsonProperty(value = "private") boolean isPrivate,
        @JsonProperty(value = "deleted") boolean isDelete,
        @JsonProperty(value = "group_constrained") boolean groupConstrained,
        @JsonProperty(value = "exclude_group_constrained") boolean excludeGroupConstrained,
        @JsonProperty(value = "include_deleted") boolean includeDeleted,
        @JsonProperty(value = "include_search_by_id") boolean includeSearchById,
        @JsonProperty(value = "team_ids") List<String> teamIds,
        @JsonProperty(value = "page") int page,
        @JsonProperty(value = "per_page") int perPage) {

    public static ChannelSearchRequest getDefaultChannelSearchRequest(final String teamId, final int page) {
        return new ChannelSearchRequest(
                "",
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                List.of(teamId),
                page,
                100

        );
    }

}
