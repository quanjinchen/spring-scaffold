package dn.spring.scaffold.framework.satoken;

import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.common.utils.JsonUtils;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SaTokenConfig {

    private final ServletFilterConfig servletFilterConfig;

    public SaTokenConfig(ServletFilterConfig servletFilterConfig) {
        this.servletFilterConfig = servletFilterConfig;
    }

    @Bean
    public StpLogic stpLogic() {
        return new StpLogicJwtForSimple();
    }

    @Bean
    public SaTokenDao saTokenDao() {
        return new SaTokenDaoRedisJackson();
    }

    @Bean
    public SaServletFilter saServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude(servletFilterConfig.getExcludePaths())
                .setAuth(obj -> SaRouter.match("/**", StpUtil::checkLogin))
                .setError(error -> {
                    log.warn("sa-token filter error: {}", error.getMessage(), error);
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    return JsonUtils.toJson(RespInfo.failed(ResultCode.UNAUTHORIZED));
                });
    }
}
