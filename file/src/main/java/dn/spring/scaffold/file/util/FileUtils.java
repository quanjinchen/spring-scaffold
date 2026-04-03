package dn.spring.scaffold.file.util;

import org.apache.tika.Tika;

import java.io.InputStream;

public final class FileUtils {

    private static final Tika TIKA = new Tika();

    private FileUtils() {
    }

    public static String getMimeType(InputStream inputStream, String name) {
        try {
            return TIKA.detect(inputStream, name);
        } catch (Exception exception) {
            return getMimeType(name);
        }
    }

    public static String getMimeType(String name) {
        return TIKA.detect(name == null ? "" : name);
    }

    public static String getFileSuffix(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return "";
        }
        int index = fileName.lastIndexOf('.');
        if (index < 0 || index == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    public static String buildFileUrl(String publicUrlPrefix, String fileId) {
        if (fileId == null || fileId.trim().isEmpty()) {
            return null;
        }

        String normalizedPrefix = publicUrlPrefix == null ? "" : publicUrlPrefix.trim();
        if (normalizedPrefix.endsWith("/")) {
            normalizedPrefix = normalizedPrefix.substring(0, normalizedPrefix.length() - 1);
        }
        return normalizedPrefix + "/file/" + fileId;
    }
}
