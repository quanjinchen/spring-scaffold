package dn.spring.scaffold.console.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.converter.AppConverter;
import dn.spring.scaffold.console.pojo.req.CreateAppReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteAppReqParam;
import dn.spring.scaffold.console.pojo.req.GetAppByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListAppReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateAppReqParam;
import dn.spring.scaffold.console.pojo.resp.AppDTO;
import dn.spring.scaffold.console.service.AppService;
import dn.spring.scaffold.system.entity.App;
import dn.spring.scaffold.system.manager.AppManager;
import dn.spring.scaffold.system.pojo.query.ListAppQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppManager appManager;

    @Override
    public RespInfo<PageData<AppDTO>> listApp(ListAppReqParam reqParam) {
        Integer pageNum = reqParam.getPageNum();
        Integer pageSize = reqParam.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        ListAppQuery query = AppConverter.INSTANCE.convert(reqParam);
        List<App> appList = appManager.listApp(query);
        List<AppDTO> appDTOList = AppConverter.INSTANCE.convert(appList);

        PageData<AppDTO> pageData = new PageData<AppDTO>();
        pageData.setTotal(new PageInfo<App>(appList).getTotal());
        pageData.setRecords(appDTOList);
        pageData.setPageNum(pageNum);
        pageData.setPageSize(pageSize);
        return RespInfo.success(pageData);
    }

    @Override
    public RespInfo<AppDTO> getAppById(GetAppByIdReqParam reqParam) {
        App app = appManager.getById(reqParam.getAppId());
        ResultCode.APP_NOT_FOUND.assertNotNull(app);
        return RespInfo.success(AppConverter.INSTANCE.convert(app));
    }

    @Override
    public RespInfo<AppDTO> createApp(CreateAppReqParam reqParam) {
        App sameAppName = appManager.getByAppName(reqParam.getAppName());
        ResultCode.APP_NAME_ALREADY_EXISTS.assertIsFalse(sameAppName != null);

        // 应用编码要求唯一，避免不同应用复用同一业务标识。
        App sameAppCode = appManager.getByAppCode(reqParam.getAppCode());
        ResultCode.APP_CODE_ALREADY_EXISTS.assertIsFalse(sameAppCode != null);

        App app = new App();
        app.setAppName(reqParam.getAppName());
        app.setAppCode(reqParam.getAppCode());
        app.setRemark(reqParam.getRemark());
        app.setClientId(generateClientId());
        app.setClientSecret(generateClientSecret());

        App savedApp = appManager.save(app);
        return RespInfo.created(AppConverter.INSTANCE.convert(savedApp));
    }

    @Override
    public RespInfo<AppDTO> updateApp(UpdateAppReqParam reqParam) {
        App existedApp = appManager.getById(reqParam.getId());
        ResultCode.APP_NOT_FOUND.assertNotNull(existedApp);

        App sameAppName = appManager.getByAppName(reqParam.getAppName());
        ResultCode.APP_NAME_ALREADY_EXISTS.assertIsFalse(sameAppName != null && !sameAppName.getId().equals(existedApp.getId()));

        App sameAppCode = appManager.getByAppCode(reqParam.getAppCode());
        ResultCode.APP_CODE_ALREADY_EXISTS.assertIsFalse(sameAppCode != null && !sameAppCode.getId().equals(existedApp.getId()));

        existedApp.setAppName(reqParam.getAppName());
        existedApp.setAppCode(reqParam.getAppCode());
        existedApp.setRemark(reqParam.getRemark());

        App savedApp = appManager.save(existedApp);
        return RespInfo.success(AppConverter.INSTANCE.convert(savedApp));
    }

    @Override
    public RespInfo<Void> deleteApp(DeleteAppReqParam reqParam) {
        App app = appManager.getById(reqParam.getAppId());
        ResultCode.APP_NOT_FOUND.assertNotNull(app);
        appManager.deleteById(reqParam.getAppId());
        return RespInfo.success();
    }

    private String generateClientId() {
        String clientId = "app-" + IdUtil.fastSimpleUUID().substring(0, 12);
        App sameClientId = appManager.getByClientId(clientId);
        while (sameClientId != null) {
            clientId = "app-" + IdUtil.fastSimpleUUID().substring(0, 12);
            sameClientId = appManager.getByClientId(clientId);
        }
        return clientId;
    }

    private String generateClientSecret() {
        return IdUtil.fastSimpleUUID().substring(0, 24);
    }
}
