package com.amit.multitool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileReaderUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderUtils.class);

    public static List<String> readEmails(final String filePath) {
        try (final Stream<String> lines = Files.lines(validateFilePath(filePath))) {
            return lines.map(String::trim).filter(Predicate.not(String::isEmpty)).collect(Collectors.toList());
        } catch (IOException | IllegalArgumentException exception) {
            LOGGER.error(exception.getMessage());
            return Collections.emptyList();
        }
    }

    public static String readMessageTemplate(final String filePath) {
        try {
            final Path path = validateFilePath(filePath);
            final String data = Files.readString(path).trim();
            if (data.isBlank()) {
                LOGGER.warn("Template is blank");
                return data;
            }
            return data;
        } catch (IOException | IllegalArgumentException exception) {
            LOGGER.error(exception.getMessage());
            return "";
        }
    }

    private static Path validateFilePath(final String filePath) throws IllegalArgumentException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
        final Path path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File does not exist: " + path);
        }
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Path is not a regular file: " + path);
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("No read permission for file: " + path);
        }
        return path;
    }

    private FileReaderUtils() {
        throw new UnsupportedOperationException();
    }

}
