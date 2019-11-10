package com.mark.dockerproject.service.business;


import com.mark.dockerproject.Const.MQConst;
import com.mark.dockerproject.utils.FastJsonConvertUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class PkgOrderlyConsumer {

    public static final String CONSUMER_GROUP_NAME = "orderly_consumer_group_name";


    private DefaultMQPushConsumer consumer;

    private PkgOrderlyConsumer() throws MQClientException {
        this.consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        this.consumer.setConsumeThreadMin(10);
        this.consumer.setConsumeThreadMin(30);
        this.consumer.setNamesrvAddr(MQConst.NAMESERVER);
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        this.consumer.subscribe(MQConst.PKG_TOPIC, MQConst.PKG_TAGS);
        this.consumer.setMessageListener(new PkgOrderlyListener());
        this.consumer.start();
    }

    class PkgOrderlyListener implements MessageListenerOrderly {

        Random random = new Random();

        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {

            for(MessageExt msg : msgs){
                try {
                    String msgid = msg.getMsgId();
                    String topic = msg.getTopic();
                    String msgBody = new String(msg.getBody(),"utf-8");
                    String tags = msg.getTags();
                    String keys = msg.getKeys();
                    System.err.println("收到消息：" + "  topic :" + topic + "  ,tags : " + tags +", msgid="+msgid+ "keys :" + keys + ", msg : " + msgBody);

                    Map<String,Object> body = FastJsonConvertUtil.convertJSONToObject(msgBody,Map.class);
                    String orderNo = (String) body.get("orderNo");
                    String userId = (String) body.get("userId");
                    String text = (String) body.get("text");

                    //	模拟实际的业务耗时操作
                    //	PS: 创建包裹信息  、对物流的服务调用（异步调用）
                    TimeUnit.SECONDS.sleep(random.nextInt(3) + 1);
                    System.err.println("业务操作: " + text);

                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }

            }


            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

}
