package com.taotao.search.service.impl;

import com.taotao.result.SearchItem;
import com.taotao.result.TaotaoResult;
import com.taotao.search.dao.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;
    
    @Override
    public TaotaoResult importAllItems() throws Exception{
        List<SearchItem> itemList = searchItemMapper.getItemList();
        for(SearchItem item:itemList){

            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSellPoint());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategoryName());
            document.addField("item_desc", item.getItemDesc());

            solrServer.add(document);
        }


        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public SearchItem findItemById(Long id) {
        SearchItem searchItem = searchItemMapper.findItemById(id);
        return searchItem;
    }
}
