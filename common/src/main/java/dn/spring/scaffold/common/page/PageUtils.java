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
        pageData.setCurrent(page.getCurrent());
        pageData.setSize(page.getSize());
        pageData.setTotal(page.getTotal());
        pageData.setPages(page.getPages());
        pageData.setRecords(page.getRecords());
        return pageData;
    }

    public static <T> PageData<T> of(long current, long size, long total, List<T> records) {
        PageData<T> pageData = new PageData<T>();
        pageData.setCurrent(current);
        pageData.setSize(size);
        pageData.setTotal(total);
        pageData.setPages(size <= 0 ? 0 : (total + size - 1) / size);
        pageData.setRecords(records == null ? Collections.<T>emptyList() : records);
        return pageData;
    }

    public static <T> PageData<T> doPage(PageReqParam reqParam, Supplier<List<T>> supplier) {
        Page<T> page = PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        List<T> result = supplier.get();
        return of(page.getPageNum(), page.getPageSize(), page.getTotal(), result);
    }
}
