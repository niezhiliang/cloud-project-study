package com.niezhiliang.cloud.order.controller;

import com.niezhiliang.cloud.order.mapper.OrderMapper;
import com.niezhiliang.cloud.order.module.OrderDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author niezhiliang
 * @Date 2021/4/13 16:10
 */
@RocketMQMessageListener(consumerGroup = "distributed-translation-order",topic = "pay-translation-topic")
@Component
@Slf4j
public class MQService implements RocketMQListener<Integer> {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 监听支付服务的消息
     * ，如果一直消费失败，消息会
     * 被丢到死信队列，我们监听死信队列
     * 将消息发送给运维的钉钉或邮箱
     * 进行人工补偿
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(Integer id) {
        log.info("id={}",id);
        OrderDO orderDO = new OrderDO();
        orderDO.setId(id);
        //设置为订单完成
        orderDO.setOrderStatus(1);
        orderMapper.updateById(orderDO);
    }
}
