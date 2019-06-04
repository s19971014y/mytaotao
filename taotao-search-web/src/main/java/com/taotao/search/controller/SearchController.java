package com.taotao.search.controller;

import com.taotao.result.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    @Value(("${ITEM_ROWS}"))
    private  Integer ITEM_ROWS;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    @ResponseBody
    public SearchResult search(@RequestParam String q, @RequestParam(defaultValue = "1") int page, Model model) throws Exception {
        //这里的请求是get请求，不能解决post中文乱码问题
        byte[] bytes = q.getBytes("ISO-8859-1");

        String queryString =  new String(bytes,"UTF-8");
        SearchResult result = searchService.search(queryString, page, 60);
        //页面那边 他说不要json格式你给我传入域对象里面去，页面使用el表达式在装载数据

        //传递给页面
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",result.getPageCount());
        model.addAttribute("itemmList",result.getItemList());
        model.addAttribute("page",page);

        return result;
    }
}
