package com.taotao.order.service;

import com.taotao.order.pojo.OrderInfo;
import com.taotao.result.TaotaoResult;

public interface OrderService {
    /**
     * 生成订单，订单明细，地址
     * 订单id要用redis的incr方法生成
     * @param orderInfo 订单信息，订单明细信息
     * @return
     */
    TaotaoResult createOrder(OrderInfo orderInfo);
}
