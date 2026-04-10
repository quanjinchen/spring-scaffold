package dn.spring.scaffold.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页结果")
public class PageResult<T> {

    @Schema(description = "数据列表")
    private List<T> records;

    @Schema(description = "总记录数")
    private long total;

    @Schema(description = "当前页码")
    private long pageNum;

    @Schema(description = "每页条数")
    private long pageSize;

    public static <T> PageResult<T> of(List<T> records, long total, PageQuery pageQuery) {
        return new PageResult<>(records, total, pageQuery.getPageNum(), pageQuery.getPageSize());
    }

    public static <T> PageResult<T> empty(PageQuery pageQuery) {
        return new PageResult<>(Collections.emptyList(), 0L, pageQuery.getPageNum(), pageQuery.getPageSize());
    }
}
