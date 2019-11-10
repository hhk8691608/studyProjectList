package com.mark.dockerproject.service.business.mq.consumer;

import com.mark.dockerproject.Const.MQConst;
import com.mark.dockerproject.utils.FastJsonConvertUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class OrderConsumer {

    private DefaultMQPushConsumer consumer;

    public OrderConsumer() throws MQClientException {
        consumer = new DefaultMQPushConsumer("callback_pay_consumer_group");
        consumer.setConsumeThreadMin(10);
        consumer.setConsumeThreadMax(50);
        consumer.setNamesrvAddr(MQConst.NAMESERVER);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe(MQConst.CALLBACK_PAY_TOPIC, MQConst.CALLBACK_PAY_TAGS);
        consumer.registerMessageListener(new MessageListenerConcurrently4Pay());
        consumer.start();
    }

    class MessageListenerConcurrently4Pay implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            MessageExt msg = msgs.get(0);
            try {
                System.out.println("回调订单方开始....");
                String topic = msg.getTopic();
                String msgBody = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                String keys = msg.getKeys();
                System.err.println("收到消息：" + "  topic :" + topic + "  ,tags : " + tags + "keys :" + keys + ", msg : " + msgBody);
                String orignMsgId = msg.getProperties().get(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID);
                System.err.println("orignMsgId: " + orignMsgId);

                //通过keys 进行去重表去重 或者使用redis进行去重???? --> 不需要

                Map<String, Object> body = FastJsonConvertUtil.convertJSONToObject(msgBody, Map.class);
                String orderId = (String) body.get("orderId");
                String userId = (String) body.get("userId");
                String status = (String)body.get("status");

                Date currentTime = new Date();

                //TODO 完成物流派发

            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

    }

}
