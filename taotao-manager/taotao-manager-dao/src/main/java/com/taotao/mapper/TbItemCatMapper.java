package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.TbItemCat;

public interface TbItemCatMapper {

    /**
     * 根据分类的id查询该分类
     * @param parentId 父级类目id的所有分类信息
     * @return
     */
    List<TbItemCat> findItemCatByParentId(Long parentId);
}