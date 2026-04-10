package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.service.OrgService;
import dn.spring.scaffold.console.service.OrgUserService;
import dn.spring.scaffold.system.entity.Org;
import dn.spring.scaffold.system.manager.OrgManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {

    @Resource
    private OrgManager orgManager;
    @Resource
    private OrgUserService orgUserService;

    @Override
    public List<Org> tree() {
        return orgManager.listAll();
    }

    @Override
    public PageResult<Org> page(PageQuery pageQuery) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Org> page = orgManager.page(pageQuery);
        return PageResult.of(page.getRecords(), page.getTotal(), pageQuery);
    }

    @Override
    public Org detail(Long id) {
        Org org = orgManager.getById(id);
        return org == null ? new Org() : org;
    }

    @Override
    public Org save(Org org) {
        Org latestOrg = orgManager.save(org);
        return latestOrg == null ? new Org() : latestOrg;
    }

    @Override
    public List<?> listOrgUsers(Long orgId) {
        return orgUserService.listOrgUsers(orgId);
    }

    @Override
    public List<?> grantOrgUsers(GrantOrgUsersReqParam reqParam) {
        return orgUserService.grantOrgUsers(reqParam);
    }
}
