package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateAppReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteAppReqParam;
import dn.spring.scaffold.console.pojo.req.GetAppByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListAppReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateAppReqParam;
import dn.spring.scaffold.console.pojo.resp.AppDTO;

public interface AppService {

    RespInfo<PageData<AppDTO>> listApp(ListAppReqParam reqParam);

    RespInfo<AppDTO> getAppById(GetAppByIdReqParam reqParam);

    RespInfo<AppDTO> createApp(CreateAppReqParam reqParam);

    RespInfo<AppDTO> updateApp(UpdateAppReqParam reqParam);

    RespInfo<Void> deleteApp(DeleteAppReqParam reqParam);
}
