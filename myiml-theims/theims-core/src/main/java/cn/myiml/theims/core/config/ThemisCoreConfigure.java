package cn.myiml.theims.core.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

/**
 * 配置themis
 * @author yangzhou
 */
@Configuration
@ImportResource(value = "classpath:META-INF/spring-aop-verify.xml")
public class ThemisCoreConfigure implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {

    }
}
