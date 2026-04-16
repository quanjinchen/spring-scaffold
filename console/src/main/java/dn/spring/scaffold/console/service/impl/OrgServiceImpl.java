package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateOrgReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteOrgReqParam;
import dn.spring.scaffold.console.pojo.req.GetOrgByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListOrgReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateOrgReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgDTO;
import dn.spring.scaffold.console.service.OrgService;
import dn.spring.scaffold.system.entity.Org;
import dn.spring.scaffold.system.manager.OrgManager;
import dn.spring.scaffold.system.manager.OrgUserManager;
import dn.spring.scaffold.system.manager.UserManager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {

    @Resource
    private OrgManager orgManager;
    @Resource
    private OrgUserManager orgUserManager;
    @Resource
    private UserManager userManager;

    @Override
    public RespInfo<List<OrgDTO>> listAllOrgTree() {
        List<Org> orgList = orgManager.listAll();
        List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>(orgList.size());
        for (Org org : orgList) {
            orgDTOList.add(toOrgDTO(org));
        }
        return RespInfo.success(orgDTOList);
    }

    @Override
    public RespInfo<PageData<OrgDTO>> listOrg(ListOrgReqParam reqParam) {
        PageHelper.startPage(reqParam.getPageNum(), reqParam.getPageSize());
        List<Org> orgList = orgManager.listAll();
        List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>(orgList.size());
        for (Org org : orgList) {
            orgDTOList.add(toOrgDTO(org));
        }

        PageData<OrgDTO> pageData = new PageData<OrgDTO>();
        pageData.setTotal(new PageInfo<Org>(orgList).getTotal());
        pageData.setRecords(orgDTOList);
        pageData.setPageNum(reqParam.getPageNum());
        pageData.setPageSize(reqParam.getPageSize());
        return RespInfo.success(pageData);
    }

    @Override
    public RespInfo<OrgDTO> getOrgById(GetOrgByIdReqParam reqParam) {
        Org org = orgManager.getById(reqParam.getOrgId());
        ResultCode.ORG_NOT_FOUND.assertNotNull(org);
        return RespInfo.success(toOrgDTO(org));
    }

    @Override
    public RespInfo<OrgDTO> createOrg(CreateOrgReqParam reqParam) {
        Org org = new Org();
        org.setParentId(reqParam.getParentId());
        org.setOrgCode(reqParam.getOrgCode());
        org.setName(reqParam.getName());
        org.setLeaderName(reqParam.getLeaderName());
        org.setSortOrder(reqParam.getSortOrder() == null ? 0 : reqParam.getSortOrder());
        org.setStatus(reqParam.getStatus() == null ? 1 : reqParam.getStatus());
        return RespInfo.created(toOrgDTO(orgManager.save(org)));
    }

    @Override
    public RespInfo<OrgDTO> updateOrg(UpdateOrgReqParam reqParam) {
        Org existedOrg = orgManager.getById(reqParam.getId());
        ResultCode.ORG_NOT_FOUND.assertNotNull(existedOrg);

        existedOrg.setParentId(reqParam.getParentId());
        existedOrg.setOrgCode(reqParam.getOrgCode());
        existedOrg.setName(reqParam.getName());
        existedOrg.setLeaderName(reqParam.getLeaderName());
        existedOrg.setSortOrder(reqParam.getSortOrder() == null ? 0 : reqParam.getSortOrder());
        existedOrg.setStatus(reqParam.getStatus() == null ? 1 : reqParam.getStatus());
        return RespInfo.success(toOrgDTO(orgManager.save(existedOrg)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespInfo<Void> deleteOrg(DeleteOrgReqParam reqParam) {
        Long orgId = reqParam.getOrgId();
        Org org = orgManager.getById(orgId);
        ResultCode.ORG_NOT_FOUND.assertNotNull(org);
        ResultCode.DELETE_ORG_FAILED_BECAUSE_HAS_CHILD.assertIsFalse(orgManager.existsChildren(orgId));
        ResultCode.DELETE_ORG_FAILED_BECAUSE_HAS_USER.assertIsFalse(orgUserManager.existsByOrgId(orgId) || userManager.existsByOrgId(orgId));
        orgUserManager.deleteByOrgId(orgId);
        orgManager.deleteById(orgId);
        return RespInfo.success();
    }

    private OrgDTO toOrgDTO(Org org) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setId(org.getId());
        orgDTO.setParentId(org.getParentId());
        orgDTO.setOrgCode(org.getOrgCode());
        orgDTO.setName(org.getName());
        orgDTO.setLeaderName(org.getLeaderName());
        orgDTO.setSortOrder(org.getSortOrder());
        orgDTO.setStatus(org.getStatus());
        orgDTO.setCreateTime(org.getCreateTime());
        orgDTO.setUpdateTime(org.getUpdateTime());
        return orgDTO;
    }
}
