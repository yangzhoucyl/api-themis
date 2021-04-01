package cn.myiml.theims.core.rule.load;

import cn.myiml.theims.core.model.CheckRulesConfigModel;
import com.google.common.cache.Cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 加载验证规则
 * @author yangzhou
 */
public interface LoadVerifyRule<E> {

    /**
     * 根据路径名加载校验路径
     * @param routeName routeName
     * @return
     */
    List<E> loadRule(String routeName);


    /**
     * 加载所有规则
     * @return E
     */
    ConcurrentHashMap<String,List<E>> loadAllRules();


    /**
     * 加载所有验证规则集合
     * @return  E
     */
    List<E> loadAllRuleList();

}
