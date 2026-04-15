package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.page.PageUtils;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.GrantOrgUsersReqParam;
import dn.spring.scaffold.console.service.OrgService;
import dn.spring.scaffold.console.service.OrgUserService;
import dn.spring.scaffold.system.entity.Org;
import dn.spring.scaffold.system.manager.OrgManager;
import dn.spring.scaffold.system.manager.OrgUserManager;
import dn.spring.scaffold.system.manager.UserManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {

    @Resource
    private OrgManager orgManager;
    @Resource
    private OrgUserService orgUserService;
    @Resource
    private OrgUserManager orgUserManager;
    @Resource
    private UserManager userManager;

    @Override
    public List<Org> tree() {
        return orgManager.listAll();
    }

    @Override
    public RespInfo<List<Org>> treeResp() {
        return RespInfo.success(tree());
    }

    @Override
    public PageData<Org> page(PageReqParam reqParam) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Org> page = orgManager.page(reqParam);
        return PageUtils.of(page);
    }

    @Override
    public RespInfo<PageData<Org>> pageResp(PageReqParam reqParam) {
        return RespInfo.success(page(reqParam));
    }

    @Override
    public Org detail(Long id) {
        Org org = orgManager.getById(id);
        return org == null ? new Org() : org;
    }

    @Override
    public RespInfo<Org> detailResp(Long id) {
        return RespInfo.success(detail(id));
    }

    @Override
    public Org save(Org org) {
        Org latestOrg = orgManager.save(org);
        return latestOrg == null ? new Org() : latestOrg;
    }

    @Override
    public RespInfo<Org> saveResp(Org org) {
        return RespInfo.created(save(org));
    }

    @Override
    public Org update(Org org) {
        return save(org);
    }

    @Override
    public RespInfo<Org> updateResp(Org org) {
        return RespInfo.success(update(org));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long orgId) {
        Org org = orgManager.getById(orgId);
        ResultCode.ORG_NOT_FOUND.assertNotNull(org);
        ResultCode.DELETE_ORG_FAILED_BECAUSE_HAS_CHILD.assertIsFalse(orgManager.existsChildren(orgId));
        ResultCode.DELETE_ORG_FAILED_BECAUSE_HAS_USER.assertIsFalse(orgUserManager.existsByOrgId(orgId) || userManager.existsByOrgId(orgId));
        orgUserManager.deleteByOrgId(orgId);
        orgManager.deleteById(orgId);
    }

    @Override
    public RespInfo<Void> deleteResp(Long orgId) {
        delete(orgId);
        return RespInfo.success();
    }

    @Override
    public List<?> listOrgUsers(Long orgId) {
        return orgUserService.listOrgUsers(orgId);
    }

    @Override
    public RespInfo<List<?>> listOrgUsersResp(Long orgId) {
        return RespInfo.success(listOrgUsers(orgId));
    }

    @Override
    public List<?> grantOrgUsers(GrantOrgUsersReqParam reqParam) {
        return orgUserService.grantOrgUsers(reqParam);
    }

    @Override
    public RespInfo<List<?>> grantOrgUsersResp(GrantOrgUsersReqParam reqParam) {
        return RespInfo.success(grantOrgUsers(reqParam));
    }
}
