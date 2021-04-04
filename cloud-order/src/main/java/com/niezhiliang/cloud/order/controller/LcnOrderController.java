package com.niezhiliang.cloud.order.controller;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.niezhiliang.cloud.order.mapper.OrderMapper;
import com.niezhiliang.cloud.order.module.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/28
 */
@RestController
@RequestMapping(value = "lcn")
public class LcnOrderController {
    @Autowired
    private OrderMapper orderMapper;
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    /**
     * 改变订单状态
     * 分布式事务：LCN方式
     * @return
     */
    @RequestMapping(value = "lcn")
    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    public String changeStatus(Integer id) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(id);
        //设置为订单完成
        orderDO.setOrderStatus(1);
        orderMapper.updateById(orderDO);
        return "success";
    }

    /**
     * 改变订单状态
     * 分布式事务：TCC方式
     * @return
     */
    @RequestMapping(value = "tcc")
    @Transactional(rollbackFor = Exception.class)
    @TccTransaction(cancelMethod = "cancelTcc",confirmMethod = "confirmTcc")
    public String changeStatus2(Integer id) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(id);
        //设置为订单完成
        orderDO.setOrderStatus(1);
        orderMapper.updateById(orderDO);
        //操作数据传到confirm和cancel方法
        threadLocal.set(id);
        return "success";
    }

    /**
     * TCC提交，可以不用写
     * @return
     */
    public String confirmTcc(Integer id) {
        try {
            System.out.println("orderId = 【"+ threadLocal.get() +"】tcc-confirm step.....");
        } finally {
            threadLocal.remove();
        }
        return "ok";
    }

    /**
     * TCC回滚，参数需要跟执行的参数一致
     * 不然不会执行
     * SQL逆操作
     * @return
     */
    public String cancelTcc(Integer id) {
        try {
            System.out.println("orderId = 【"+ threadLocal.get() +"】tcc-confirm step.....");
            Integer orderId = threadLocal.get();
            OrderDO orderDO = new OrderDO();
            orderDO.setId(orderId);
            orderDO.setOrderStatus(0);
            orderMapper.updateById(orderDO);
        } finally {
            threadLocal.remove();
        }
        return "ok";
    }
}
