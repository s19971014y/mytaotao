package com.taotao.sso.controller;

import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Value("${TT_TOKEN}")
    private String TT_TOKEN;



    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public String checkData(@PathVariable String param,@PathVariable Integer type,String callback){
        TaotaoResult result = userService.checkData(param,type);
        if(StringUtils.isNotBlank(callback)){
            //如果有这个参数存在，我们就要把json改为jsonp相应的参数
            String jsonp = "callback("+ JsonUtils.objectToJson(result) +")";
            return jsonp;
        }
        return JsonUtils.objectToJson(result);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser tbUser){
        TaotaoResult result = userService.createUser(tbUser);
        return result;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response){
        TaotaoResult result = userService.loginUser(userName, passWord);
        //要把用户信息存入到cookie
        //账号密码验证成功了才存入cookie
        if(result.getStatus() == 200) {
            CookieUtils.setCookie(request,response,TT_TOKEN,JsonUtils.objectToJson(result.getData().toString()));
        }
        return result;
    }

    //jsonp的get请求 去限定了以后 进来不聊这个方法
    @RequestMapping(value = "/token/{token}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    //注意 这个方法 是校验用户是否登录的方法，但是呢 www.taoato.com www.search.com www.item.com 这三个不同的域 都会调用校验是否登录的操作 所以这里应该是 跨域了的
    public String getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        //走到这里 就是跨域请求
        if (StringUtils.isNotBlank(callback)) {
            String json = callback + "(" + JsonUtils.objectToJson(result) + ");";
            return json;
        }
        //走到这里 就是当工程
        return JsonUtils.objectToJson(result);
    }

    @RequestMapping("/logout/{token}")
    @ResponseBody

    public TaotaoResult logoutUser(@PathVariable String token){

        TaotaoResult result = userService.loginoutUser(token);
        return result;
    }
}
