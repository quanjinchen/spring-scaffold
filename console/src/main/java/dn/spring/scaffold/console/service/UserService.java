package dn.spring.scaffold.console.service;

import dn.spring.scaffold.common.page.PageData;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.req.CreateUserReqParam;
import dn.spring.scaffold.console.pojo.req.DeleteUserReqParam;
import dn.spring.scaffold.console.pojo.req.GetUserByIdReqParam;
import dn.spring.scaffold.console.pojo.req.ListUserReqParam;
import dn.spring.scaffold.console.pojo.req.ResetUserPasswordReqParam;
import dn.spring.scaffold.console.pojo.req.UpdateUserReqParam;
import dn.spring.scaffold.console.pojo.resp.UserDTO;

public interface UserService {

    RespInfo<Void> createUser(CreateUserReqParam createUserReqParam);

    RespInfo<UserDTO> getUserById(GetUserByIdReqParam getUserByIdReqParam);

    RespInfo<PageData<UserDTO>> listUser(ListUserReqParam listUserReqParam);

    RespInfo<Void> updateUser(UpdateUserReqParam updateUserReqParam);

    RespInfo<Void> deleteUser(DeleteUserReqParam deleteUserReqParam);

    RespInfo<Void> resetUserPassword(ResetUserPasswordReqParam resetUserPasswordReqParam);
}
