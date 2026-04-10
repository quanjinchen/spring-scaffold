package dn.spring.scaffold.console.service;

import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgUserInfo;

import java.util.List;

public interface OrgUserService {

    List<OrgUserInfo> listOrgUsers(Long orgId);

    List<OrgUserInfo> listUserOrgsUsers(Long userId);

    List<OrgUserInfo> grantOrgUsers(GrantOrgUsersReqParam reqParam);
}
