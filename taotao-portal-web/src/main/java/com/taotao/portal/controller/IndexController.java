package com.taotao.portal.controller;

import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.protal.pojo.Ad1Node;
import com.taotao.result.ItemCatResult;
import com.taotao.service.ItemCatService;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ItemCatService itemCatService;

    @Value("${AD1_CID}")
    private Long AD1_CID;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;


    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> contents = contentService.findConByCategoryId(AD1_CID);

        List<Ad1Node> nodes = new ArrayList<>();

        for(TbContent content:contents){
            Ad1Node node = new Ad1Node();
            node.setAlt(content.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            node.setHref(content.getUrl());
            node.setSrc(content.getPic());
            node.setSrcB(content.getPic2());

            nodes.add(node);
        }

        model.addAttribute("ad1", JsonUtils.objectToJson(nodes));

        return "index";
    }

    //http://localhost:8082/item/cat/itemcat/all.html
    @RequestMapping("/item/cat/itemcat/all")
    @ResponseBody
    public String queryAll(String callback){
        ItemCatResult result = itemCatService.findItemCatAll(Long.valueOf(0));
        if(StringUtils.isNotBlank(callback)){
            String jsonp = callback +"(" +JsonUtils.objectToJson(result) +")";
            return jsonp;
        }
        return JsonUtils.objectToJson(result);
    }
}
