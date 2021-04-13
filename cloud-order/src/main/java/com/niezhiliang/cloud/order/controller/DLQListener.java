package com.niezhiliang.cloud.order.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author niezhiliang
 * @Date 2021/4/13 16:22
 */
@RocketMQMessageListener(topic = "%DLQ%pay-translation-topic",consumerGroup = "distributed-translation-order")
@Component
@Slf4j
public class DLQListener implements RocketMQListener<MessageExt> {

    /**
     * 监听死信队列，消费补偿
     * 发送钉钉/邮件
     * @param message
     */
    @Override
    public void onMessage(MessageExt message) {
        log.info("死信队列：{}",JSON.toJSONString(message));
        //TODO  发送钉钉或者邮箱
    }
}
