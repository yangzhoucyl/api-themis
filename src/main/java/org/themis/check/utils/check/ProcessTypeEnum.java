package org.themis.check.utils.check;

import java.util.Arrays;

/**
 * 调用处理枚举
 * @author YangZhou
 */

public enum ProcessTypeEnum {

    /**
     * 消息队列处理
     */
    PRODUCE_PROCESS,
    /**
     * feign调用
     */
    FEIGN_PROCESS,
    /**
     * 网关调用
     */
    GATEWAY_PROCESS,

    /**
     * 超链接调用
     */
    URL_PROCESS,
    /**
     * 老系统保存等待回调处理
     */
    OLD_SAVE_PROCESS,
    /**
     * 新系统保存等待回调处理
     */
    SAVE_PROCESS,

    /**
     * 默认处理方式
     */
    DEFAULT;

    /**
     * 类型是否包含枚举类对象
     * @param processType
     * @return
     */
    public static boolean isContainerType(String processType){
       return Arrays.stream(ProcessTypeEnum.values()).anyMatch(type -> type.name().equals(processType));
    }
}
