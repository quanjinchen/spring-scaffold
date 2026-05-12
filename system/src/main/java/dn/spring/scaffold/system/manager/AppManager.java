package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.App;
import dn.spring.scaffold.system.pojo.query.ListAppQuery;

import java.util.List;

public interface AppManager {

    App getById(Long appId);

    App getByAppName(String appName);

    App getByAppCode(String appCode);

    App getByClientId(String clientId);

    List<App> listApp(ListAppQuery query);

    App save(App app);

    void deleteById(Long appId);
}
