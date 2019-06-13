package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.result.EasyUITreeNode;
import com.taotao.result.ItemCat;
import com.taotao.result.ItemCatResult;
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

    @Override
    public ItemCatResult findItemCatAll(Long parentId) {
        //因为需要返回一个 ItemCatResult对象， 里面有个集合数据，集合数据有14个，这14数据是展示数据信息的
        ItemCatResult result = new ItemCatResult();
        result.setData(getItemCatAll(parentId));
        return result;
    }

    //完成数据的装载
    private List<ItemCat> getItemCatAll(Long parentId){

        List result = new ArrayList<>();
        List<TbItemCat> itemCats = tbItemCatMapper.findItemCatByParentId(parentId);

        int count = 0;
        for(TbItemCat itemCat:itemCats){
            ItemCat item = new ItemCat();
            //设置u
            item.setUrl("/products/"+itemCat.getId()+".html");
            if(itemCat.getIsParent()){
                if(parentId == 0){
                    //设置n
                    item.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                }else {
                    //表示的是第二级目录
                    item.setName(itemCat.getName());
                }
                count++;
                //设置i
                item.setItem(getItemCatAll(itemCat.getId()));
                //把第一级目录和第二级目录添加到结果集 list集合里面去
                result.add(item);

                //限制只显示14个第一级目录
                if(parentId == 0 && count >= 14){
                    break;
                }
            } else {
                //最后一级应该是
                result.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }

        }
        return result;
    }
}
