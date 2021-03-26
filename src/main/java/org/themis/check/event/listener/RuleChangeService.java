package org.themis.check.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.themis.check.event.RuleChangeEvent;
import org.themis.check.utils.check.InitializeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 规则变更事件监听
 * @author yangzhou
 */
@Service
@Slf4j
public class RuleChangeService implements ApplicationListener<RuleChangeEvent> {

    private final InitializeRule initializeRule;


    @Autowired
    public RuleChangeService(InitializeRule initializeRule) {
        this.initializeRule = initializeRule;
    }

    @Override
    @Async
    public void onApplicationEvent(RuleChangeEvent ruleChangeEvent) {
        log.info("监听到参数校验规则更改事件,执行规则缓存更新: [{}]", ruleChangeEvent.getEventName());
        initializeRule.initializationRule();
    }

}
