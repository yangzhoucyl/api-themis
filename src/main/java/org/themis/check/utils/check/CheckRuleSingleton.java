package org.themis.check.utils.check;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 双检锁实现校验参数单列类
 * @author YangZhou
 */
public class CheckRuleSingleton {

    private static volatile CheckRuleSingleton checkRuleSingleton;

    private ConcurrentHashMap<String, List<CheckRulesConfigModel>> rules = new ConcurrentHashMap<>();

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

    public ConcurrentHashMap<String, List<CheckRulesConfigModel>> getRules() {
        return rules;
    }

    public synchronized void setRules(ConcurrentHashMap<String, List<CheckRulesConfigModel>> rules) {
        this.rules = rules;
    }
}
