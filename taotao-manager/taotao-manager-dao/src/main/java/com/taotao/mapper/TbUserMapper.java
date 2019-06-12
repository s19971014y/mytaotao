package com.taotao.mapper;


import com.taotao.pojo.TbUser;

public interface TbUserMapper {
    /**
     * 查询用户信息
     * @param user
     * @return
     */
    TbUser checkUser(TbUser user);

    /**
     * 插入一个用户信息到用户表中
     * @param tbUser 用户信息对象
     */
    void insert(TbUser tbUser);

    /**
     * 根据用户账号密码查询用户信息
     * @param userName 账号
     * @param passWord 密码
     * @return 返回指定用户账号与密码的用户对象
     */
    TbUser getUserByUserAndPass(String userName, String passWord);
}