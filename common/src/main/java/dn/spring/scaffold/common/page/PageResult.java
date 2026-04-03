package dn.spring.scaffold.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private List<T> records;

    private long total;

    private long pageNum;

    private long pageSize;

    public static <T> PageResult<T> of(List<T> records, long total, PageQuery pageQuery) {
        return new PageResult<>(records, total, pageQuery.getPageNum(), pageQuery.getPageSize());
    }

    public static <T> PageResult<T> empty(PageQuery pageQuery) {
        return new PageResult<>(Collections.emptyList(), 0L, pageQuery.getPageNum(), pageQuery.getPageSize());
    }
}
