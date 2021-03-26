package org.themis.check.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.util.ObjectUtils;
import org.themis.check.dao.CheckParameterConfigMapper;
import org.themis.check.dao.CheckRouteConfigMapper;
import org.themis.check.dto.ConfigRouteSearchDTO;
import org.themis.check.dto.SaveRouteDTO;
import org.themis.check.dto.SaveRuleDTO;
import org.themis.check.entity.CheckParameterConfig;
import org.themis.check.entity.CheckRouteConfig;
import org.themis.check.event.RuleChangeEvent;
import org.themis.check.service.RuleConfigService;
import org.themis.check.utils.Builder;
import org.themis.check.utils.check.CheckRulesConfigModel;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author YangZhou
 */
@Service
public class RuleConfigServiceImpl implements RuleConfigService, ApplicationEventPublisherAware {

    @Resource
    private CheckRouteConfigMapper routeConfigMapper;

    @Resource
    private CheckParameterConfigMapper parameterConfigMapper;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public CheckRouteConfig saveRuleConfig(SaveRouteDTO saveRoute) {
        CheckRouteConfig routeConfig;
        if (ObjectUtils.isEmpty(saveRoute.getConfigId())){
            routeConfig = Builder.of(CheckRouteConfig:: new)
                    .with(CheckRouteConfig:: setConfigId, saveRoute.getConfigId())
                    .with(CheckRouteConfig:: setRoute, saveRoute.getRoute())
                    .with(CheckRouteConfig:: setDomain, saveRoute.getDomain())
                    .with(CheckRouteConfig:: setName, saveRoute.getName())
                    .with(CheckRouteConfig:: setStatus, saveRoute.getStatus())
                    .with(CheckRouteConfig:: setProcessType, saveRoute.getTypeEnum().name())
                    .with(CheckRouteConfig:: setTypeVal, saveRoute.getTypeVal())
                    .with(CheckRouteConfig:: setCreateTime, new Date())
                    .with(CheckRouteConfig:: setCreateUser, "超级管理员")
                    .build();
            routeConfigMapper.insert(routeConfig);
        }else {
            routeConfig = routeConfigMapper.selectByPrimaryKey(saveRoute.getConfigId());
            if (routeConfig != null){
                routeConfig.setRoute(saveRoute.getRoute());
                routeConfig.setDomain(saveRoute.getDomain());
                routeConfig.setName(saveRoute.getName());
                routeConfig.setStatus(saveRoute.getStatus());
                routeConfig.setProcessType(saveRoute.getTypeEnum().name());
                routeConfig.setTypeVal(saveRoute.getTypeVal());
                routeConfig.setUpdateTime(new Date());
                routeConfigMapper.updateByPrimaryKey(routeConfig);
            }
        }
        applicationEventPublisher.publishEvent(new RuleChangeEvent("配置路径变更",this));
        return routeConfig;
    }

    @Override
    public CheckParameterConfig saveCheckRule(SaveRuleDTO saveRule) {
        CheckParameterConfig parameterConfig;
        if (ObjectUtils.isEmpty(saveRule.getRuleId())){
            parameterConfig = Builder.of(CheckParameterConfig:: new)
                    .with(CheckParameterConfig:: setConfigId, saveRule.getRuleId())
                    .with(CheckParameterConfig:: setRouteConfigId, saveRule.getRouteConfigId())
                    .with(CheckParameterConfig:: setParamName, saveRule.getParamName())
                    .with(CheckParameterConfig:: setPattern, saveRule.getPattern().name())
                    .with(CheckParameterConfig:: setCreateTime, new Date())
                    .with(CheckParameterConfig:: setCheckRule, saveRule.getCheckRule())
                    .with(CheckParameterConfig:: setMessage, saveRule.getMessage())
                    .build();
            parameterConfigMapper.insert(parameterConfig);
        }else {
            parameterConfig = parameterConfigMapper.selectByPrimaryKey(saveRule.getRuleId());
            parameterConfig.setParamName(saveRule.getParamName());
            parameterConfig.setCheckRule(saveRule.getCheckRule());
            parameterConfig.setPattern(saveRule.getPattern().name());
            parameterConfig.setUpdateTime(new Date());
            parameterConfig.setMessage(saveRule.getMessage());
            parameterConfigMapper.updateByPrimaryKey(parameterConfig);
        }
        applicationEventPublisher.publishEvent(new RuleChangeEvent("配置规则变更",this));
        return parameterConfig;
    }

    @Override
    public List<CheckRulesConfigModel> listConfigRoute(ConfigRouteSearchDTO configRouteSearch) {
        PageHelper.startPage(configRouteSearch.getStart(), configRouteSearch.getSize());
        return routeConfigMapper.findAllByRoute(configRouteSearch.getRoute());
    }

}
