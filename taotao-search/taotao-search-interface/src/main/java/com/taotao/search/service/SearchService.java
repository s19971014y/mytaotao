package com.taotao.search.service;

import com.taotao.result.SearchResult;

public interface SearchService {

    /**
     * 查询商品信息
     * @param q 查询条件
     * @param page 当前页
     * @param rows 每一页显示的条数
     * @return SearchResult对象，商品集合对象，总记录条数和当前页
     */
    SearchResult search(String q,int page,int rows) throws Exception;
}
