package com.mark.dockerproject.mq.orderlyMq;


import com.mark.dockerproject.Const.MQConst;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderlyProducer {

    private DefaultMQProducer producer;


    public static final String PRODUCER_GROUP_NAME = "orderly_producer_group_name";

    private OrderlyProducer() {
        this.producer = new DefaultMQProducer(PRODUCER_GROUP_NAME);
        this.producer.setNamesrvAddr(MQConst.NAMESERVER);
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

        for(Message me : messageList){

            try {
                this.producer.send(me, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        Integer id = (Integer) o;
                        return list.get(id);
                    }
                },messageQueueNumber);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


}
