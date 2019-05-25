package com.taotao.mapper;


import com.taotao.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryMapper {

    /**
     * 根据内容id查询所有分类信息
     * @param parentId 内容个分类id
     * @return 返回指定内容分类id下的所有分类信息
     */
    List<TbContentCategory> findTbContentCategoryById(Long parentId);
}