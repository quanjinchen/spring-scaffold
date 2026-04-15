package dn.spring.scaffold.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@Schema(description = "分页数据")
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页码")
    private long pageNum = 1L;

    @Schema(description = "每页条数")
    private long pageSize = 10L;

    @Schema(description = "总记录数")
    private long total = 0L;

    @Schema(description = "当前页数据列表")
    private List<T> records = Collections.emptyList();

    public static PageData empty(int pageNum, int pageSize) {
        PageData pageData = new PageData<>();
        pageData.setPageNum(pageNum);
        pageData.setPageSize(pageSize);
        pageData.setTotal(0L);
        pageData.setRecords(Collections.emptyList());
        return pageData;
    }
}
