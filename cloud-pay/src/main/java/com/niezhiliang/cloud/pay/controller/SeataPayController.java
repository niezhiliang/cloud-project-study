package com.niezhiliang.cloud.pay.controller;

import com.niezhiliang.cloud.pay.mapper.PayMapper;
import com.niezhiliang.cloud.pay.module.PayDO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author : niezhiliang
 * @Date : 2021/4/4
 * Seata AT和TCC测试
 */
@RequestMapping(value = "seata")
@RestController
public class SeataPayController {
    @Autowired
    private PayMapper payMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SeataTccService seataTccService;

    /**
     * 模拟第三方回调
     * AT模式
     * @return
     */
    @GetMapping(value = "AT")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String AT() {
        PayDO payDO = new PayDO();
        payDO.setId(1);
        //设置支付状态为成功
        payDO.setPayStatus(1);
        payMapper.updateById(payDO);

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", payDO.getId());
        //调用订单服务，修改订单状态
        restTemplate.postForEntity("http://service-order/seata/AT",paramMap,String.class);
        //模拟异常，让分布式事务回滚
        //int i = 1/0;

        return "succcess";
    }

    /**
     * TCC模式
     * try方法尽量不要跟调用者在一个类里面
     * 容易导致aop失效，导致不执行confirm或者cancel
     * 方法
     * @return
     */
    @GetMapping(value = "TCC")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String TCC() {
        seataTccService.tccTry(null,1);
        return "succcess";
    }


}
