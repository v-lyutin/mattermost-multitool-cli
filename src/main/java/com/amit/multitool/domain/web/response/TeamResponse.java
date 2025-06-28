package com.amit.multitool.domain.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TeamResponse(
        @JsonProperty(value = "id") String id,
        @JsonProperty(value = "display_name") String displayName,
        @JsonProperty(value = "name") String name) {
}
