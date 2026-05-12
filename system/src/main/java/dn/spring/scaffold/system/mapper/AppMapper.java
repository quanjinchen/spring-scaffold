package dn.spring.scaffold.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dn.spring.scaffold.system.entity.App;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppMapper extends BaseMapper<App> {
}
