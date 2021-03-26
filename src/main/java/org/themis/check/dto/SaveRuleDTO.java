package org.themis.check.dto;

import lombok.Data;
import org.themis.check.utils.check.PatternEnum;

/**
 * @author YangZhou
 */
@Data
public class SaveRuleDTO {
    private Long ruleId;
    /**
     * 配置id
     */
    private Long routeConfigId;
    private String paramName;
    private String checkRule;
    private PatternEnum pattern;
    private String message;

}
