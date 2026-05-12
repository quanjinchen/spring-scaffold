package dn.spring.scaffold.framework.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import dn.spring.scaffold.framework.satoken.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {

    private static final Long DEFAULT_OPERATOR_ID = 0L;

    @Resource
    private LoginUserContext loginUserContext;

    @Override
    public void insertFill(MetaObject metaObject) {
        Long operatorId = getCurrentOperatorId();
        if (this.getFieldValByName("createBy", metaObject) == null) {
            this.strictInsertFill(metaObject, "createBy", Long.class, operatorId);
        }
        if (this.getFieldValByName("updateBy", metaObject) == null) {
            this.strictInsertFill(metaObject, "updateBy", Long.class, operatorId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (this.getFieldValByName("updateBy", metaObject) == null) {
            this.strictUpdateFill(metaObject, "updateBy", Long.class, getCurrentOperatorId());
        }
    }

    private Long getCurrentOperatorId() {
        Long loginUserId = loginUserContext.getLoginUserId();
        if (loginUserId == null) {
            return DEFAULT_OPERATOR_ID;
        }
        return loginUserId;
    }
}
