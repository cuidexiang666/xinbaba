package cn.itcast.babasport.mqmessage;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;

import cn.itcast.babasport.service.search.SearchService;

public class CustomMessageListener implements MessageListener{

	@Resource
	private SearchService searchService;
	@Override
	public void onMessage(Message message) {
		
		//处理消息体   商品ID
		try {
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
			String id = activeMQTextMessage.getText();
			System.out.println(id);
			searchService.insertProductToSolr(Long.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
