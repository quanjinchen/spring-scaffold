package dn.spring.scaffold.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long current = 1L;

    private long size = 10L;

    private long total = 0L;

    private long pages = 0L;

    private List<T> records = Collections.emptyList();
}
