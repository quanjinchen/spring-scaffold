package dn.spring.scaffold.system.manager;

import dn.spring.scaffold.system.entity.OrgUser;

import java.util.List;

public interface OrgUserManager {

    List<OrgUser> listByOrgId(Long orgId);

    List<OrgUser> listByUserId(Long userId);

    boolean existsByUserId(Long userId);

    boolean existsByOrgId(Long orgId);

    void replaceOrgUsers(Long orgId, List<Long> userIds);

    void save(OrgUser orgUser);

    void deleteByUserId(Long userId);

    void deleteByOrgId(Long orgId);
}
