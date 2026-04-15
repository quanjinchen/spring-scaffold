package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageQuery;
import dn.spring.scaffold.common.page.PageResult;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;

public interface UserService {

    PageResult<UserDetailResp> page(PageQuery pageQuery);

    RespInfo<PageResult<UserDetailResp>> pageResp(PageQuery pageQuery);

    UserDetailResp detail(Long id);

    RespInfo<UserDetailResp> detailResp(Long id);

    UserDetailResp save(UserDetailResp userDetailResp);

    RespInfo<UserDetailResp> saveResp(UserDetailResp userDetailResp);

    UserDetailResp update(UserDetailResp userDetailResp);

    RespInfo<UserDetailResp> updateResp(UserDetailResp userDetailResp);

    void delete(Long userId);

    RespInfo<Void> deleteResp(Long userId);

    String resetPassword(Long userId);

    RespInfo<String> resetPasswordResp(Long userId);
}
