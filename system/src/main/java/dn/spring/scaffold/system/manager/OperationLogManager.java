package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.OperationLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface OperationLogManager {

    Page<OperationLog> page(PageReqParam reqParam);

    void save(OperationLog operationLog);
}
