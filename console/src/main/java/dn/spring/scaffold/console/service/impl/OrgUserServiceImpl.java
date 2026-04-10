package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgUserInfo;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.console.service.OrgUserService;
import dn.spring.scaffold.console.service.UserService;
import dn.spring.scaffold.system.entity.OrgUser;
import dn.spring.scaffold.system.manager.OrgUserManager;
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
    private UserService userService;

    @Override
    public List<OrgUserInfo> listOrgUsers(Long orgId) {
        List<OrgUser> orgUsers = orgUserManager.listByOrgId(orgId);
        if (orgUsers.isEmpty()) {
            return Collections.emptyList();
        }

        List<OrgUserInfo> result = new ArrayList<OrgUserInfo>();
        for (OrgUser orgUser : orgUsers) {
            UserDetailResp user = userService.detail(orgUser.getUserId());
            OrgUserInfo info = new OrgUserInfo();
            info.setOrgId(orgId);
            info.setUserId(user.getId());
            info.setUsername(user.getUsername());
            info.setNickname(user.getNickname());
            result.add(info);
        }
        return result;
    }

    @Override
    public List<OrgUserInfo> listUserOrgsUsers(Long userId) {
        List<OrgUser> orgUsers = orgUserManager.listByUserId(userId);
        if (orgUsers.isEmpty()) {
            return Collections.emptyList();
        }

        List<OrgUserInfo> result = new ArrayList<OrgUserInfo>();
        UserDetailResp user = userService.detail(userId);
        for (OrgUser orgUser : orgUsers) {
            OrgUserInfo info = new OrgUserInfo();
            info.setOrgId(orgUser.getOrgId());
            info.setUserId(userId);
            info.setUsername(user.getUsername());
            info.setNickname(user.getNickname());
            result.add(info);
        }
        return result;
    }

    @Override
    public List<OrgUserInfo> grantOrgUsers(GrantOrgUsersReqParam reqParam) {
        orgUserManager.replaceOrgUsers(reqParam.getOrgId(), reqParam.getUserIds());
        return listOrgUsers(reqParam.getOrgId());
    }
}
