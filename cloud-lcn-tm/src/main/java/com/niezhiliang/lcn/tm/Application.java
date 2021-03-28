package com.niezhiliang.lcn.tm;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/28
 */
@SpringBootApplication
@EnableTransactionManagerServer
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
