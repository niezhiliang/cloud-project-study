package com.niezhiliang.cloud.order.controller;

import com.niezhiliang.cloud.order.mapper.OrderMapper;
import com.niezhiliang.cloud.order.module.OrderDO;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author : niezhiliang
 * @Date : 2021/4/4
 */
@Component
public class SeataTccServiceImpl implements SeataTccService {
    @Autowired
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean tccTry(BusinessActionContext context,Integer orderId) {
        System.out.println("-----try------");
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        //设置为订单完成
        orderDO.setOrderStatus(1);
        orderMapper.updateById(orderDO);
        //int i = 1/0;
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean tccConfirm(BusinessActionContext context) {
        System.out.println("-----confirm--orderId: "+ context.getActionContext("orderId")+ " ----");
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean tccCancel(BusinessActionContext context) {
        System.out.println("-----cancel--orderId: "+ context.getActionContext("orderId")+ " ----");
        Integer orderId = (Integer) context.getActionContext("orderId");
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setOrderStatus(0);
        orderMapper.updateById(orderDO);
        return true;
    }
}
