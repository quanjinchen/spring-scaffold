package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;
import dn.spring.scaffold.system.entity.SysMenu;

import java.util.List;

public interface MenuService {

    List<SysMenu> listAll();

    List<MenuTreeNode> tree();

    RespInfo<List<MenuTreeNode>> treeResp();

    List<MenuTreeNode> treeByUserId(Long userId);

    PageData<SysMenu> page(PageReqParam reqParam);

    RespInfo<PageData<SysMenu>> pageResp(PageReqParam reqParam);

    SysMenu detail(Long id);

    RespInfo<SysMenu> detailResp(Long id);

    SysMenu save(SysMenu menu);

    RespInfo<SysMenu> saveResp(SysMenu menu);

    SysMenu update(SysMenu menu);

    RespInfo<SysMenu> updateResp(SysMenu menu);

    void delete(Long menuId);

    RespInfo<Void> deleteResp(Long menuId);

    List<Long> listRoleMenuIds(Long roleId);
}
