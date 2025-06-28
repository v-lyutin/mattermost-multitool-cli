package com.amit.multitool.utils;

import com.amit.multitool.domain.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public final class PostCsvWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostCsvWriter.class);

    private static final String CSV_HEADER = "ID,Root ID,Create At,User ID,Message\n";

    private static final String FILE_EXTENSION = ".csv";

    private static final String FIELD_DELIMITER = ",";

    private static final String LINE_SEPARATOR = "\n";

    private static final String QUOTE = "\"";

    public static void writePostsToCsv(final Set<Post> posts, final String fileName) {
        if (posts == null || posts.isEmpty()) {
            LOGGER.warn("Attempt to write empty posts collection");
            return;
        }
        final Path filePath = generateFileName(fileName);
        try {
            writePostsToFile(posts, filePath);
            LOGGER.info("Successfully generated report: {}", filePath.toAbsolutePath());
        } catch (IOException exception) {
            LOGGER.error("Failed to write report to file: {}", filePath.toAbsolutePath(), exception);
        }
    }

    private static Path generateFileName(String fileName) {
        return Paths.get(fileName + FILE_EXTENSION);
    }

    private static void writePostsToFile(final Set<Post> posts, final Path filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(CSV_HEADER);
            posts.forEach(post -> writePostLine(writer, post));
        }
    }

    private static void writePostLine(final BufferedWriter writer, final Post post) {
        try {
            writer.write(convertPostToCsvLine(post));
            writer.write(LINE_SEPARATOR);
        } catch (IOException exception) {
            LOGGER.error("Failed to write post line for post ID: {}", post.id(), exception);
        }
    }

    private static String convertPostToCsvLine(final Post post) {
        return String.join(
                FIELD_DELIMITER,
                post.id(),
                post.isRoot() ? post.id() : post.rootId(),
                DateTimeUtils.millisToDateTimeFormat(post.createAt()),
                post.senderEmail(),
                escapeCsvField(post.message())
        );
    }

    private static String escapeCsvField(final String field) {
        if (field == null) {
            return "";
        }
        if (field.contains(FIELD_DELIMITER) || field.contains("\n") || field.contains(QUOTE)) {
            return QUOTE + field.replace(QUOTE, QUOTE + QUOTE) + QUOTE;
        }
        return field;
    }

    private PostCsvWriter() {
        throw new UnsupportedOperationException();
    }

}
