package dn.spring.scaffold.console.converter;

import dn.spring.scaffold.common.converter.BaseConverter;
import dn.spring.scaffold.console.pojo.resp.UserDetailResp;
import dn.spring.scaffold.system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConverter extends BaseConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mapping(target = "phone", source = "phone", qualifiedByName = TO_PLAIN_TEXT)
    UserDetailResp convert(User user);

    List<UserDetailResp> convert(List<User> users);

    @Mapping(target = "phone", source = "phone", qualifiedByName = TO_ENCRYPT_FIELD)
    User convert(UserDetailResp userDetailResp);
}
