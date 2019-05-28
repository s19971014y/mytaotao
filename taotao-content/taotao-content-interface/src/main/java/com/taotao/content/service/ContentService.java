package com.taotao.content.service;

import com.taotao.pojo.TbContent;
import com.taotao.result.EasyUIResult;
import com.taotao.result.TaotaoResult;

import java.util.List;

public interface ContentService {
    /**
     * 根据内容分类id查询内容信息
     * @param categoryId 内容分类id
     * @return EasyUIResult对象里面包含了内容总记录条数(total)，所有内容对象(List<?></>)
     */
    EasyUIResult findContentAll(Long categoryId);

    /**
     * 添加一个内容信息
     * @param tbContent 需要添加的内容信息
     * @return TaotaoResult   状态码为200则表示成功
     */
    TaotaoResult addContent(TbContent tbContent);

    /**
     * 根据指定内容分类id查询该分类下的所有内容信息集合对象
     * @param categoryId  分类id
     * @return 指定分类下的内容信息
     */
    List<TbContent> findConByCategoryId(Long categoryId);
}
