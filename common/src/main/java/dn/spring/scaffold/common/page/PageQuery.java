package dn.spring.scaffold.common.page;

import lombok.Data;

@Data
public class PageQuery {

    private long pageNum = 1L;

    private long pageSize = 20L;
}
