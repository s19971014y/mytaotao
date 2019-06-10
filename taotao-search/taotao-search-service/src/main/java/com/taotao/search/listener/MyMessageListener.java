package com.taotao.search.listener;

import com.taotao.pojo.TbItem;
import com.taotao.result.SearchItem;
import com.taotao.search.service.SearchItemService;
import com.taotao.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

public class MyMessageListener implements MessageListener {

    @Autowired
    private SearchItemService itemService;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;

            try {
                String itemId = textMessage.getText();
                SearchItem item = itemService.findItemById(Long.valueOf(itemId));
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSellPoint());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategoryName());
                document.addField("item_desc", item.getItemDesc());
                solrServer.add(document);
                solrServer.commit();
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
