package dn.spring.scaffold.system.manager.impl;

import dn.spring.scaffold.system.entity.OrgUser;
import dn.spring.scaffold.system.manager.OrgUserManager;
import dn.spring.scaffold.system.mapper.OrgUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Component
public class OrgUserManagerImpl implements OrgUserManager {

    @Resource
    private OrgUserMapper orgUserMapper;

    @Override
    public List<OrgUser> listByOrgId(Long orgId) {
        List<OrgUser> orgUsers = orgUserMapper.selectList(
                new LambdaQueryWrapper<OrgUser>().eq(OrgUser::getOrgId, orgId)
        );
        return orgUsers == null ? Collections.emptyList() : orgUsers;
    }

    @Override
    public List<OrgUser> listByUserId(Long userId) {
        List<OrgUser> orgUsers = orgUserMapper.selectList(
                new LambdaQueryWrapper<OrgUser>().eq(OrgUser::getUserId, userId)
        );
        return orgUsers == null ? Collections.emptyList() : orgUsers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replaceOrgUsers(Long orgId, List<Long> userIds) {
        orgUserMapper.delete(new LambdaQueryWrapper<OrgUser>().eq(OrgUser::getOrgId, orgId));
        if (userIds == null || userIds.isEmpty()) {
            return;
        }
        for (Long userId : userIds) {
            OrgUser orgUser = new OrgUser();
            orgUser.setOrgId(orgId);
            orgUser.setUserId(userId);
            orgUserMapper.insert(orgUser);
        }
    }
}
