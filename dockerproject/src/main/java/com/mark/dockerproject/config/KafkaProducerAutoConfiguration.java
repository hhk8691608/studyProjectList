package com.mark.dockerproject.config;

import com.mark.dockerproject.mq.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerAutoConfiguration {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    public KafkaSender kafkaSender(){
        return new KafkaSender(kafkaTemplate);
    }


}
