package cn.myiml.theims.core.verify;

import cn.myiml.theims.core.enums.PatternEnum;
import cn.myiml.theims.core.model.VerifyRulesConfigModel;
import cn.myiml.theims.core.rule.load.LoadVerifyRule;
import cn.myiml.theims.core.verify.annotation.VerifyField;
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
public class VerifyRuleSingleton {

    private static volatile VerifyRuleSingleton verifyRuleSingleton;

    private final Cache<String, List<VerifyRulesConfigModel>> cache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .build();

    private VerifyRuleSingleton(){
    }

    public static VerifyRuleSingleton getInstance(){
        if (null == verifyRuleSingleton){
            synchronized (VerifyRuleSingleton.class){
                if (null == verifyRuleSingleton){
                    verifyRuleSingleton = new VerifyRuleSingleton();
                }
            }
        }
        return verifyRuleSingleton;
    }

    public Cache<String, List<VerifyRulesConfigModel>> getRules() {
        return cache;
    }

    public synchronized void setRules(LoadVerifyRule<VerifyRulesConfigModel> verifyRule) {
        this.cache.putAll(verifyRule.loadAllRules());
    }


    @SneakyThrows
    public List<VerifyRulesConfigModel> getVal(String key, LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule){
       return cache.get(key, () -> addCache(key, loadVerifyRule));
    }

    public List<VerifyRulesConfigModel> addCache(String route, LoadVerifyRule<VerifyRulesConfigModel> loadVerifyRule){
        List<VerifyRulesConfigModel> rulesConfigModels = loadVerifyRule.loadRule(route).get(route);
        if (rulesConfigModels.isEmpty()){
            return new ArrayList<>();
        }
        return rulesConfigModels;
    }

    public Cache<String, List<VerifyRulesConfigModel>> addCache(String route,List<VerifyRulesConfigModel> newCache){
        cache.put(route, newCache);
        return cache;
    }
}
