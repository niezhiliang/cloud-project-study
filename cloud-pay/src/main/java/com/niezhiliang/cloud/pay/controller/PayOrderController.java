package com.niezhiliang.cloud.pay.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.google.inject.internal.asm.$Handle;
import com.niezhiliang.cloud.pay.mapper.PayMapper;
import com.niezhiliang.cloud.pay.module.PayDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/28
 */
@RestController
@RequestMapping(value = "pay")
public class PayOrderController {
    @Autowired
    private PayMapper payMapper;
    @Autowired
    private RestTemplate restTemplate;

    public static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    /**
     * 模拟第三方回调
     * @return
     */
    @GetMapping(value = "callBack")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String callBack() {
        PayDO payDO = new PayDO();
        payDO.setId(1);
        //设置支付状态为成功
        payDO.setPayStatus(1);

        payMapper.updateById(payDO);

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", payDO.getId());
        //调用订单服务，修改订单状态
        restTemplate.postForEntity("http://service-order/order/change",paramMap,String.class);
        //模拟异常，让分布式事务回滚
        //int i = 1/0;

        return "succcess";
    }

    /**
     * TCC分布式事务
     * @return
     */
    @GetMapping(value = "callBack2")
    @Transactional(rollbackFor = Exception.class)
    @TccTransaction(cancelMethod = "cancelTcc",confirmMethod ="confirmTcc")
    public String callBack2() {
        PayDO payDO = new PayDO();
        payDO.setId(1);
        //设置支付状态为成功
        payDO.setPayStatus(1);

        payMapper.updateById(payDO);

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", payDO.getId());

        //通过threadLocal传递给confirm和cancel
        threadLocal.set(payDO.getId());
        //调用订单服务，修改订单状态
        restTemplate.postForEntity("http://service-order/order/change2",paramMap,String.class);
        //模拟异常，让分布式事务回滚
        int i = 1/0;

        return "succcess";
    }

    /**
     * 提交
     * @return
     */
    public String confirmTcc() {
        try {
            System.out.println("payId = 【"+ threadLocal.get() +"】tcc-confirm step.....");
        } finally {
            threadLocal.remove();
        }
        return "ok";
    }


    /**
     * 逆SQL，进行回滚
     * @return
     */
    public String cancelTcc() {
        try {
            System.out.println("payId = 【"+ threadLocal.get() +"】tcc-canel step.....");
            //逆操作
            Integer id = threadLocal.get();
            PayDO payDO = new PayDO();
            payDO.setId(id);
            payDO.setPayStatus(0);
            payMapper.updateById(payDO);
        } finally {
            threadLocal.remove();
        }
        return "ok";
    }
}