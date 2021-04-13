package com.niezhiliang.cloud.pay.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Author niezhiliang
 * @Date 2021/4/13 15:24
 */
@Component
@Slf4j
public class MQService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送事务半消息
     * @param orderId
     */
    public void sendTranslationMsg(Integer orderId) {
        Message<Integer> message = MessageBuilder.withPayload(orderId).build();
        rocketMQTemplate.sendMessageInTransaction("pay-translation-topic", message, null);
    }
}
