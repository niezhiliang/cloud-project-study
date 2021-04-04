package com.niezhiliang.cloud.order.controller;

import com.niezhiliang.cloud.order.mapper.OrderMapper;
import com.niezhiliang.cloud.order.module.OrderDO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : niezhiliang
 * @Date : 2021/4/4
 * Seata分布式测试
 */
@RestController
@RequestMapping(value = "seata")
public class SeataOrderController {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SeataTccService seataTccService;

    /**
     * 改变订单状态
     * 分布式事务：LCN方式
     * @return
     */
    @RequestMapping(value = "AT")
    @Transactional(rollbackFor = Exception.class)
    public String changeStatus(Integer id) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(id);
        //设置为订单完成
        orderDO.setOrderStatus(1);
        orderMapper.updateById(orderDO);
        //异常回滚
        int i = 1/0;
        return "success";
    }

    /**
     * TCC模式
     * @param id
     * @return
     */
    @RequestMapping(value = "TCC")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String tcc(Integer id) {
        seataTccService.tccTry(null,id);
        return "success";
    }
}
