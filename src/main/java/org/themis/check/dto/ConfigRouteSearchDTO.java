package org.themis.check.dto;

import lombok.Data;

/**
 * @author YangZhou
 */
@Data
public class ConfigRouteSearchDTO {
    private String route;
    private Integer start = 1;
    private Integer size = 10;
}
