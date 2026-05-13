package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GetAccessTokenReqParam;
import dn.spring.scaffold.console.pojo.resp.AccessTokenDTO;
import dn.spring.scaffold.console.service.AuthService;
import dn.spring.scaffold.framework.auth.AccessTokenInfo;
import dn.spring.scaffold.framework.auth.AccessTokenManager;
import dn.spring.scaffold.system.entity.App;
import dn.spring.scaffold.system.manager.AppManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthServiceImpl implements AuthService {

    private static final int ACCESS_TOKEN_EXPIRE_SECONDS = 7200;

    @Resource
    private AppManager appManager;
    @Resource
    private AccessTokenManager accessTokenManager;

    @Override
    public RespInfo<AccessTokenDTO> getAccessToken(GetAccessTokenReqParam reqParam) {
        App app = appManager.getByClientId(reqParam.getClientId());
        ResultCode.APP_CLIENT_AUTH_FAILED.assertNotNull(app);
        ResultCode.APP_CLIENT_AUTH_FAILED.assertIsTrue(reqParam.getClientSecret().equals(app.getClientSecret()));

        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setAppId(app.getId());
        accessTokenInfo.setAppName(app.getAppName());
        accessTokenInfo.setExpiresIn(ACCESS_TOKEN_EXPIRE_SECONDS);
        String accessToken = accessTokenManager.getAccessToken(accessTokenInfo, ACCESS_TOKEN_EXPIRE_SECONDS);

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setAccessToken(accessToken);
        accessTokenDTO.setExpiresIn(ACCESS_TOKEN_EXPIRE_SECONDS);
        return RespInfo.success(accessTokenDTO);
    }
}
