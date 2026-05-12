package dn.spring.scaffold.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dn.spring.scaffold.system.entity.App;
import dn.spring.scaffold.system.manager.AppManager;
import dn.spring.scaffold.system.mapper.AppMapper;
import dn.spring.scaffold.system.pojo.query.ListAppQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AppManagerImpl implements AppManager {

    @Resource
    private AppMapper appMapper;

    @Override
    public App getById(Long appId) {
        return appMapper.selectById(appId);
    }

    @Override
    public App getByAppName(String appName) {
        if (!StringUtils.hasText(appName)) {
            return null;
        }
        return appMapper.selectOne(new LambdaQueryWrapper<App>()
                .eq(App::getAppName, appName)
                .last("limit 1"));
    }

    @Override
    public App getByAppCode(String appCode) {
        if (!StringUtils.hasText(appCode)) {
            return null;
        }
        return appMapper.selectOne(new LambdaQueryWrapper<App>()
                .eq(App::getAppCode, appCode)
                .last("limit 1"));
    }

    @Override
    public App getByClientId(String clientId) {
        if (!StringUtils.hasText(clientId)) {
            return null;
        }
        return appMapper.selectOne(new LambdaQueryWrapper<App>()
                .eq(App::getClientId, clientId)
                .last("limit 1"));
    }

    @Override
    public List<App> listApp(ListAppQuery query) {
        LambdaQueryWrapper<App> queryWrapper = new LambdaQueryWrapper<App>()
                .orderByAsc(App::getId);
        if (query != null && StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                    .like(App::getAppName, query.getKeyword())
                    .or()
                    .like(App::getAppCode, query.getKeyword())
                    .or()
                    .like(App::getClientId, query.getKeyword()));
        }
        return appMapper.selectList(queryWrapper);
    }

    @Override
    public App save(App app) {
        if (app.getId() == null) {
            appMapper.insert(app);
            return app;
        }
        appMapper.updateById(app);
        return appMapper.selectById(app.getId());
    }

    @Override
    public void deleteById(Long appId) {
        appMapper.deleteById(appId);
    }
}
