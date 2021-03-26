package org.themis.check.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>@Title 返回结果封装</p>
 * <p>@Description 返回结果封装</p>
 * <p>@Version 1.0.0 版本号</p>
 * <p>@author maoquanyi</p>
 * <p>@date 2021年01月04日</p>
 * <p>Copyright © dgg group.All Rights Reserved. 版权信息</p>
 */
@Data
public class ResultDTO implements Serializable {
    private static final long serialVersionUID = 2692883752512495999L;
    /**
     * 编码
     */
    private String code;
    private String data;
    private String msg;
    private String message;

}
