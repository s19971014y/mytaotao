package com.taotao.order.interceptor;

import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Value("${TT_TOKEN}")
    private String TT_TOKEN;
    @Value("${SSO_LOGIN_URL}")
    private String SSO_LOGIN_URL;

    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        /**
         * 1、从cookie中取 token
         *    取到
         *              拿着token
         *              去请求单点登录系统
         *              找到
         *                     直接显示用户并且放行
         *               找不到用户
         *                      redis过期
         *    娶不到
         *              直接跳转登录页面
         */

        String token = CookieUtils.getCookieValue(request,TT_TOKEN,true);
        //判断是空的意思
        if(StringUtils.isBlank(token)){
            String url = request.getRequestURL().toString();
            response.sendRedirect(SSO_LOGIN_URL+"?url="+url);
            //拦截
            return false;
        }

        //判断TaotaoResult的data数据里面是否为false
        TaotaoResult result = userService.getUserByToken(token);
        if(result.getStatus() != 200){
            String url = request.getRequestURL().toString();
            response.sendRedirect(SSO_LOGIN_URL+"?url="+url);
            //拦截
            return false;
        }

        //因为走到这里就表示 cookie里面有token token没有过期  data为用户的json
        TbUser user = (TbUser) result.getData();
        //在把这个东西存入域中
        request.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
