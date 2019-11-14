package com.stylefeng.guns.rest.mq.producer;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.mq.constant.MqConst;
import com.stylefeng.guns.api.order.mq.producer.TestProducerServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service(interfaceClass = TestProducerServiceAPI.class)
public class TestProducerServiceAPIImpl implements TestProducerServiceAPI {

    private DefaultMQProducer producer;

    private TestProducerServiceAPIImpl(){
        this.producer = new DefaultMQProducer(MqConst.TEST_MQ_PRODUCER_GROUP_NAME);
        this.producer.setNamesrvAddr(MqConst.NAMESERVER);
        this.producer.setSendMsgTimeout(3000);
        start();
    }

    public void start(){
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.producer.shutdown();
    }

    @Override
    public void sendTestMessage(Message msg) {

        try {
            SendResult sendResult = this.producer.send(msg);
            System.out.printf("%s%n", sendResult);
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
