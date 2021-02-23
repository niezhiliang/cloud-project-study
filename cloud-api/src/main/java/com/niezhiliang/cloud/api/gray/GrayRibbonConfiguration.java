package com.niezhiliang.cloud.api.gray;

import org.springframework.context.annotation.Bean;

/**
 * @Classname niezhiliang
 * @Date 2021/2/23 19:54
 * 不用配置注解修饰，启动类上@RibbonClient
 * 会把该类放到容器中
 */
public class GrayRibbonConfiguration {

    @Bean
    public GrayRule grayRule () {
        return new GrayRule();
    }
}
