package com.niezhiliang.cloud.service.sms.limit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author : niezhiliang
 * @Date : 2021/7/24
 */
@Component
public class LimitFilter implements Filter {

    /**
     * 限流QPS:2
     */
    private final RateLimiter limiter = RateLimiter.create(2);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 限流的业务逻辑
        if (limiter.tryAcquire()){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            servletResponse.setCharacterEncoding("utf-8");
            servletResponse.setContentType("text/html; charset=utf-8");
            PrintWriter pw = null;
            pw = servletResponse.getWriter();
            pw.write("哥被限流了");
            pw.close();
        }
    }
}
