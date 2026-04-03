package dn.spring.scaffold.framework.datapermission;

import dn.spring.scaffold.common.annotation.DataPermission;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotatedElementUtils;

public class DataPermissionAnnotationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DataPermission dataPermission = AnnotatedElementUtils.findMergedAnnotation(invocation.getMethod(), DataPermission.class);
        if (dataPermission == null) {
            dataPermission = AnnotatedElementUtils.findMergedAnnotation(invocation.getThis().getClass(), DataPermission.class);
        }
        if (dataPermission == null || !dataPermission.enabled()) {
            return invocation.proceed();
        }
        DataPermissionContextHolder.setPolicy(dataPermission.value());
        try {
            return invocation.proceed();
        } finally {
            DataPermissionContextHolder.clear();
        }
    }
}
