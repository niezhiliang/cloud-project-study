package com.niezhiliang.cloud.pay.controller;

import com.niezhiliang.cloud.pay.mapper.PayMapper;
import com.niezhiliang.cloud.pay.module.PayDO;
import com.niezhiliang.cloud.pay.mq.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author niezhiliang
 * @Date 2021/4/13 15:21
 */
@RestController
public class MQController {

    @Autowired
    private PayMapper payMapper;
    @Autowired
    private MQService mqService;


    /**
     * 模拟第三方回调
     * AT模式
     * @return
     */
    @GetMapping(value = "mq")
    public String AT() {
        PayDO payDO = new PayDO();
        payDO.setId(1);
        //设置支付状态为成功
        payDO.setPayStatus(1);
        payMapper.updateById(payDO);

        //模拟异常，让分布式事务回滚
        //int i = 1/0;
        //发送事务消息
        mqService.sendTranslationMsg(payDO.getId());

        return "succcess";
    }
}
