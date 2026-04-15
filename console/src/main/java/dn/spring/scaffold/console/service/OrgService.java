package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.system.entity.Org;

import java.util.List;

public interface OrgService {

    List<Org> tree();

    RespInfo<List<Org>> treeResp();

    PageData<Org> page(PageReqParam reqParam);

    RespInfo<PageData<Org>> pageResp(PageReqParam reqParam);

    Org detail(Long id);

    RespInfo<Org> detailResp(Long id);

    Org save(Org org);

    RespInfo<Org> saveResp(Org org);

    Org update(Org org);

    RespInfo<Org> updateResp(Org org);

    void delete(Long orgId);

    RespInfo<Void> deleteResp(Long orgId);

    List<?> listOrgUsers(Long orgId);

    RespInfo<List<?>> listOrgUsersResp(Long orgId);

    List<?> grantOrgUsers(GrantOrgUsersReqParam reqParam);

    RespInfo<List<?>> grantOrgUsersResp(GrantOrgUsersReqParam reqParam);
}
