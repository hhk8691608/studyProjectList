package com.mark.dockerproject.service.business;

import com.mark.dockerproject.service.business.mq.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {

    @Autowired
    private CallbackService callbackService;

    //回调order通知支付成功消息
    public void paySuccess2Order(String orderId,String userId){
        callbackService.sendOKMessage(orderId,userId);
    }

}
