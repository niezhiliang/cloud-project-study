package com.niezhiliang.cloud.service.sms.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : niezl
 * @date : 2021/2/21
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Value("${server.port}")
    private String port;

    /**
     * 测试灰度发布
     * @return
     */
    @GetMapping("/sms-test")
    public String test(){

        return "sms-test:"+port;
    }

    /**
     * 测试网关敏感信息透传
     * @param request
     * @return
     */
    @GetMapping("/sms-test2")
    public String test2(HttpServletRequest request) {
        String cookie = request.getHeader("Cookie");
        System.out.println("Cookie = " + cookie);
        return "sms-test:"+port + "Cookie=" + cookie;
    }
}
