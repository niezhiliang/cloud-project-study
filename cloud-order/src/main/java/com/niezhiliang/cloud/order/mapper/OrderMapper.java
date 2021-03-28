package com.niezhiliang.cloud.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niezhiliang.cloud.order.module.OrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/28
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
