package com.taotao.controller;

import com.taotao.content.service.ContentService;
import com.taotao.result.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUIResult findAllContentCategoryId(Long categoryId){
        EasyUIResult result = contentService.findContentAll(categoryId);
        return result;
    }
}
