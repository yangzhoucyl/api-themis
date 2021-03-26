package org.themis.check.utils.check;

import lombok.Data;

import java.util.List;

/**
 * 校验规则配置
 * @author YangZhou
 */
@Data
public class CheckRulesConfigModel {
    private Long configId;

    private String domain;

    /**
     * 校验路径
     */
    private String route;

    /**
     * 配置名
     */
    private String name;

    /**
     * 启用状态
     */
    private Integer status;

    private String processType;

    private String typeVal;

    List<RuleConfigModel> rules;
}
