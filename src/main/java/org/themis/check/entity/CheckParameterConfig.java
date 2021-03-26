package org.themis.check.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YangZhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckParameterConfig extends BaseEntity {
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

    /**
     * 错误提示消息
     */
    private String message;

}