package dn.spring.scaffold.console.service.impl;

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
    public List<OrgUserInfo> listOrgUsers(Long orgId) {
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

    @Override
    public List<OrgUserInfo> listUserOrgsUsers(Long userId) {
        List<OrgUser> orgUsers = orgUserManager.listByUserId(userId);
        if (orgUsers.isEmpty()) {
            return Collections.emptyList();
        }

        List<OrgUserInfo> result = new ArrayList<OrgUserInfo>();
        User user = userManager.getById(userId);
        if (user == null) {
            return Collections.emptyList();
        }
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
