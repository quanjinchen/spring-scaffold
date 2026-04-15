package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

public interface SysRoleManager {

    Page<SysRole> page(PageReqParam reqParam);

    SysRole getById(Long roleId);

    List<SysRole> listByIds(Collection<Long> roleIds);

    SysRole save(SysRole role);

    void deleteById(Long roleId);
}
