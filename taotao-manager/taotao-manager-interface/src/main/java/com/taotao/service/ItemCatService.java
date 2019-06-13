package com.taotao.service;

import com.taotao.result.EasyUITreeNode;
import com.taotao.result.ItemCatResult;

import java.util.List;

public interface ItemCatService {
    /**
     * 根据分类 id 查询信息
     * @param parentId 父级分类id
     * @return 返回集合 有三个属性 id name state
     */
    List<EasyUITreeNode> getCatList(Long parentId);

    ItemCatResult findItemCatAll(Long parentId);
}
