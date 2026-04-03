package dn.spring.scaffold.framework.datapermission;

import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataPermissionConfig {

    @Bean
    public DataPermissionAnnotationInterceptor dataPermissionAnnotationInterceptor() {
        return new DataPermissionAnnotationInterceptor();
    }

    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor(DataPermissionAnnotationInterceptor interceptor) {
        return new DataPermissionAnnotationAdvisor(interceptor);
    }

    @Bean
    public MultiDataPermissionHandler multiDataPermissionHandler() {
        return new MultiDataPermissionHandler() {
            @Override
            public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
                return null;
            }
        };
    }
}
