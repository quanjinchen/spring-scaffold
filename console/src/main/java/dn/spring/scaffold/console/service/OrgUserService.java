package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GetOrgUserListReqParam;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgUserInfo;

import java.util.List;

public interface OrgUserService {

    RespInfo<List<OrgUserInfo>> listOrgUser(GetOrgUserListReqParam reqParam);

    RespInfo<List<OrgUserInfo>> grantOrgUsers(GrantOrgUsersReqParam reqParam);
}
