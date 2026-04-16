package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GetOrgUserListReqParam;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgUserInfo;
import dn.spring.scaffold.console.service.OrgUserService;
import dn.spring.scaffold.system.entity.OrgUser;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.OrgUserManager;
import dn.spring.scaffold.system.manager.UserManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrgUserServiceImpl implements OrgUserService {

    @Resource
    private OrgUserManager orgUserManager;
    @Resource
    private UserManager userManager;

    @Override
    public RespInfo<List<OrgUserInfo>> listOrgUser(GetOrgUserListReqParam reqParam) {
        return RespInfo.success(buildOrgUserInfoList(reqParam.getOrgId()));
    }

    @Override
    public RespInfo<List<OrgUserInfo>> grantOrgUsers(GrantOrgUsersReqParam reqParam) {
        orgUserManager.replaceOrgUsers(reqParam.getOrgId(), reqParam.getUserIds());
        return RespInfo.success(buildOrgUserInfoList(reqParam.getOrgId()));
    }

    private List<OrgUserInfo> buildOrgUserInfoList(Long orgId) {
        List<OrgUser> orgUsers = orgUserManager.listByOrgId(orgId);
        if (orgUsers.isEmpty()) {
            return Collections.emptyList();
        }

        List<OrgUserInfo> result = new ArrayList<OrgUserInfo>();
        for (OrgUser orgUser : orgUsers) {
            User user = userManager.getById(orgUser.getUserId());
            if (user == null) {
                continue;
            }
            OrgUserInfo info = new OrgUserInfo();
            info.setOrgId(orgId);
            info.setUserId(user.getId());
            info.setUsername(user.getUsername());
            info.setNickname(user.getNickname());
            result.add(info);
        }
        return result;
    }
}
