package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.system.entity.Org;

import java.util.List;

public interface OrgService {

    List<Org> tree();

    PageResult<Org> page(PageQuery pageQuery);

    Org detail(Long id);

    Org save(Org org);

    List<?> listOrgUsers(Long orgId);

    List<?> grantOrgUsers(GrantOrgUsersReqParam reqParam);
}
