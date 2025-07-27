package com.amit.multitool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public final class BatchSchedulerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchSchedulerUtils.class);

    public static final long GLOBAL_BATCH_DELAY_MS = 1_000L;

    public static final long MAX_WAITING_TIME_MS = 3_600_000L;

    public static <T> void scheduleBatch(final List<T> items, final Consumer<T> itemConsumer) {
        if (items == null || items.isEmpty()) {
            return;
        }
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduleTasks(items, itemConsumer, scheduledExecutorService);
        shutdownAndAwait(scheduledExecutorService);
    }

    private static <T> void scheduleTasks(final List<T> items,
                                          final Consumer<T> itemConsumer,
                                          final ScheduledExecutorService scheduledExecutorService) {
        IntStream.range(0, items.size()).forEach(iteration -> {
            final T item = items.get(iteration);
            final long delay = iteration * GLOBAL_BATCH_DELAY_MS;
            scheduledExecutorService.schedule(
                    buildSafeBatchTask(item, itemConsumer),
                    delay,
                    TimeUnit.MILLISECONDS);
        });
    }

    private static void shutdownAndAwait(final ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.shutdown();
        try {
            boolean isCompleted = scheduledExecutorService.awaitTermination(MAX_WAITING_TIME_MS, TimeUnit.MILLISECONDS);
            if (isCompleted) {
                LOGGER.info("Batch processing finished successfully");
            }
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Batch processing was interrupted", exception);
        }
    }

    private static <T> Runnable buildSafeBatchTask(final T item, final Consumer<T> itemConsumer) {
        return () -> {
            try {
                itemConsumer.accept(item);
            } catch (final Exception exception) {
                LOGGER.error("Error during batch task execution: {}", item, exception);
            }
        };
    }

    private BatchSchedulerUtils() {
        throw new UnsupportedOperationException();
    }

}
