package com.niezhiliang.cloud.service.sms.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : niezl
 * @date : 2021/2/21
 */
@RestController
@RequestMapping(value = "test")
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/sms-test")
    public String test(){

        return "sms-test:"+port;
    }
}
