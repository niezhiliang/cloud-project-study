package com.niezhiliang.cloud.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Author : niezhiliang
 * @Date : 2021/7/24
 * RateLimit限流
 */
@Component
@Slf4j
public class LimitFilter extends ZuulFilter {

    /**
     * RateLimiter限制QPS为2
     */
    private final RateLimiter limiter = RateLimiter.create(2);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if (!limiter.tryAcquire()) {
            log.warn("被限流了");
            RequestContext currentContext = RequestContext.getCurrentContext();
            //下面的拦截器不再执行
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        return null;
    }
}
