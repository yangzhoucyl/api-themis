package org.themis.check.constant.enums;


import org.themis.check.constant.CallbackConsts;

/**
 * @Description: 返回状态
 * @Author Created by yan.x on 2020-01-08 .
 **/
enum ResponseMessage {
    /**
     *
     */
    SUCCESS(CallbackConsts.REQUEST_SUCCESS_CODE_200, "操作成功"),
    UNKNOW_ERROR(CallbackConsts.UN_KNOW_ERROR_CODE, "服务异常,请稍后再试!"),
    VAILD_ERROR(400, "参数验证异常"),
    NOT_FOUND(404, "资源不存在"),
    INVOKE_FAILURE(500, "访问失败"),
    ACCESS_DENIED_ERROR(405, "访问受限"),
    PARAMETER_ERROR(400, "请求参数有误");

    private final int code;
    private final String message;

    ResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
