package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.system.entity.OperationLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OperationLogManager {

    Page<OperationLog> page(PageQuery pageQuery);

    void save(OperationLog operationLog);
}
