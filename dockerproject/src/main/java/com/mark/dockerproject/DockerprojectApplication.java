package com.mark.dockerproject;

import com.mark.dockerproject.mq.kafka.KafkaContants;
import com.mark.dockerproject.mq.kafka.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DockerprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerprojectApplication.class, args);
    }
//    public static void main(String[] args) {
//        ConfigurableApplicationContext run = SpringApplication.run(DockerprojectApplication.class, args);
//        // 这里通过容器获取，正常使用情况下，可以直接使用 Autowired 注入
//        KafkaSender bean = run.getBean(KafkaSender.class);
//        for (int i = 0; i < 3; i++) {
//            //调用消息发送类中的消息发送方法
//            bean.sendMessage(KafkaContants.TRADE_TOPIC, "send a test message"+i);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

}
