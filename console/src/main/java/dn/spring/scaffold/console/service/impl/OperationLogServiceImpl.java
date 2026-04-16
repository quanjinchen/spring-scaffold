package dn.spring.scaffold.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.ListOperationLogReqParam;
import dn.spring.scaffold.console.pojo.resp.OperationLogDTO;
import dn.spring.scaffold.console.service.OperationLogService;
import dn.spring.scaffold.system.entity.OperationLog;
import dn.spring.scaffold.system.manager.OperationLogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationLogManager operationLogManager;

    @Override
    public RespInfo<PageData<OperationLogDTO>> listOperationLog(ListOperationLogReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        List<OperationLog> operationLogList = operationLogManager.listOperationLogs();

        List<OperationLogDTO> operationLogDTOList = new ArrayList<OperationLogDTO>(operationLogList.size());
        for (OperationLog operationLog : operationLogList) {
            OperationLogDTO operationLogDTO = new OperationLogDTO();
            operationLogDTO.setId(operationLog.getId());
            operationLogDTO.setModuleName(operationLog.getModuleName());
            operationLogDTO.setActionName(operationLog.getActionName());
            operationLogDTO.setOperatorName(operationLog.getOperatorName());
            operationLogDTO.setRequestPath(operationLog.getRequestPath());
            operationLogDTO.setSuccessFlag(operationLog.getSuccessFlag());
            operationLogDTO.setRequestTime(operationLog.getRequestTime());
            operationLogDTOList.add(operationLogDTO);
        }

        PageData<OperationLogDTO> pageData = new PageData<OperationLogDTO>();
        pageData.setTotal(new PageInfo<OperationLog>(operationLogList).getTotal());
        pageData.setRecords(operationLogDTOList);
        pageData.setPageNum(reqParam.getPageNum());
        pageData.setPageSize(reqParam.getPageSize());
        return RespInfo.success(pageData);
    }
}
