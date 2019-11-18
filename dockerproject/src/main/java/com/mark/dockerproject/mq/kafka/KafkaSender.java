package com.mark.dockerproject.mq.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSender {

    private KafkaTemplate<String, String> kafkaTemplate;
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    /**
     * send message
     */
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }



}
