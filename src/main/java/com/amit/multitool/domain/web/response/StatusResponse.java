package com.amit.multitool.domain.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StatusResponse(
        @JsonProperty(value = "status") String status) {
}
