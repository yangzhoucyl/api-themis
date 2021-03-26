package org.themis.check.dto;

import lombok.Data;
import org.themis.check.utils.check.ProcessTypeEnum;

/**
 * @author YangZhou
 */
@Data
public class SaveRouteDTO {
    private Long configId;
    private String domain;
    private String route;
    private String name;
    private Integer status;
    private ProcessTypeEnum typeEnum;
    private String typeVal;
}
