package com.taotao.sso.service.impl;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JedisUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Override
    public TaotaoResult checkData(String param, int type) {
        TbUser user = new TbUser();
        if(1 == type){
            user.setUserName(param);
            TbUser tbUser = tbUserMapper.checkUser(user);
            if(tbUser != null){
                return TaotaoResult.build(400,"账号已经存在",false);
            }
        } else if(2 == type){
            user.setPhone(param);
            TbUser tbUser = tbUserMapper.checkUser(user);
            if(tbUser != null){
                return TaotaoResult.build(400,"手机号已经存在",false);
            }
        } else if(3 == type){
            user.setEmail(param);
            TbUser tbUser = tbUserMapper.checkUser(user);
            if(tbUser != null){
                return TaotaoResult.build(400,"邮箱已经存在",false);
            }
        } else {
            return TaotaoResult.build(400,"非法的参数");
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult createUser(TbUser tbUser) {
        /**
         * 注册一个用户之前需要校验数据
         * 前台校验   后台校验
         * js代码校验用户输入的是否合法
         * 后台校验是否重复
         */

        //校验账号是否为空
        if(StringUtils.isBlank(tbUser.getUserName())){
            return  TaotaoResult.build(400,"账号为空");
        }
        //校验密码是否为空
        if(StringUtils.isBlank(tbUser.getPassWord())){
            return TaotaoResult.build(400,"密码为空");
        }
        //校验手机是否为空
        if(StringUtils.isBlank(tbUser.getPhone())){
            return  TaotaoResult.build(400,"手机为空");
        }
        //校验邮箱是否为空
        if(StringUtils.isBlank(tbUser.getEmail())){
            return  TaotaoResult.build(400,"邮箱为空");
        }
        //校验账号是否重复
        TaotaoResult result = checkData(tbUser.getUserName(),1);
        if(!(boolean)result.getData()){
            return TaotaoResult.build(400,"账号已经存在");
        }
        //校验手机是否重复
        if(StringUtils.isNotBlank(tbUser.getPhone())){
            result = checkData(tbUser.getPhone(),2);
            if(!(boolean)result.getData()){
                return TaotaoResult.build(400,"手机号已经被使用");
            }
        }
        //校验邮箱是否重复
        if(StringUtils.isNotBlank(tbUser.getEmail())){
            result = checkData(tbUser.getEmail(),3);
            if(!(boolean)result.getData()){
                return TaotaoResult.build(400,"邮箱已经被使用");
            }
        }
        //走到这里才能存入数据到数据库
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        tbUser.setPassWord(DigestUtils.md5DigestAsHex(tbUser.getPassWord().getBytes()));
        tbUserMapper.insert(tbUser);
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult loginUser(String userName, String passWord) {

        //校验账号是否为空
        if(StringUtils.isBlank(userName)){
            return  TaotaoResult.build(400,"用户名不能为空");
        }
        //校验密码是否为空
        if(StringUtils.isBlank(passWord)){
            return TaotaoResult.build(400,"密码不能为空");
        }
        //注意密码要md5加密验证
        TbUser tbUser = tbUserMapper.getUserByUserAndPass(userName,passWord);
        if(tbUser == null){
            return TaotaoResult.build(400,"账号密码有误请重新输入");
        }
        //生成一个 随机永远不会重复的字符串
        String token = UUID.randomUUID().toString().replace("-","");
        //注意存入缓存中的用户信息不需要密码
        JedisUtils.set(USER_INFO+":"+token, JsonUtils.objectToJson(tbUser));
        JedisUtils.expire(USER_INFO+":"+token,SESSION_EXPIRE);


        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = JedisUtils.get(USER_INFO+":"+token);
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户没有登录",false);
        }
        //延长登录时间
        JedisUtils.expire(USER_INFO+":"+token,SESSION_EXPIRE);
        //是一个字符串类型的json格式
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);

        return TaotaoResult.ok(tbUser);
    }

    @Override
    public TaotaoResult loginoutUser(String token) {
        Long num = JedisUtils.del(USER_INFO+":"+token);
        if(num == 0){
            return TaotaoResult.build(400,"没有找到该用户",false);
        }
        return TaotaoResult.ok();
    }
}
