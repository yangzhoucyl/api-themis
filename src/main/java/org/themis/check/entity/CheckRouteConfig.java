package org.themis.check.entity;

import lombok.Data;

/**
 * @author YangZhou
 */
@Data
public class CheckRouteConfig extends BaseEntity {
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
     * 处理类型
     */
    private String processType;

    /**
     * 处理类型参数
     */
    private String typeVal;

    /**
     * 启用状态
     */
    private Integer status;


}