package test.com.taotao.search.service.impl; 

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


import javax.jms.*;
import javax.sound.midi.Soundbank;
import javax.xml.soap.Text;
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
    public void demo1() throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://47.101.212.18:61616");
        Connection connection = factory.createConnection();
        connection.start();

        //是否开启事务
        //是否自动应答机制开启
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue test");
        //通过一个点对点对象创建一个生产者对象
        MessageProducer producer = session.createProducer(queue);
        //Message消息对象  有五种写法
        //字符串类型的消息对象
        TextMessage message = new ActiveMQTextMessage();
        message.setText("point to point");
        producer.send(message);
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void demo2() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://47.101.212.18:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue test");

        //消费者
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message instanceof  TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {//只有当有消息的时候才会执行
                        System.out.println("接收到了数据，数据如下：");
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.out.println("等待接收数据");
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }@Test
    public void demo3() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://47.101.212.18:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
       //发布者对象的名称
        Topic topic = session.createTopic("test topic");

        MessageProducer producer = session.createProducer(topic);

        TextMessage message = new ActiveMQTextMessage();
        message.setText("point to point");
        producer.send(message);

        producer.close();
        session.close();
        connection.close();
    }@Test
    public void demo4() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://47.101.212.18:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
       //发布者对象的名称
        Topic topic = session.createTopic("test topic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof  TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("接收到了数据  数据如下：");

                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.out.println("等待接收数据");
        System.in.read();

        session.close();
        connection.close();
    }

} 
