package com.mark.dockerproject.mq.transactionMq.producer;


import com.mark.dockerproject.Const.MQConst;
import com.mark.dockerproject.service.business.mq.consumer.TransactionListenerImpl;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class TransactionProducer implements InitializingBean {

    private TransactionMQProducer producer;

    private ExecutorService executorService;

    @Autowired
    private TransactionListenerImpl transactionListenerImpl;

    private static final String PRODUCER_GROUP_NAME = "tx_pay_producer_group_name";

    private TransactionProducer() {
        this.producer = new TransactionMQProducer(PRODUCER_GROUP_NAME);
        this.executorService = new org.apache.tomcat.util.threads.ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(PRODUCER_GROUP_NAME + "-check-thread");
                return thread;
            }
        });
        this.producer.setExecutorService(executorService);
        this.producer.setNamesrvAddr(MQConst.NAMESERVER);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.producer.setTransactionListener(transactionListenerImpl);
        start();
    }

    private void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.producer.shutdown();
    }


    public TransactionSendResult sendMessage(Message message, Object argument) {
        TransactionSendResult sendResult = null;
        try {
            sendResult = this.producer.sendMessageInTransaction(message, argument);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult;
    }



}
