package com.stylefeng.guns.api.order.mq.constant;

import java.io.Serializable;

public class MqConst implements Serializable {

    public static final String NAMESERVER = "192.168.232.129:9876;192.168.232.130:9876";

    public static final String TEXT_MQ_TOPIC = "text_mq_topic";

    public static final String TEXT_MQ_TAGS = "testmq";

    public static final String TEST_MQ_PRODUCER_GROUP_NAME = "test_mq_producer_group_name";

    public static final String TEST_MQ_CONSUMER_GROUP_NAME = "test_mq_consumer_group_name";


}
