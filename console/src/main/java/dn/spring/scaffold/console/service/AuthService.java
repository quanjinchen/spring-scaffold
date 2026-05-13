package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GetAccessTokenReqParam;
import dn.spring.scaffold.console.pojo.resp.AccessTokenDTO;

public interface AuthService {

    RespInfo<AccessTokenDTO> getAccessToken(GetAccessTokenReqParam reqParam);
}
