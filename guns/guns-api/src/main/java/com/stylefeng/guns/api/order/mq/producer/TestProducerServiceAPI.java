package com.stylefeng.guns.api.order.mq.producer;

import org.apache.rocketmq.common.message.Message;

public interface TestProducerServiceAPI {

        void sendTestMessage(Message msg);
}
