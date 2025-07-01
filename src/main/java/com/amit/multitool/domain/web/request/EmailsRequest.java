package com.amit.multitool.domain.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EmailsRequest(
        @JsonProperty(value = "emails") List<String> emails) {
}
