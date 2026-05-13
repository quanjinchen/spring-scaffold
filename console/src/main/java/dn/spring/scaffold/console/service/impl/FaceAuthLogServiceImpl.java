package dn.spring.scaffold.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.ListFaceAuthLogReqParam;
import dn.spring.scaffold.console.pojo.resp.FaceAuthLogDTO;
import dn.spring.scaffold.console.service.FaceAuthLogService;
import dn.spring.scaffold.system.entity.FaceAuthLog;
import dn.spring.scaffold.system.enums.FaceAuthApiTypeEnum;
import dn.spring.scaffold.system.manager.FaceAuthLogManager;
import dn.spring.scaffold.system.pojo.query.ListFaceAuthLogQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaceAuthLogServiceImpl implements FaceAuthLogService {

    @Resource
    private FaceAuthLogManager faceAuthLogManager;

    @Override
    public RespInfo<PageData<FaceAuthLogDTO>> listFaceAuthLog(ListFaceAuthLogReqParam reqParam) {
        ListFaceAuthLogQuery query = new ListFaceAuthLogQuery();
        query.setAuthApiType(resolveAuthApiType(reqParam.getAuthApiType()));
        query.setIp(reqParam.getIp());
        query.setStatus(reqParam.getStatus());
        query.setAppName(reqParam.getAppName());
        query.setAuthFullName(reqParam.getAuthFullName());

        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        List<FaceAuthLog> faceAuthLogList = faceAuthLogManager.listFaceAuthLogs(query);

        List<FaceAuthLogDTO> faceAuthLogDTOList = new ArrayList<FaceAuthLogDTO>(faceAuthLogList.size());
        for (FaceAuthLog faceAuthLog : faceAuthLogList) {
            FaceAuthLogDTO faceAuthLogDTO = new FaceAuthLogDTO();
            faceAuthLogDTO.setId(faceAuthLog.getId());
            faceAuthLogDTO.setAuthApiType(faceAuthLog.getAuthApiType() == null ? null : faceAuthLog.getAuthApiType().getCode());
            faceAuthLogDTO.setIp(faceAuthLog.getIp());
            faceAuthLogDTO.setAppId(faceAuthLog.getAppId());
            faceAuthLogDTO.setAppName(faceAuthLog.getAppName());
            faceAuthLogDTO.setAuthFullName(faceAuthLog.getAuthFullName());
            faceAuthLogDTO.setAuthUserId(faceAuthLog.getAuthUserId());
            faceAuthLogDTO.setStatus(faceAuthLog.getStatus());
            faceAuthLogDTO.setErrmsg(faceAuthLog.getErrmsg());
            faceAuthLogDTO.setCreateTime(faceAuthLog.getCreateTime());
            faceAuthLogDTOList.add(faceAuthLogDTO);
        }

        PageData<FaceAuthLogDTO> pageData = new PageData<FaceAuthLogDTO>();
        pageData.setTotal(new PageInfo<FaceAuthLog>(faceAuthLogList).getTotal());
        pageData.setRecords(faceAuthLogDTOList);
        pageData.setPageNum(reqParam.getPageNum());
        pageData.setPageSize(reqParam.getPageSize());
        return RespInfo.success(pageData);
    }

    private FaceAuthApiTypeEnum resolveAuthApiType(Integer authApiType) {
        if (authApiType == null) {
            return null;
        }
        for (FaceAuthApiTypeEnum value : FaceAuthApiTypeEnum.values()) {
            if (value.getCode().equals(authApiType)) {
                return value;
            }
        }
        return null;
    }
}
