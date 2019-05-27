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

    /**
     * 添加一个内容分类到数据库
     * @param tbContentCategory 内容分类对象
     */
    void insert(TbContentCategory tbContentCategory);

    /**
     * 根据父节点查询当前内容分类
     * @param parentId 父节点id
     * @return 返回该父节点指定的内容分类信息
     */
    TbContentCategory findContentCategoryByParentId(Long parentId);

    /**
     * 修改内容分类信息
     * @param tbContentCategory 需要修改的内容分类
     */

    void updateCategoryIsParent(TbContentCategory tbContentCategory);
}