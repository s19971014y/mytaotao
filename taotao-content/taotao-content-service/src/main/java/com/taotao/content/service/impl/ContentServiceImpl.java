package com.taotao.content.service.impl;

import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUIResult;
import com.taotao.result.TaotaoResult;
import com.taotao.utils.JedisClusterUtils;
import com.taotao.utils.JedisUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public EasyUIResult findContentAll(Long categoryId) {
        List<TbContent> contents = tbContentMapper.findContentByCategoryId(categoryId);
        EasyUIResult result = new EasyUIResult(contents.size(),contents);
        return result;
    }

    @Override
    public TaotaoResult addContent(TbContent tbContent) {
        Date time = new Date();
        tbContent.setCreated(time);
        tbContent.setUpdated(time);
        tbContentMapper.insertContent(tbContent);
        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> findConByCategoryId(Long categoryId) {

        /**
         * 首先判断redis中是否有数据，有则从redis中获取，并且return返回
         * 如果没有 代码往下执行 查询sql
         *
         */

        String json = JedisClusterUtils.hget(CONTENT_KEY, categoryId + "");
        if(StringUtils.isNotBlank(json)){
            List<TbContent> tbContents = JsonUtils.jsonToList(json, TbContent.class);
            System.out.println("从缓存中拿到了数据");
            return tbContents;
        }

        List<TbContent> result = tbContentMapper.findContentByCategoryId(categoryId);

        System.out.println("从数据库中拿到了数据");

        /**
         * 存入一个散列数据到redis中
         * key叫做CONTENT_KEY
         * value是一个map集合
         *             map集合的key为  分类id value为：内容集合json格式String字符串
         */
        JedisClusterUtils.hset(CONTENT_KEY,categoryId+"", JsonUtils.objectToJson(result));


        /**
         * 把从数据库得到的数据存入redis中 在return返回
         */
        return result;
    }


}
