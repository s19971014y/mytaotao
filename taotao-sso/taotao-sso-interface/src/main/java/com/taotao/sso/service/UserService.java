package com.taotao.sso.service;

import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;

public interface UserService {
    /**
     * 校验用户账号或者邮箱
     * @param param 需要校验的数据
     * @param type 校验的类型(1:账号 2:手机号 3:邮箱)
     * @return TaotaoResult对象(200表示成功，msg表示返回信息，data表示数据是否可用)
     */
    TaotaoResult checkData(String param,int type);

    /**
     * 注册用户
     * @param tbUser 需要注册的用户信息
     * @return  200成功 400失败
     */
    TaotaoResult createUser(TbUser tbUser);


    /**
     * 用户登录
     * @param userName 用户账号
     * @param passWord 用户密码
     * @return
     */
    TaotaoResult loginUser(String userName,String passWord);

    /**
     * 根据token从redis中查询用户信息
     * @param token
     * @return
     */
    TaotaoResult getUserByToken(String token);


    /**
     * 根据token删除redis中指定的数据
     * @param token 用户token
     * @return 删除指定的token下的用户信息，或者该token没有存在
     */
    TaotaoResult loginoutUser(String token);
}
