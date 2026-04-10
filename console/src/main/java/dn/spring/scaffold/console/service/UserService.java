package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;

public interface UserService {

    PageResult<UserDetailResp> page(PageQuery pageQuery);

    UserDetailResp detail(Long id);

    UserDetailResp save(UserDetailResp userDetailResp);

    String resetPassword(Long userId);
}
