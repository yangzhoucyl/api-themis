package org.themis.check.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.themis.check.utils.HttpRequestUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 白名单控制拦截器
 * @author YangZhou
 */
@Slf4j
public class WhiteListInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> params = HttpRequestUtils.getRequestBodyMap(request);
        log.info("外部请求进入系统,请求参数: [{}]", JSONObject.toJSONString(params));
//        if ()
        return true;
    }

}
