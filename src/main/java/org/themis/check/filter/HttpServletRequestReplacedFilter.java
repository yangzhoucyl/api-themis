package org.themis.check.filter;

import org.themis.check.utils.BodyReaderHttpServletRequestWrapper;
import org.springframework.stereotype.Component;
import org.themis.check.constant.CallbackConsts;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 重写过滤器 解决request.reader()流只能读取一次的问题
 * @author YangZhou
 */
@Component
@WebFilter("/*")
public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (CallbackConsts.HTTP_METHOD_POST.equals(httpServletRequest.getMethod().toUpperCase())) {
                requestWrapper = new BodyReaderHttpServletRequestWrapper(
                        (HttpServletRequest) request);
            }
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {


    }


    @Override
    public void destroy() {


    }
}
