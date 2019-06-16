package com.taotao.order.service.impl;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.result.TaotaoResult;
import com.taotao.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;

    @Value("${ORDER_ID_BEGIN}")
    private String ORDER_ID_BEGIN;

    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;


    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {

        if(!JedisUtils.exists(ORDER_GEN_KEY)){
            JedisUtils.set(ORDER_GEN_KEY,ORDER_ID_BEGIN);
        }
        //通过redis生成订单id
        String orderId = JedisUtils.incr(ORDER_GEN_KEY).toString();
        //设置订单号
        orderInfo.setOrderId(orderId);
        //订单状态未支付
        orderInfo.setStatus(1);
        Date date = new Date();
        orderInfo.setCreateTime(date);
        orderInfo.setUpdateTime(date);

        //插入订单信息到订单表中去
        orderMapper.insertOrder(orderInfo);

        //从OrderInfo对象里面得到订单明细集合对象
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for(TbOrderItem orderItem: orderItems){
            //通过redis生成订单id
            String orderItemId = JedisUtils.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            //设置订单号
            orderItem.setId(orderItemId);
            //设置订单详情与订单表的关系
            orderItem.setOrderId(orderId);
            //插入订单详情信息
            orderItemMapper.insertOrderItem(orderItem);
        }

        //插入地址信息
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        //建立订单与地址的关系
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);

        //插入地址
        orderShippingMapper.insertOrderShipping(orderShipping);
        //页面需要展示订单号
        return TaotaoResult.ok(orderId);
    }
}
