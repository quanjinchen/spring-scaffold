package dn.spring.scaffold.file.util;

import dn.spring.scaffold.common.exception.BizException;
import org.springframework.http.MediaType;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataUrlUtils {

    private static final Pattern DATA_URL_PATTERN = Pattern.compile("^data:(.+?);base64,(.+)$");

    private DataUrlUtils() {
    }

    public static DataUrlInfo parseDataUrl(String dataUrl) {
        Matcher matcher = DATA_URL_PATTERN.matcher(dataUrl == null ? "" : dataUrl);
        if (!matcher.matches()) {
            throw new BizException("invalid data url format");
        }

        String mimeType = matcher.group(1);
        String base64Data = matcher.group(2);
        return new DataUrlInfo(MediaType.valueOf(mimeType), Base64.getDecoder().decode(base64Data));
    }

    public static final class DataUrlInfo {

        private final MediaType mediaType;

        private final byte[] data;

        public DataUrlInfo(MediaType mediaType, byte[] data) {
            this.mediaType = mediaType;
            this.data = data;
        }

        public MediaType getMediaType() {
            return mediaType;
        }

        public byte[] getData() {
            return data;
        }
    }
}
