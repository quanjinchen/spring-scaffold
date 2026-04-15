package dn.spring.scaffold.console.converter;

import dn.spring.scaffold.common.converter.BaseConverter;
import dn.spring.scaffold.console.pojo.req.ListUserReqParam;
import dn.spring.scaffold.console.pojo.resp.UserDTO;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.pojo.query.ListUserQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConverter extends BaseConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mapping(target = "phone", source = "phone", qualifiedByName = TO_PLAIN_TEXT)
    UserDTO convert(User user);

    List<UserDTO> convert(List<User> users);

    @Mapping(target = "phone", source = "phone", qualifiedByName = TO_ENCRYPT_FIELD)
    User convert(UserDTO userDTO);

    ListUserQuery convert(ListUserReqParam reqParam);
}
