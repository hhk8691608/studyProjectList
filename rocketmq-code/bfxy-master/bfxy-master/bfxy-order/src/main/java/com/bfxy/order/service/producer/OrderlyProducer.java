package com.bfxy.order.service.producer;


import java.util.List;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

@Component
public class OrderlyProducer {

	private DefaultMQProducer producer;
	
	public static final String NAMESERVER = "192.168.11.121:9876;192.168.11.122:9876;192.168.11.123:9876;192.168.11.124:9876";
	
	public static final String PRODUCER_GROUP_NAME = "orderly_producer_group_name"; 
	
	private OrderlyProducer() {
		this.producer = new DefaultMQProducer(PRODUCER_GROUP_NAME);
		this.producer.setNamesrvAddr(NAMESERVER);
		this.producer.setSendMsgTimeout(3000);
		start();
	}
	
	public void start() {
		try {
			this.producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		this.producer.shutdown();
	}
	
	public void sendOrderlyMessages(List<Message> messageList, int messageQueueNumber) {
		for(Message me : messageList) {
			try {
				this.producer.send(me, new MessageQueueSelector() {
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
						Integer id = (Integer)arg;
						return mqs.get(id);
					}
				}, messageQueueNumber);
			} catch (MQClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemotingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MQBrokerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
