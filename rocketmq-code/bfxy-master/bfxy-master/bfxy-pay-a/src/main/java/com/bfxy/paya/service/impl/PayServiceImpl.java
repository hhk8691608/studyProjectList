package com.bfxy.paya.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfxy.paya.entity.CustomerAccount;
import com.bfxy.paya.mapper.CustomerAccountMapper;
import com.bfxy.paya.service.PayService;
import com.bfxy.paya.service.producer.CallbackService;
import com.bfxy.paya.service.producer.TransactionProducer;
import com.bfxy.paya.utils.FastJsonConvertUtil;

@Service
public class PayServiceImpl implements PayService {

	public static final String TX_PAY_TOPIC = "tx_pay_topic";
	
	public static final String TX_PAY_TAGS = "pay";
	
	@Autowired
	private CustomerAccountMapper customerAccountMapper;
	
	@Autowired
	private TransactionProducer transactionProducer;
	
	@Autowired
	private CallbackService callbackService;
	
	@Override
	public String payment(String userId, String orderId, String accountId, double money) {
		String paymentRet = "";
		try {
			//	最开始有一步 token验证操作（重复提单问题）
			
			BigDecimal payMoney = new BigDecimal(money);
			
			//加锁开始（获取）
			
			CustomerAccount old = customerAccountMapper.selectByPrimaryKey(accountId);
			BigDecimal currentBalance = old.getCurrentBalance();
			int currentVersion = old.getVersion();
			//	要对大概率事件进行提前预判（小概率事件我们做放过,但是最后保障数据的一致性即可）
			//业务出发:
			//当前一个用户账户 只允许一个线程（一个应用端访问）
			//技术出发：
			//1 redis去重 分布式锁
			//2 数据库乐观锁去重
			//	做扣款操作的时候：获得分布式锁，看一下能否获得
			BigDecimal newBalance = currentBalance.subtract(payMoney);
			
			//加锁结束（释放）
			
			if(newBalance.doubleValue() > 0 ) {	//	或者一种情况获取锁失败
				//	1.组装消息
				//  1.执行本地事务
				String keys = UUID.randomUUID().toString() + "$" + System.currentTimeMillis();
				Map<String, Object> params = new HashMap<>();
				params.put("userId", userId);
				params.put("orderId", orderId);
				params.put("accountId", accountId);
				params.put("money", money);	//100
				
				Message message = new Message(TX_PAY_TOPIC, TX_PAY_TAGS, keys, FastJsonConvertUtil.convertObjectToJSON(params).getBytes());
				//	可能需要用到的参数
				params.put("payMoney", payMoney);
				params.put("newBalance", newBalance);
				params.put("currentVersion", currentVersion);
				
				//	同步阻塞
				CountDownLatch countDownLatch = new CountDownLatch(1);
				params.put("currentCountDown", countDownLatch);
				//	消息发送并且 本地的事务执行
				TransactionSendResult sendResult = transactionProducer.sendMessage(message, params);
			
				countDownLatch.await();
				
				if(sendResult.getSendStatus() == SendStatus.SEND_OK 
						&& sendResult.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
					//	回调order通知支付成功消息
					callbackService.sendOKMessage(orderId, userId);
					paymentRet = "支付成功!";
				} else {
					paymentRet = "支付失败!";
				}
			} else {
				paymentRet = "余额不足!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			paymentRet = "支付失败!";
		}
		return paymentRet;
	}

}
