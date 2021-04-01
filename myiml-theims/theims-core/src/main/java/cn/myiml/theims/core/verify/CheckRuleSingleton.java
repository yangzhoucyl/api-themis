package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.model.CheckRulesConfigModel;
import cn.myiml.theims.core.rule.load.LoadVerifyRule;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 双检锁实现校验参数单列类
 * @author YangZhou
 */
@Component
public class CheckRuleSingleton {

    private static volatile CheckRuleSingleton checkRuleSingleton;

    private final Cache<String, List<CheckRulesConfigModel>> cache = CacheBuilder.newBuilder()
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

    public synchronized void setRules(LoadVerifyRule<CheckRulesConfigModel> verifyRule) {
        this.cache.putAll(verifyRule.loadAllRules());
    }


    @SneakyThrows
    public List<CheckRulesConfigModel> getVal(String key, LoadVerifyRule<CheckRulesConfigModel> loadVerifyRule){
       return cache.get(key, () -> addCache(key, loadVerifyRule));
    }


    public List<CheckRulesConfigModel> addCache(String route, LoadVerifyRule<CheckRulesConfigModel> loadVerifyRule){
        List<CheckRulesConfigModel> rulesConfigModels = loadVerifyRule.loadRule(route);
        if (rulesConfigModels.isEmpty()){
            return new ArrayList<>();
        }
        return rulesConfigModels;
    }

    public Cache<String, List<CheckRulesConfigModel>> addCache(String route,List<CheckRulesConfigModel> newCache){
        cache.put(route, newCache);
        return cache;
    }
}
