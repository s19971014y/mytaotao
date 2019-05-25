package com.taotao.controller;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.result.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/content/category")
public class ContentController {
    @Autowired
    public ContentCategoryService contentCategoryService;

    @RequestMapping("list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id",defaultValue = "0")Long parentId){

        List<EasyUITreeNode> result = contentCategoryService.getContentCategoryList(parentId);

        return result;
    }
}
