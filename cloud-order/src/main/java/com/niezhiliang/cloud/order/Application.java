package com.niezhiliang.cloud.order;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/28
 */
@SpringBootApplication
@EnableDistributedTransaction
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
