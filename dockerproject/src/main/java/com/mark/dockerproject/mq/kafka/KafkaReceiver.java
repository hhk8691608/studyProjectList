package com.mark.dockerproject.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaReceiver {

    // 配置监听的主体，groupId 和配置文件中的保持一致
    @KafkaListener(topics = { KafkaContants.TRADE_TOPIC }, groupId = "test-consumer-group")
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("consumer get message is  "+ message);
        }
    }

}
