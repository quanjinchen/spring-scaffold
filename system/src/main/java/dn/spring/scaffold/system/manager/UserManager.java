package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.system.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

public interface UserManager {

    Page<User> page(PageQuery pageQuery);

    User getById(Long userId);

    User getByAccount(String account);

    List<User> listByIds(Collection<Long> userIds);

    boolean existsByOrgId(Long orgId);

    User save(User user);

    void deleteById(Long userId);
}
