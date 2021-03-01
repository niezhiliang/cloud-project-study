package com.niezhiliang.cloud.zuul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/1
 */
@RestController
public class MyController {

    /**
     * 测试路由到本身
     * @return
     */
    @GetMapping(value = "zuulIndex")
    public String index() {

        return "hello zuulIndex";
    }
}
