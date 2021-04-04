package com.niezhiliang.cloud.order.controller;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @Author : niezhiliang
 * @Date : 2021/4/4
 */
@LocalTCC
public interface SeataTccService {

    @TwoPhaseBusinessAction(name = "tcc", commitMethod = "tccConfirm", rollbackMethod = "tccCancel")
    boolean tccTry(BusinessActionContext context,@BusinessActionContextParameter(paramName = "orderId") Integer orderId);

    /**
     * TCC confirm方法
     * @return
     */
    boolean tccConfirm(BusinessActionContext context);


    /**
     * Tcc cancel方法
     * @return
     */
    boolean tccCancel(BusinessActionContext context);
}
