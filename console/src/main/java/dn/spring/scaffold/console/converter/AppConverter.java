package dn.spring.scaffold.console.converter;

import dn.spring.scaffold.common.converter.BaseConverter;
import dn.spring.scaffold.console.pojo.req.ListAppReqParam;
import dn.spring.scaffold.console.pojo.resp.AppDTO;
import dn.spring.scaffold.system.entity.App;
import dn.spring.scaffold.system.pojo.query.ListAppQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppConverter extends BaseConverter {

    AppConverter INSTANCE = Mappers.getMapper(AppConverter.class);

    AppDTO convert(App app);

    List<AppDTO> convert(List<App> appList);

    ListAppQuery convert(ListAppReqParam reqParam);
}
