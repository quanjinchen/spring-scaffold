package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.OperationLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface OperationLogManager {

    Page<OperationLog> page(PageReqParam reqParam);

    List<OperationLog> listOperationLogs();

    void save(OperationLog operationLog);
}
