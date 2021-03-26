package org.themis.check.entity;

import lombok.Data;

/**
 * 白名单配置
 * @author YangZhou
 */
@Data
public class WhitelistConfig {
    private Long id;
    private String systemCode;
    private String status;
    private String systemName;
}
