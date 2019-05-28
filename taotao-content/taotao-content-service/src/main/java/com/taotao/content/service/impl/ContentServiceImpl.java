package com.taotao.content.service.impl;

import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUIResult;
import com.taotao.result.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

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

        return tbContentMapper.findContentByCategoryId(categoryId);
    }


}
