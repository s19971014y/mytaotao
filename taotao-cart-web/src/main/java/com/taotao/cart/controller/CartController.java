package com.taotao.cart.controller;

import com.taotao.pojo.TbItem;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpCookie;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Value("${TT_CART}")
    private String TT_CART;

    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Integer itemId, Integer num, HttpCookie cookie,HttpServletRequest request){

        //cookie的key定义为：TT_CART value定义为json格式的商品信息
        List<TbItem> cartList = getCartList(request);
        //判断集合对象是否为null


        return null;
    }
    //根据cookie的key ，取cookie的value 也就是json格式的商品信息
    private List<TbItem> getCartList(HttpServletRequest request){

        //调用cookie的工具类，根据key取value 默认编码为utf-8
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        return JsonUtils.jsonToList(json,TbItem.class);
    }
}
