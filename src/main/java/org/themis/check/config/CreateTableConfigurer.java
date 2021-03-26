package org.themis.check.config;

import lombok.extern.slf4j.Slf4j;
import org.themis.check.dao.CheckRouteConfigMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化创建规则表
 * @author yangzhou
 */
@Component
@Order(1)
@Slf4j
public class CreateTableConfigurer implements ApplicationRunner {

    @Value("${paramCheck.filter:false}")
    private Boolean filter;

    @Resource
    private CheckRouteConfigMapper checkRouteConfigMapper;

    public void initSqlTable(){
        if (filter){
            checkRouteConfigMapper.updateTableParameter();
            checkRouteConfigMapper.updateTableRoute();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initSqlTable();
    }
}
