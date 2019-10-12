package com.bfxy.paya.service.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfxy.paya.utils.FastJsonConvertUtil;

@Service
public class CallbackService {

	public static final String CALLBACK_PAY_TOPIC = "callback_pay_topic";
	
	public static final String CALLBACK_PAY_TAGS = "callback_pay";
	
	public static final String NAMESERVER = "192.168.11.121:9876;192.168.11.122:9876;192.168.11.123:9876;192.168.11.124:9876";
	
	@Autowired
	private SyncProducer syncProducer;
	
	public void sendOKMessage(String orderId, String userId) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("orderId", orderId);
		params.put("status", "2");	//ok
		
		String keys = UUID.randomUUID().toString() + "$" + System.currentTimeMillis();
		Message message = new Message(CALLBACK_PAY_TOPIC, CALLBACK_PAY_TAGS, keys, FastJsonConvertUtil.convertObjectToJSON(params).getBytes());
		
		SendResult ret = syncProducer.sendMessage(message);	
	}
	

	
}
