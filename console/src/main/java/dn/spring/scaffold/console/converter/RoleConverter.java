package dn.spring.scaffold.console.converter;

import dn.spring.scaffold.common.converter.BaseConverter;
import dn.spring.scaffold.console.pojo.req.ListRoleReqParam;
import dn.spring.scaffold.console.pojo.resp.RoleDTO;
import dn.spring.scaffold.system.entity.SysRole;
import dn.spring.scaffold.system.pojo.query.ListRoleQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConverter extends BaseConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    RoleDTO convert(SysRole role);

    List<RoleDTO> convert(List<SysRole> roles);

    ListRoleQuery convert(ListRoleReqParam reqParam);
}
