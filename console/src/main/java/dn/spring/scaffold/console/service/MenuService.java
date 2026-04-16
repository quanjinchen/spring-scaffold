package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateMenuReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteMenuReqParam;
import dn.spring.scaffold.console.pojo.req.GetMenuByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListMenuReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateMenuReqParam;
import dn.spring.scaffold.console.pojo.resp.MenuDTO;
import dn.spring.scaffold.console.pojo.resp.MenuTreeNode;

import java.util.List;

public interface MenuService {

    RespInfo<List<MenuTreeNode>> listAllMenuTree();

    RespInfo<PageData<MenuDTO>> listMenu(ListMenuReqParam reqParam);

    RespInfo<MenuDTO> getMenuById(GetMenuByIdReqParam reqParam);

    RespInfo<MenuDTO> createMenu(CreateMenuReqParam reqParam);

    RespInfo<MenuDTO> updateMenu(UpdateMenuReqParam reqParam);

    RespInfo<Void> deleteMenu(DeleteMenuReqParam reqParam);

    List<MenuTreeNode> listMenuTreeByUserId(Long userId);
}
