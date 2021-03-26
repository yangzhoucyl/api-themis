package org.themis.check.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.themis.check.vo.ThemisResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常拦截
 * @author YangZhou
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionInterceptor {

    @Value("${paramCheck.verify.paramErrorCode:500}")
    private int paramErrorCode;

    @ExceptionHandler
    @ResponseBody
    public ThemisResponse illegalArgumentExceptionResponse(IllegalArgumentException e){
        log.error("参数校验异常",e);
        return new ThemisResponse(paramErrorCode,e.getMessage(), null);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ThemisResponse unprocessedExceptionResponse(Exception e){
        log.error("未捕获处理异常", e);
        return new ThemisResponse(500, "服务异常请联系管理员");
    }
}
