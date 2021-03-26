package org.themis.check.config;

import org.themis.check.interceptor.ParameterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置
 * @author YangZhou
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

    @Resource
    private ParameterInterceptor parameterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(parameterInterceptor);
        registration.addPathPatterns("/**");

    }
}
