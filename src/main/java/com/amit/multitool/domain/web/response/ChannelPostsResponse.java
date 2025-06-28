package com.amit.multitool.domain.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChannelPostsResponse(@JsonProperty(value = "posts") Map<String, PostResponse> posts) {
}
