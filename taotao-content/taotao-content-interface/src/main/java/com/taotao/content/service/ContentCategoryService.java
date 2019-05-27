package com.taotao.content.service;

import com.taotao.pojo.TbContentCategory;
import com.taotao.result.EasyUITreeNode;
import com.taotao.result.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    /**
     * 查询所有内容分类信息
     * @param parentId  父类ID
     * @return 返回EasyUITreeNode对象(id,text,state)
     */

    List<EasyUITreeNode> getContentCategoryList(Long parentId);

    /**
     * 添加内容分类信息,注意需要自己组装id,status,sortOrder,isParent,created,updated
     * @param parentId 父类id
     * @param name 名称
     * @return 添加一个内容分类
     */

    TaotaoResult addContentCategory(Long parentId,String name);
}
