package dn.spring.scaffold.framework.utils;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class ReportUtils {

    private ReportUtils() {
    }

    public static <T> void writeExcel(HttpServletResponse response, String fileName, String sheetName, Class<T> headClass, List<T> data) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20"));
        EasyExcel.write(response.getOutputStream(), headClass).sheet(sheetName).doWrite(data);
    }
}
