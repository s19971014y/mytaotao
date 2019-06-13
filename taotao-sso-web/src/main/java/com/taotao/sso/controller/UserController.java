package com.taotao.sso.controller;

import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
    public TaotaoResult login(String userName, String passWord){
        TaotaoResult result = userService.loginUser(userName, passWord);
        return result;
    }

    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult getUserByToken(@PathVariable String token){
        TaotaoResult result = userService.getUserByToken(token);
        return result;
    }

    @RequestMapping("/logout/{token}")
    @ResponseBody
    public TaotaoResult logoutUser(@PathVariable String token){
        TaotaoResult result = userService.loginoutUser(token);
        return result;
    }
}
