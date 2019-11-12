package com.mark.dockerproject.service.business.mq.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.mark.dockerproject.dao.pay.CustomerAccountDao;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
        System.err.println("执行本地事务单元------------");
        CountDownLatch currentCountDown = null;
        try {
            Map<String,Object> params = (Map<String,Object>)arg;
            String userId = (String)params.get("userId");
            String accountId = (String)params.get("accountId");
            String orderId = (String)params.get("orderId");
            BigDecimal payMoney = (BigDecimal)params.get("payMoney");	//	当前的支付款
            BigDecimal newBalance = (BigDecimal)params.get("newBalance");	//	前置扣款成功的余额
            int currentVersion = (int)params.get("currentVersion");
            currentCountDown = (CountDownLatch)params.get("currentCountDown");
            Date currentTime = new Date();
            int count = customerAccountDao.updateBalance(accountId,newBalance,currentVersion,currentTime);
            if(count == 1) {
                currentCountDown.countDown();
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                currentCountDown.countDown();
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            currentCountDown.countDown();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return null;
    }
}
