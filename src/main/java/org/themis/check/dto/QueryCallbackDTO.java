package org.themis.check.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>@Title 查询callBack参数</p>
 * <p>@Description 查询callBack参数</p>
 * <p>@Version 1.0.0 版本号</p>
 * <p>@author maoquanyi</p>
 * <p>@date 2021年01月04日</p>
 * <p>Copyright © dgg group.All Rights Reserved. 版权信息</p>
 */
@Data
public class QueryCallbackDTO implements Serializable {
    private static final long serialVersionUID = 2692883752512493211L;
    /**
     * 回调状态
     */
    private String callbackStatus;
    private List<String> callbackStatusList;


    private String startCallbackTime;
    private String endCallbackTime;

    private String callbackCount;

    private String startRequestTime;
    private String endRequestTime;

    private String callbackId;

}
