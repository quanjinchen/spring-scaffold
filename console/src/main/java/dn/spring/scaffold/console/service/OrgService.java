package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.system.entity.Org;
import dn.spring.scaffold.system.mapper.OrgMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgService {

    private final OrgMapper orgMapper;

    private final OrgUserService orgUserService;

    public OrgService(OrgMapper orgMapper, OrgUserService orgUserService) {
        this.orgMapper = orgMapper;
        this.orgUserService = orgUserService;
    }

    public List<Org> tree() {
        return orgMapper.selectList(new LambdaQueryWrapper<Org>()
                .orderByAsc(Org::getSortOrder)
                .orderByAsc(Org::getId));
    }

    public PageResult<Org> page(PageQuery pageQuery) {
        Page<Org> page = orgMapper.selectPage(new Page<Org>(pageQuery.getPageNum(), pageQuery.getPageSize()),
                new LambdaQueryWrapper<Org>()
                        .orderByAsc(Org::getSortOrder)
                        .orderByAsc(Org::getId));
        return PageResult.of(page.getRecords(), page.getTotal(), pageQuery);
    }

    public Org detail(Long id) {
        Org org = orgMapper.selectOne(new LambdaQueryWrapper<Org>()
                .eq(Org::getId, id)
                .last("limit 1"));
        return org == null ? new Org() : org;
    }

    public Org save(Org org) {
        if (org.getId() == null) {
            orgMapper.insert(org);
            return org;
        }
        orgMapper.updateById(org);
        return org;
    }

    public java.util.List<?> listOrgUsers(Long orgId) {
        return orgUserService.listOrgUsers(orgId);
    }

    public java.util.List<?> grantOrgUsers(GrantOrgUsersReqParam reqParam) {
        return orgUserService.grantOrgUsers(reqParam);
    }
}
