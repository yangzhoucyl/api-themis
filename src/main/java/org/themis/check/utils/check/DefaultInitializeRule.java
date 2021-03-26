package org.themis.check.utils.check;

import org.themis.check.dao.CheckRouteConfigMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 默认初始化规则实现
 * @author YangZhou
 */
@Component
@Order(value = 1)
public class DefaultInitializeRule implements InitializeRule, ApplicationRunner {


    @Resource
    private CheckRouteConfigMapper routeConfigMapper;

    @Override
    public ConcurrentHashMap<String, List<CheckRulesConfigModel>> initializationRule() {
        CheckRuleSingleton ruleSingleton = CheckRuleSingleton.getInstance();
        List<CheckRulesConfigModel> rule = routeConfigMapper.findAllRouteAndRule();;
        Map<String, List<CheckRulesConfigModel>> rules = rule.stream().collect(Collectors.groupingBy(CheckRulesConfigModel::getRoute));
        ConcurrentHashMap<String,List<CheckRulesConfigModel>> rulesCache = new ConcurrentHashMap<>(rules);
        ruleSingleton.setRules(rulesCache);
        return rulesCache;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializationRule();
    }

}
