package dn.spring.scaffold.framework.mybatisplus.config;

import dn.spring.scaffold.common.encryptor.DefaultEncryptor;
import dn.spring.scaffold.common.encryptor.Encryptor;
import dn.spring.scaffold.framework.mybatisplus.typehandler.AutoEnumTypeHandler;
import dn.spring.scaffold.framework.mybatisplus.typehandler.EncryptTypeHandler;
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
@MapperScan("dn.spring.scaffold.*.mapper")
public class MyBatisPlusConfig {

    public static final String CONFIG_ENCRYPTOR_KEY = "project.encryptor.key";

    public static final String CONFIG_ENCRYPTOR_IV = "project.encryptor.iv";

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultEncryptor defaultEncryptor(Environment environment) {
        String key = environment.getProperty(CONFIG_ENCRYPTOR_KEY);
        String iv = environment.getProperty(CONFIG_ENCRYPTOR_IV);
        if (!StringUtils.hasText(key)) {
            throw new IllegalStateException(CONFIG_ENCRYPTOR_KEY + " configuration cannot be empty");
        }
        if (!StringUtils.hasText(iv)) {
            throw new IllegalStateException(CONFIG_ENCRYPTOR_IV + " configuration cannot be empty");
        }
        return new DefaultEncryptor(key, iv);
    }

    @Bean
    public SqlSessionFactoryBeanCustomizer sqlSessionFactoryBeanCustomizer(Encryptor encryptor) {
        return factoryBean -> {
            factoryBean.setTypeHandlers(new EncryptTypeHandler(encryptor));
            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setDefaultEnumTypeHandler(AutoEnumTypeHandler.class);
            factoryBean.setConfiguration(configuration);
        };
    }
}
