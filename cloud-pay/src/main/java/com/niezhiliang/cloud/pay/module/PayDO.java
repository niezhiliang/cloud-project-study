package com.niezhiliang.cloud.pay.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_pay")
public class PayDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer payStatus;
}
