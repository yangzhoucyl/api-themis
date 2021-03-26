package org.themis.check.service;


import org.themis.check.dto.ConfigRouteSearchDTO;
import org.themis.check.dto.SaveRouteDTO;
import org.themis.check.dto.SaveRuleDTO;
import org.themis.check.entity.CheckParameterConfig;
import org.themis.check.entity.CheckRouteConfig;
import org.themis.check.utils.check.CheckRulesConfigModel;

import java.util.List;

/**
 * 参数校验配置
 * @author YangZhou
 */
public interface RuleConfigService {

    /**
     * 保存拦截配置路径
     * @param saveRoute
     * @return
     */
    CheckRouteConfig saveRuleConfig(SaveRouteDTO saveRoute);

    /**
     * 保存拦截设置
     * @param saveRule 保存参数
     * @return
     */
    CheckParameterConfig saveCheckRule(SaveRuleDTO saveRule);

    /**
     * 查询参数列表
     * @param configRouteSearch
     * @return
     */
    List<CheckRulesConfigModel> listConfigRoute(ConfigRouteSearchDTO configRouteSearch);
}
