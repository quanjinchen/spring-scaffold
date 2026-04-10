package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.OrgUser;

import java.util.List;

public interface OrgUserManager {

    List<OrgUser> listByOrgId(Long orgId);

    List<OrgUser> listByUserId(Long userId);

    void replaceOrgUsers(Long orgId, List<Long> userIds);
}
