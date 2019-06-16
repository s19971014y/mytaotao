package com.taotao.order.controller;

import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Value("${TT_CART}")
    private String TT_CART;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request){
        List<TbItem> cartList = getCartList(request);
        //传递给页面
        request.setAttribute("cartList",cartList);
        return "order-cart";
    }

    private List<TbItem> getCartList(HttpServletRequest request){
        //去购物车列表
        String json = CookieUtils.getCookieValue(request,TT_CART,true);
        //判断json是否为null
        if(StringUtils.isNotBlank(json)){
            //把json转换成商品列表返回
            List<TbItem> list = JsonUtils.jsonToList(json,TbItem.class);
            return list;
        }
        return new ArrayList<>();
    }

    @RequestMapping("/create")
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        TbUser user = (TbUser) request.getAttribute("user");
        //接收表单提交的orderInfo
        //补全数据
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUserName());

        //调用Service 创建订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        //发送一些数据给页面 让页面显示 添加成功的信息
        //取订单号
        String orderId = result.getData().toString();
        // a)需要Service返回订单号
        request.setAttribute("orderId", orderId);
        request.setAttribute("payment", orderInfo.getPayment());
        // b)当前日期加三天。
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        request.setAttribute("date", dateTime.toString("yyyy-MM-dd"));
        // 4、返回逻辑视图展示成功页面
        return "success";
    }
}
