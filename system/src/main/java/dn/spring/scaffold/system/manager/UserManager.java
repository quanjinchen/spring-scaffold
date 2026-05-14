package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.common.page.PageReqParam;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.pojo.query.ListUserQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

public interface UserManager {

    Page<User> page(PageReqParam reqParam);

    User getById(Long userId);

    User getByAccount(String account);

    User getByUsername(String username);

    User getByEmail(String email);

    User getByPhone(String phone);

    User getByIdCard(String idCard);

    List<User> listByIds(Collection<Long> userIds);

    List<User> listUsersWithFaceFeature();

    List<User> listUsers(ListUserQuery query);

    Page<User> page(PageReqParam reqParam, ListUserQuery query);

    User save(User user);

    void deleteById(Long userId);
}
