package org.themis.check.utils.check;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化规则方式
 * @author YangZhou
 */
public interface InitializeRule {

    /**
     * 初始化校验规则
     * @return ConcurrentHashMap
     */
    ConcurrentHashMap<String, List<CheckRulesConfigModel>> initializationRule();
}
