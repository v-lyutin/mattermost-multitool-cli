package com.amit.multitool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public final class ThreadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtils.class);

    public static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Thread was interrupted during sleep", exception);
        }
    }

    private ThreadUtils() {
        throw new UnsupportedOperationException();
    }

}
