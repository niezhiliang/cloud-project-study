package com.niezhiliang.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/7
 * 老项目改造uri映射问题
 */
@Component
public class MappingFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //得到完整的请求地址
        String remoteAddr = request.getRequestURI();
        if (remoteAddr.contains("sms-test3")) {
            //设置两个属性，执行RibbonRoutingFilter的时候会通过该属性去拼接完整的url地址
            requestContext.set(FilterConstants.REQUEST_URI_KEY,"/test/sms-test");
            //requestContext.set(FilterConstants.SERVICE_ID_KEY,"sms");
        }
        return null;
    }
}
