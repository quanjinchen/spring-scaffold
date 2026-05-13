package dn.spring.scaffold.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dn.spring.scaffold.system.entity.FaceAuthLog;
import dn.spring.scaffold.system.manager.FaceAuthLogManager;
import dn.spring.scaffold.system.mapper.FaceAuthLogMapper;
import dn.spring.scaffold.system.pojo.query.ListFaceAuthLogQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FaceAuthLogManagerImpl implements FaceAuthLogManager {

    @Resource
    private FaceAuthLogMapper faceAuthLogMapper;

    @Override
    public void save(FaceAuthLog faceAuthLog) {
        faceAuthLogMapper.insert(faceAuthLog);
    }

    @Override
    public List<FaceAuthLog> listFaceAuthLogs(ListFaceAuthLogQuery query) {
        LambdaQueryWrapper<FaceAuthLog> queryWrapper = new LambdaQueryWrapper<FaceAuthLog>()
                .orderByDesc(FaceAuthLog::getCreateTime)
                .orderByDesc(FaceAuthLog::getId);
        if (query == null) {
            return faceAuthLogMapper.selectList(queryWrapper);
        }
        if (query.getAuthApiType() != null) {
            queryWrapper.eq(FaceAuthLog::getAuthApiType, query.getAuthApiType());
        }
        if (StringUtils.hasText(query.getIp())) {
            queryWrapper.like(FaceAuthLog::getIp, query.getIp());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(FaceAuthLog::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getAppName())) {
            queryWrapper.like(FaceAuthLog::getAppName, query.getAppName());
        }
        if (StringUtils.hasText(query.getAuthFullName())) {
            queryWrapper.like(FaceAuthLog::getAuthFullName, query.getAuthFullName());
        }
        return faceAuthLogMapper.selectList(queryWrapper);
    }
}
