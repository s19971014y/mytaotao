package test.com.taotao.search.service.impl; 

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/** 
* MyTest Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 31, 2019</pre> 
* @version 1.0 
*/ 
public class MyTestTest { 

    @Test
    public void add() throws IOException, SolrServerException {

        //solr服务对象
        SolrServer solrServer = new HttpSolrServer("http://47.101.212.18:8080/solr");

        //solr文档对象
        SolrInputDocument document = new SolrInputDocument();

        //使用solr文档对象往里面添加域对象
        document.addField("id","002");
        document.addField("item_title","华为p30");
        document.addField("item_sell_point","摄像头30w倍缩放");
        document.addField("item_price","6999");


        solrServer.add(document);
        solrServer.commit();



    }

    @Test
    public void query2() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://47.101.212.18:8080/solr");
        SolrQuery query = new SolrQuery();

        query.setQuery("华为");
        query.set("df","item_keywords");

        query.setHighlight(true);
        query.addHighlightField("item_title");

        query.setHighlightSimplePost("<em>");
        query.setHighlightSimplePost("</em>");

        QueryResponse response = solrServer.query(query);

        SolrDocumentList results = response.getResults();
        for(SolrDocument document:results){
            System.out.println("商品的id为"+document.get("id"));
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            List<String> list = highlighting.get(document.get("id")).get("item_title");

            String itemTitle = null;

            if(list != null && list.size()>0){
                itemTitle = list.get(0);
            }else {
                //如果集合为null
                itemTitle = (String) document.get("item_title");
            }

            System.out.println("商品的名称为:"+itemTitle);

        }

    }

} 
