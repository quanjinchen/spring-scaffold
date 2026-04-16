package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateOrgReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteOrgReqParam;
import dn.spring.scaffold.console.pojo.req.GetOrgByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListOrgReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateOrgReqParam;
import dn.spring.scaffold.console.pojo.resp.OrgDTO;

import java.util.List;

public interface OrgService {

    RespInfo<List<OrgDTO>> listAllOrgTree();

    RespInfo<PageData<OrgDTO>> listOrg(ListOrgReqParam reqParam);

    RespInfo<OrgDTO> getOrgById(GetOrgByIdReqParam reqParam);

    RespInfo<OrgDTO> createOrg(CreateOrgReqParam reqParam);

    RespInfo<OrgDTO> updateOrg(UpdateOrgReqParam reqParam);

    RespInfo<Void> deleteOrg(DeleteOrgReqParam reqParam);
}
