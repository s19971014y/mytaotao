package com.taotao.cart.controller;

import com.taotao.pojo.TbItem;
import com.taotao.result.TaotaoResult;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Value("${TT_CART}")
    private String TT_CART;

    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;

    @Autowired
    private ItemService itemService;
    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){

        //cookie的key定义为：TT_CART value定义为json格式的商品信息
        List<TbItem> cartList = getCartList(request);
        //判断集合对象是否为null
        boolean flag = false;
        for (TbItem item:cartList){
           //判断页面传递过来的itemId是否有包含在这个集合里面，如果有则原来的基础之上加1，没有则直接加1
            if(item.getId() == itemId){
                item.setNum(item.getNum()+num);
                flag = true;
            }
        }

        //如果flag为false则表示  新添加到购物车商品  在cookie的购物车里面找不到
        if(!flag){
            //使用商品id查询商品信息把商品信息变成json格式，存入cookie中
            TbItem item = itemService.findItemById(itemId);
            //设置商品数量
            item.setNum(1);
            //设置图片
            String image = item.getImage();
            if(StringUtils.isNotBlank(image)){
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            //将商品加入到集合中
            cartList.add(item);
        }

        //走到这里  cookie一定有商品
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);

        return "cartSuccess";
    }
    //根据cookie的key ，取cookie的value 也就是json格式的商品信息
    private List<TbItem> getCartList(HttpServletRequest request){

        //调用cookie的工具类，根据key取value 默认编码为utf-8
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        if(StringUtils.isNotBlank(json)){
            List<TbItem> result = JsonUtils.jsonToList(json, TbItem.class);
            return result;
        }
        return new ArrayList<>();
    }

    @RequestMapping("/cart")
    public String showCartList(HttpServletRequest request, Model model){
        //能够到这里  表示一定有商品信息
        List<TbItem> cartList = getCartList(request);
        model.addAttribute("cartList",cartList);
        return "cart";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateNum(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        //能够点击+ 或者 - 表示一定有商品在cookie中
        List<TbItem> cartList = getCartList(request);
        for(TbItem item:cartList){
            if(item.getId() == itemId.longValue()){
                item.setNum(num);
                break;
            }
        }
        //再次把商品改变的数据加入到cookie里面去
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
        return  TaotaoResult.ok();
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        List<TbItem> cartList = getCartList(request);
        System.out.println(JsonUtils.objectToJson(cartList));
        for(int i=0;i < cartList.size();i++){
            if(cartList.get(i).getId() == itemId.longValue()){
                //增强for循环底层是迭代器，不能进行增删操作，否则报错
                cartList.remove(i);
            }
        }
        System.out.println(JsonUtils.objectToJson(cartList));
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
        return "redirect:/cart/cart.html";
    }
}
