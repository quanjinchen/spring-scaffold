package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.ListFaceAuthLogReqParam;
import dn.spring.scaffold.console.pojo.resp.FaceAuthLogDTO;

public interface FaceAuthLogService {

    RespInfo<PageData<FaceAuthLogDTO>> listFaceAuthLog(ListFaceAuthLogReqParam reqParam);
}

