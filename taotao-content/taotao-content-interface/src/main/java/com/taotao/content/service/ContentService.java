package com.taotao.content.service;

import com.taotao.result.EasyUIResult;

public interface ContentService {
    /**
     * 根据内容分类id查询内容信息
     * @param categoryId 内容分类id
     * @return EasyUIResult对象里面包含了内容总记录条数(total)，所有内容对象(List<?></>)
     */
    EasyUIResult findContentAll(Long categoryId);
}
