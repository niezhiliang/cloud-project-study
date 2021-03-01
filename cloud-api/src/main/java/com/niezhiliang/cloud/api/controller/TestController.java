package com.niezhiliang.cloud.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname niezhiliang
 * @Date 2021/2/23 19:29
 */
@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过ribbon进行服务间调用
     * 用于测试灰度是否成功，
     * 请求头中需要带上version = v1/v2
     * 别的暂时没处理，会一直取不到服务导致死循环
     * @return
     */
    @GetMapping(value = "test")
    public String test() {

        return restTemplate.getForObject("http://SMS/test/sms-test",String.class);
    }
}
