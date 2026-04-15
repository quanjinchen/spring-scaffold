package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.Org;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface OrgManager {

    List<Org> listAll();

    Page<Org> page(PageReqParam reqParam);

    Org getById(Long orgId);

    Org save(Org org);

    boolean existsChildren(Long orgId);

    void deleteById(Long orgId);
}
