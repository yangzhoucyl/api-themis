package org.themis.check.utils.check;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 双检锁实现校验参数单列类
 * @author YangZhou
 */
public class CheckRuleSingleton {

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
}
