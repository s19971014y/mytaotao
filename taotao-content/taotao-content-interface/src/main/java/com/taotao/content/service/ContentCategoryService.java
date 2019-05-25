package com.taotao.content.service;

import com.taotao.result.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {

    /**
     * 查询所有内容分类信息
     * @param parentId  父类ID
     * @return 返回EasyUITreeNode对象(id,text,state)
     */

    List<EasyUITreeNode> getContentCategoryList(Long parentId);
}
