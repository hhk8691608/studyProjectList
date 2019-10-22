package com.itmayiedu.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingapi.tx.annotation.TxTransaction;
import com.itmayeidu.api.service.PayOrderService;
import com.itmayiedu.base.ResponseBase;
import com.itmayiedu.constants.Constants;
import com.itmayiedu.dao.PaymentInfoDao;
import com.itmayiedu.entity.PaymentInfo;
import com.itmayiedu.fegin.OrderServiceFegin;

@RestController
public class PayOrderServiceImpl implements PayOrderService {
	@Autowired
	private PaymentInfoDao paymentInfoDao;
	@Autowired
	private OrderServiceFegin orderServiceFegin;

	@SuppressWarnings("unused")
	@TxTransaction(isStart = true)
	@Transactional
	public String payOrder(@RequestParam("orderId") String orderId, @RequestParam("temp") int temp) {
		PaymentInfo paymentInfo = paymentInfoDao.getByOrderIdPayInfo(orderId);
		if (paymentInfo == null) {
			return Constants.PAY_FAIL;
		}

		// 支付宝重试机制
		Integer state = paymentInfo.getState();
		if (state == 1) {
			return Constants.PAY_SUCCESS;
		}

		// 支付宝交易号
		String tradeNo = "644064779";
		paymentInfo.setState(1);// 标识为已经支付
		paymentInfo.setPayMessage("test");
		paymentInfo.setPlatformorderId(tradeNo);
		// 手动 begin begin
		Integer updateResult = paymentInfoDao.updatePayInfo(paymentInfo);
		if (updateResult <= 0) {
			return Constants.PAY_FAIL;
		}

		// 调用订单接口通知 支付状态
		ResponseBase orderResult = orderServiceFegin.updateOrderIdInfo(1l, tradeNo, orderId);
		if (!orderResult.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
			// 回滚 手动回滚 rollback

			return Constants.PAY_FAIL;
		} // 2PC 3PC TCC MQ
		int i = 1 / temp;
		return Constants.PAY_SUCCESS;
	}

}
