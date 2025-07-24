package com.amit.multitool.service.impl;

import com.amit.multitool.service.RequestExecutorService;
import feign.FeignException;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class FeignRequestExecutorService implements RequestExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignRequestExecutorService.class);

    @Qualifier(value = "mattermostRateLimiter")
    private final RateLimiter rateLimiter;

    @Autowired
    public FeignRequestExecutorService(final RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public <T> Optional<T> executeRequest(final Supplier<T> apiCall) {
        try {
            RateLimiter.waitForPermission(this.rateLimiter);
            final T result = apiCall.get();
            return Optional.ofNullable(result);
        } catch (final FeignException exception) {
            LOGGER.error("Request error: {}", exception.getMessage());
            return Optional.empty();
        } catch (final Exception exception) {
            LOGGER.error("Unexpected error: {}", exception.getMessage());
            return Optional.empty();
        }
    }

}
