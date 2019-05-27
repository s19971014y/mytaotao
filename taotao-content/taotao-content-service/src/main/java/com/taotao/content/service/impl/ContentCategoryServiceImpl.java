package com.taotao.content.service.impl;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.result.EasyUITreeNode;
import com.taotao.result.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
        List<TbContentCategory> tbcontentCategorys = tbContentCategoryMapper.findTbContentCategoryById(parentId);
        List<EasyUITreeNode> result = new ArrayList<>();
        for(TbContentCategory tbContentCategory: tbcontentCategorys){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            result.add(node);
        }
        return result;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        //传过来参数后，封装成一个TbContentCategory对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        Date time = new Date();
        tbContentCategory.setCreated(time);
        tbContentCategory.setUpdated(time);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        //不管在哪添加，新建出来的都是子节点
        tbContentCategory.setIsParent(false);

        //添加一个内容分类
        tbContentCategoryMapper.insert(tbContentCategory);
        //必须查询他的父节点是否是父节点,如果是，不管，如果不是，改为父节点
        TbContentCategory contentNode = tbContentCategoryMapper.findContentCategoryByParentId(parentId);
        if(!contentNode.getIsParent()){
            contentNode.setIsParent(true);
            //更新到数据库中
            tbContentCategoryMapper.updateCategoryIsParent(contentNode);
        }
        return TaotaoResult.ok(tbContentCategory);
    }
}
