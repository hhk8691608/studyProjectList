package com.stylefeng.guns.rest.mq.consumer;

import com.alibaba.dubbo.config.annotation.Service;
import com.mysql.cj.protocol.MessageListener;
import com.stylefeng.guns.api.order.mq.constant.MqConst;
import com.stylefeng.guns.api.order.mq.consumer.TestConsumerServiceAPI;
import com.stylefeng.guns.api.order.mq.producer.TestProducerServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Component
@Service(interfaceClass = TestConsumerServiceAPI.class)
public class TestConsumerServiceAPIImpl implements TestConsumerServiceAPI {

    private DefaultMQPushConsumer consumer;

    private TestConsumerServiceAPIImpl() throws MQClientException {
        this.consumer = new DefaultMQPushConsumer(MqConst.TEST_MQ_CONSUMER_GROUP_NAME);
        this.consumer.setConsumeThreadMin(10);
        this.consumer.setConsumeThreadMin(30);
        this.consumer.setNamesrvAddr(MqConst.NAMESERVER);
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        this.consumer.subscribe(MqConst.TEXT_MQ_TOPIC, MqConst.TEXT_MQ_TAGS);
        this.consumer.setMessageListener(new TestListener());
        this.consumer.start();
    }

    class TestListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            try {
                for(MessageExt msg:list){
                        String msgBody = new String(msg.getBody(),"utf-8");
                        System.out.println("  MessageBody: "+ msgBody);//输出消息内容
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace(); 
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
        }
    }

    @Override
    public void consumerTestMsg() {

    }


}
