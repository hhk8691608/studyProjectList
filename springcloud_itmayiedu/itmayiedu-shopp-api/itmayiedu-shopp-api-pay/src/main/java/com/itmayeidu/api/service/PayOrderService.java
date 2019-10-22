package com.itmayeidu.api.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itmayiedu.base.ResponseBase;

@RequestMapping("/pay")
public interface PayOrderService {

	@RequestMapping("/payOrder")
	public String payOrder(@RequestParam("orderId") String orderId,@RequestParam("temp") int temp);

}
