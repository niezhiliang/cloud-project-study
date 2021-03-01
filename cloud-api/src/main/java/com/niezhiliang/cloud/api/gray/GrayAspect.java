package com.niezhiliang.cloud.api.gray;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname niezhiliang
 * @Date 2021/2/23 19:31
 */
@Aspect
@Component
public class GrayAspect {

    /**
     * 拦截所有controller的切面
     * 第一个* 表示任意返回值
     * com.niezhiliang.cloud.api.controller..*Controller*.* 拦截的类任意方法
     * (..) 任意参数
     */
    @Pointcut("execution(* com.niezhiliang.cloud.api.controller..*Controller*.*(..))")
    public void requestPoint() {

    }

    /**
     * 拦截请求
     * @param point
     */
    @Before("requestPoint()")
    public void before(JoinPoint point) {
        //获取HttpServletRequest对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //从请求头拿出用户tag
        String version = request.getHeader("version");
        Map<String,String> map = new HashMap<>();
        map.put("version",version);
        //放入threadlocal中，方便ribbon选择灰度对应的服务
        ThreadLocalUtils.set(map);
    }
}
