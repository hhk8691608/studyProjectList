package com.mark.dockerproject.service.business;

import com.mark.dockerproject.Const.MQConst;
import com.mark.dockerproject.dao.pay.CustomerAccountDao;
import com.mark.dockerproject.model.pay.CustomerAccount;
import com.mark.dockerproject.mq.transactionMq.producer.TransactionProducer;
import com.mark.dockerproject.service.business.mq.CallbackService;
import com.mark.dockerproject.utils.FastJsonConvertUtil;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Service
public class PayService {

    @Autowired
    private CallbackService callbackService;

    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Autowired
    private TransactionProducer transactionProducer;

    public String payment(String userId, String orderId, String accountId, double money) {
        String paymentRet = "";
        try {
                    BigDecimal payMoney = new BigDecimal(money);
                    //0 token验证操作（重复提单问题）
                    //1 redis去重 分布式锁
                    //2 数据库乐观锁去重
                    //	做扣款操作的时候：获得分布式锁，看一下能否获得
                    //加锁开始（获取）---数据库乐观锁去重
                    CustomerAccount old = customerAccountDao.findAccountById(accountId);
                    BigDecimal currentBalance = old.getCurrentBalance();
                    int currentVersion = old.getVersion();

                    BigDecimal newBalance = currentBalance.subtract(payMoney);//TODO ??
                    //加锁结束（释放）

                    if(newBalance.doubleValue() > 0){
                        //	1.组装消息
                        //  1.执行本地事务
                        String keys = UUID.randomUUID().toString() + "$" + System.currentTimeMillis();
                        Map<String, Object> params = new HashMap<>();
                        params.put("userId", userId);
                        params.put("orderId", orderId);
                        params.put("accountId", accountId);
                        params.put("money", money);	//100

                        Message message = new Message(MQConst.TX_PAY_TOPIC,MQConst.TX_PAY_TAGS, keys, FastJsonConvertUtil.convertObjectToJSON(params).getBytes());
                        //	可能需要用到的参数
                        params.put("payMoney", payMoney);
                        params.put("newBalance", newBalance);
                        params.put("currentVersion", currentVersion);

                        //	同步阻塞
                        CountDownLatch countDownLatch = new CountDownLatch(1);
                        params.put("currentCountDown", countDownLatch);
                        //	消息发送并且 本地的事务执行
                        TransactionSendResult sendResult = transactionProducer.sendMessage(message,params);

                        countDownLatch.await();
                        if(sendResult.getSendStatus() == SendStatus.SEND_OK
                                && sendResult.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE){
                            //	回调order通知支付成功消息
                            paymentRet = "支付成功!";
//                            paySuccess2Order(orderId,userId);
                        }else{
                            paymentRet = "支付失败!";
                        }

                    }else {
                        paymentRet = "余额不足!";
                    }
            } catch (Exception e) {
                e.printStackTrace();
                paymentRet = "支付失败!";
            }
        return paymentRet;
    }

    //回调order通知支付成功消息
    public void paySuccess2Order(String orderId,String userId){
        callbackService.sendOKMessage(orderId,userId);
    }


    public void testAPI(String accountId, int currentVersion){
        CustomerAccount old = customerAccountDao.findAccountById(accountId);
        System.out.println(old.toString());
        System.out.println("--------------------------");
        BigDecimal newBalance = new BigDecimal(250.25);
        Date currentTime = new Date();
        int updateCode = customerAccountDao.updateBalance(accountId,newBalance,currentVersion,currentTime);
        System.out.println("is update success code = "+updateCode);

    }

}
