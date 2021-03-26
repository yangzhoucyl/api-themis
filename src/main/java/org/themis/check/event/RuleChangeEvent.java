package org.themis.check.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 规则变更事件
 * @author yangzhou
 */
public class RuleChangeEvent extends ApplicationEvent {

    private String eventName;

    public RuleChangeEvent(Object source) {
        super(source);
    }

    public RuleChangeEvent(String eventName, Object source){
        super(source);
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
