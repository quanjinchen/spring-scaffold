package dn.spring.scaffold.system.mapper;

import dn.spring.scaffold.system.entity.Org;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgMapper extends BaseMapper<Org> {
}
