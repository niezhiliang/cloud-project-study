package com.niezhiliang.cloud.pay.controller;

import com.niezhiliang.cloud.pay.mapper.PayMapper;
import com.niezhiliang.cloud.pay.module.PayDO;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Author : niezhiliang
 * @Date : 2021/4/4
 */
@Component
public class SeataTccServiceImpl implements SeataTccService {
    @Autowired
    private PayMapper payMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean tccTry(BusinessActionContext context,Integer payId) {
        System.out.println("------try------");
        PayDO payDO = new PayDO();
        payDO.setId(payId);
        //设置支付状态为成功
        payDO.setPayStatus(1);
        payMapper.updateById(payDO);

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", payDO.getId());
        //调用订单服务，修改订单状态
        restTemplate.postForEntity("http://service-order/seata/TCC",paramMap,String.class);
        return true;
    }


    /**
     * TCC confirm方法
     * 真实业务场景需要考虑TCC的空回滚、幂等、悬挂等情况
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean tccConfirm(BusinessActionContext context) {
        System.out.println("-----payId:"+ context.getActionContext("payId")+" confirm------");
        return true;
    }

    /**
     * TCC cancel方法
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean tccCancel(BusinessActionContext context) {
        System.out.println("-----payId:"+ context.getActionContext("payId")+" cancel------");
        //逆操作
        Integer id = (Integer) context.getActionContext("payId");
        PayDO payDO = new PayDO();
        payDO.setId(id);
        payDO.setPayStatus(0);
        payMapper.updateById(payDO);
        return true;
    }
}
