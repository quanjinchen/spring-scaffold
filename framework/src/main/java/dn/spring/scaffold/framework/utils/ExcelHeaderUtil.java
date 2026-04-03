package dn.spring.scaffold.framework.utils;

import com.alibaba.excel.metadata.Head;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExcelHeaderUtil {

    private ExcelHeaderUtil() {
    }

    public static Map<Integer, Head> singleRowHeaders(String... headers) {
        Map<Integer, Head> headMap = new LinkedHashMap<Integer, Head>();
        if (headers == null) {
            return headMap;
        }
        for (int i = 0; i < headers.length; i++) {
            headMap.put(i, new Head(i, null, headers[i], Collections.singletonList(headers[i]), Boolean.TRUE, Boolean.TRUE));
        }
        return headMap;
    }
}
