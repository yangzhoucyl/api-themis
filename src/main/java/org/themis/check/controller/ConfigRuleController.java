package org.themis.check.controller;

import com.github.pagehelper.PageInfo;
import org.themis.check.dto.ConfigRouteSearchDTO;
import org.themis.check.dto.SaveRouteDTO;
import org.themis.check.dto.SaveRuleDTO;
import org.themis.check.entity.CheckParameterConfig;
import org.themis.check.entity.CheckRouteConfig;
import org.themis.check.service.RuleConfigService;
import org.themis.check.utils.check.CheckRulesConfigModel;
import org.themis.check.utils.check.InitializeRule;
import org.themis.check.vo.ThemisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YangZhou
 */
@RestController
@RequestMapping(value = "/nk/config/")
public class ConfigRuleController extends ThemisResponse {

    @Autowired
    private RuleConfigService ruleConfigService;

    @Autowired
    private InitializeRule initializeRule;

    @PostMapping(value = "/save_route.do")
    public ThemisResponse configCheckRoute(@RequestBody SaveRouteDTO saveRoute){
        CheckRouteConfig routeConfig = ruleConfigService.saveRuleConfig(saveRoute);
        return success(routeConfig);
    }

    @PostMapping(value = "/save_check_rule.do")
    public ThemisResponse configCheckRule(@RequestBody SaveRuleDTO saveRule){
        CheckParameterConfig parameterConfig = ruleConfigService.saveCheckRule(saveRule);
        return success(parameterConfig);
    }

    @PostMapping(value = "/list_config_route.do")
    public ThemisResponse listConfigRoute(@RequestBody ConfigRouteSearchDTO configRouteSearch){
        List<CheckRulesConfigModel> list = ruleConfigService.listConfigRoute(configRouteSearch);
        PageInfo<CheckRulesConfigModel> result = new PageInfo<>(list);
        return success(result);
    }

    @PostMapping(value = "/update_rule_cache.do")
    public ThemisResponse updateRuleCache(){
        return success(initializeRule.initializationRule());
    }
}
