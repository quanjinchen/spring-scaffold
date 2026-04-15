package dn.spring.scaffold.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class PageUtils {

    private PageUtils() {
    }

    public static <T> PageData<T> of(IPage<T> page) {
        PageData<T> pageData = new PageData<T>();
        pageData.setPageNum(page.getCurrent());
        pageData.setPageSize(page.getSize());
        pageData.setTotal(page.getTotal());
        pageData.setRecords(page.getRecords());
        return pageData;
    }

    public static <T> PageData<T> of(long current, long size, long total, List<T> records) {
        PageData<T> pageData = new PageData<T>();
        pageData.setPageNum(current);
        pageData.setPageSize(size);
        pageData.setTotal(total);
        pageData.setRecords(records == null ? Collections.<T>emptyList() : records);
        return pageData;
    }

    public static <T> PageData<T> doPage(PageReqParam reqParam, Supplier<List<T>> supplier) {
        Page<T> page = PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        List<T> result = supplier.get();
        return of(page.getPageNum(), page.getPageSize(), page.getTotal(), result);
    }
}
