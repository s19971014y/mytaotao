package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.result.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<EasyUITreeNode> getCatList(Long parentId) {
        List<TbItemCat> tbItamCats = tbItemCatMapper.findItemCatByParentId(parentId);
        List<EasyUITreeNode> result = new ArrayList<>();
       for(TbItemCat itemCat:tbItamCats){
           EasyUITreeNode node = new EasyUITreeNode();
           node.setId(itemCat.getId());
           node.setText(itemCat.getName());
           node.setState(itemCat.getIsParent()?"closed":"open");
           result.add(node);
       }
        return result;
    }
}
