package dn.spring.scaffold.console.service;

import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgUserInfo;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.system.entity.OrgUser;
import dn.spring.scaffold.system.mapper.OrgUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrgUserService {

    private final OrgUserMapper orgUserMapper;

    private final UserService userService;

    public OrgUserService(OrgUserMapper orgUserMapper, UserService userService) {
        this.orgUserMapper = orgUserMapper;
        this.userService = userService;
    }

    public List<OrgUserInfo> listOrgUsers(Long orgId) {
        List<OrgUser> orgUsers = orgUserMapper.selectList(new LambdaQueryWrapper<OrgUser>()
                .eq(OrgUser::getOrgId, orgId));
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

    public List<OrgUserInfo> listUserOrgsUsers(Long userId) {
        List<OrgUser> orgUsers = orgUserMapper.selectList(new LambdaQueryWrapper<OrgUser>()
                .eq(OrgUser::getUserId, userId));
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

    public List<OrgUserInfo> grantOrgUsers(GrantOrgUsersReqParam reqParam) {
        orgUserMapper.delete(new LambdaQueryWrapper<OrgUser>().eq(OrgUser::getOrgId, reqParam.getOrgId()));
        if (reqParam.getUserIds() != null) {
            for (Long userId : reqParam.getUserIds()) {
                OrgUser orgUser = new OrgUser();
                orgUser.setOrgId(reqParam.getOrgId());
                orgUser.setUserId(userId);
                orgUserMapper.insert(orgUser);
            }
        }
        return listOrgUsers(reqParam.getOrgId());
    }
}
