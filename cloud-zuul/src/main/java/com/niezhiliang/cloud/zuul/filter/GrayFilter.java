//package com.niezhiliang.cloud.zuul.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author: niezhiliang
// * @date : 2021/2/21
// * 灰度发布拦截器
// */
//@Component
//public class GrayFilter extends ZuulFilter {
//
//
//    /**
//     * 路由转发时拦截
//     * @return
//     */
//    @Override
//    public String filterType() {
//        return FilterConstants.ROUTE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return false;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//
//
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//
//        String headerUserId = request.getHeader("userId");
//        if (null == headerUserId) {
//            return null;
//        }
//        int userId = Integer.parseInt(headerUserId);
//        // 根据用户id 查 规则  查库 v1,meata
//
//        //模拟数据库灰度规则
//        // 金丝雀
//        if (userId == 1){
//            RibbonFilterContextHolder.getCurrentContext().add("version","v1");
//        // 普通用户
//        } else if (userId == 2){
//            RibbonFilterContextHolder.getCurrentContext().add("version","v2");
//        }
//        return null;
//    }
//}
