package dn.spring.scaffold.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaPermissionImpl implements StpInterface {

    private final PermissionService permissionService;

    public SaPermissionImpl(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return permissionService.getPermissionList(Long.valueOf(String.valueOf(loginId)));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return permissionService.getRoleCodeList(Long.valueOf(String.valueOf(loginId)));
    }
}
