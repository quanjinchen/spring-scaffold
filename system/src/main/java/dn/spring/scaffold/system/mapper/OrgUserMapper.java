package dn.spring.scaffold.system.mapper;

import dn.spring.scaffold.system.entity.OrgUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgUserMapper extends BaseMapper<OrgUser> {
}
