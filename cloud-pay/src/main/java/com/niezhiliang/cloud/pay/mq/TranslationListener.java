package com.niezhiliang.cloud.pay.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Author niezhiliang
 * @Date 2021/4/13 15:34
 */
@RocketMQTransactionListener
@Slf4j
@Component
public class TranslationListener implements RocketMQLocalTransactionListener {

    /**
     * 发送半消息成功后，会执行该方法
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("执行本地事务：{}",JSON.toJSONString(msg));
        System.out.println(new String((byte[]) msg.getPayload()));
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * mq长时间未收到事务确认消息，会主动进行回查
     * 默认60s
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("mq回查：{}",JSON.toJSONString(msg));
        return RocketMQLocalTransactionState.UNKNOWN;
    }
}
