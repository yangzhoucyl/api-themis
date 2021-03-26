package org.themis.check.utils.check;

import lombok.Data;

/**
 * 校验规则
 * @author YangZhou
 */
@Data
public class RuleConfigModel {

    private Long configId;

    private Long routeConfigId;

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 校验规则
     */
    private String checkRule;

    /**
     * 验证模式
     */
    private String pattern;

    private String message;
}
