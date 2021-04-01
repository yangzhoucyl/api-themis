package org.themis.check.utils.check;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.themis.check.dao.CheckRouteConfigMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 双检锁实现校验参数单列类
 * @author YangZhou
 */
@Component
public class CheckRuleSingleton {

    @Resource
    private CheckRouteConfigMapper routeConfigMapper;

    private static volatile CheckRuleSingleton checkRuleSingleton;

    private Cache<String, List<CheckRulesConfigModel>> cache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .build();

    private CheckRuleSingleton(){
    }

    public static CheckRuleSingleton getInstance(){
        if (null == checkRuleSingleton){
            synchronized (CheckRuleSingleton.class){
                if (null == checkRuleSingleton){
                    checkRuleSingleton = new CheckRuleSingleton();
                }
            }
        }
        return checkRuleSingleton;
    }

    public Cache<String, List<CheckRulesConfigModel>> getRules() {
        return cache;
    }

    public synchronized void setRules(ConcurrentHashMap<String, List<CheckRulesConfigModel>> rules) {
        this.cache.putAll(rules);
    }


    @SneakyThrows
    public List<CheckRulesConfigModel> getVal(String key){
       return cache.get(key, () -> addCache(key));
    }


    public List<CheckRulesConfigModel> addCache(String route){
        List<CheckRulesConfigModel> rulesConfigModels = routeConfigMapper.findAllRouteAndRuleByRoute(route);
        if (rulesConfigModels.isEmpty()){
            return new ArrayList<>();
        }
        return rulesConfigModels;
    }

    public Cache<String, List<CheckRulesConfigModel>> addCache(String route,List<CheckRulesConfigModel> newCache){
        cache.put(route, newCache);
        return cache;
    }

    public void setRouteConfigMapper(CheckRouteConfigMapper routeConfigMapper) {
        this.routeConfigMapper = routeConfigMapper;
    }
}
