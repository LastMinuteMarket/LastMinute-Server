package com.lastminute.lastminuteserver.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public final class MultipartUtil {

    private static final String BASE_DIR = "lastminute/files";

    public static String getLocalHomeDirectory() {
        return System.getProperty("user.home");
    }

    public static String createFileId() {
        return UUID.randomUUID().toString();
    }

    public static String getFormat(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return null;
        }

        return contentType.substring(contentType.lastIndexOf('/') + 1);
    }

    public static String cratePath(String fileId, String format) {
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }
}
