package com.mark.dockerproject.service.business.mq;

import com.mark.dockerproject.Const.MQConst;
import com.mark.dockerproject.service.business.mq.producer.SyncProducer;
import com.mark.dockerproject.utils.FastJsonConvertUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CallbackService {

    @Autowired
    private SyncProducer syncProducer;

    public void sendOKMessage(String orderId, String userId) {

        Map<String,Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("orderId", orderId);
        params.put("status", "2");	//ok

        String keys = UUID.randomUUID().toString() + "$" + System.currentTimeMillis();
        Message message = new Message(MQConst.CALLBACK_PAY_TOPIC,MQConst. CALLBACK_PAY_TAGS, keys, FastJsonConvertUtil.convertObjectToJSON(params).getBytes());
        SendResult ret = syncProducer.sendMessage(message);
    }

}
