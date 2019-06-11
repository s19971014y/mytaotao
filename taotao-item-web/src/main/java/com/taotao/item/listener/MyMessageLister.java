package com.taotao.item.listener;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyMessageLister implements MessageListener {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;


    private BufferedWriter writer;

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;

            try {
                String itemId = textMessage.getText();
                TbItem tbItem = itemService.findItemById(Long.valueOf(itemId));
                Item item = new Item(tbItem);
                TbItemDesc itemDesc = itemService.findItemDescByItemId(Long.valueOf(itemId));
                String itemParam = itemService.findItemParamByItemId(Long.valueOf(itemId));
                Map map = new HashMap();
                map.put("item",item);
                map.put("itemDesc",itemDesc);
                map.put("itemParam",itemParam);
                Configuration configuration = freeMarkerConfig.getConfiguration();
                Template template = configuration.getTemplate("item.ftl");
                writer = new BufferedWriter(new FileWriter("E:\\static\\"+itemId+".html"));
                template.process(map,writer);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (TemplateException e) {
                e.printStackTrace();
            }finally {
                if(writer != null ){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
