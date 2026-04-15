package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.system.entity.SysMenu;
import dn.spring.scaffold.system.manager.SysMenuManager;
import dn.spring.scaffold.system.mapper.SysMenuMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SysMenuManagerImpl implements SysMenuManager {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> listAll() {
        return sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .orderByAsc(SysMenu::getSortOrder)
                .orderByAsc(SysMenu::getId));
    }

    @Override
    public SysMenu getById(Long menuId) {
        return sysMenuMapper.selectById(menuId);
    }

    @Override
    public List<SysMenu> listByIds(Collection<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return Collections.emptyList();
        }
        return sysMenuMapper.selectBatchIds(menuIds);
    }

    @Override
    public SysMenu save(SysMenu menu) {
        if (menu.getId() == null) {
            sysMenuMapper.insert(menu);
            return menu;
        }
        sysMenuMapper.updateById(menu);
        return sysMenuMapper.selectById(menu.getId());
    }

    @Override
    public boolean existsChildren(Long menuId) {
        if (menuId == null) {
            return false;
        }
        Long count = sysMenuMapper.selectCount(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menuId));
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long menuId) {
        sysMenuMapper.deleteById(menuId);
    }
}
