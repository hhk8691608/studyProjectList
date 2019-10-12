package com.bfxy.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bfxy.order.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	//	超时降级
//	@HystrixCommand(
//				commandKey = "createOrder",
//				commandProperties = {
//						@HystrixProperty(name="execution.timeout.enabled", value="true"),
//						@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
//				},
//				fallbackMethod = "createOrderFallbackMethod4Timeout"
//			)
	
	//	限流策略：线程池方式
//	@HystrixCommand(
//				commandKey = "createOrder",
//				commandProperties = {
//						@HystrixProperty(name="execution.isolation.strategy", value="THREAD")
//				},
//				threadPoolKey = "createOrderThreadPool",
//				threadPoolProperties = {
//						@HystrixProperty(name="coreSize", value="10"),
//						@HystrixProperty(name="maxQueueSize", value="20000"),
//						@HystrixProperty(name="queueSizeRejectionThreshold", value="30")
//				},
//				fallbackMethod="createOrderFallbackMethod4Thread"
//			)
	
	//	限流策略：信号量方式
	
//	@HystrixCommand(
//				commandKey="createOrder",
//				commandProperties= {
//						@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
//						@HystrixProperty(name="execution.isolation.semaphore.maxConcurrentRequests", value="3")
//				},
//				fallbackMethod = "createOrderFallbackMethod4semaphore"
//			)
	@RequestMapping("/createOrder")
	public String createOrder(@RequestParam("cityId")String cityId, 
			@RequestParam("platformId")String platformId,
			@RequestParam("userId")String userId,
			@RequestParam("supplierId")String supplierId,
			@RequestParam("goodsId")String goodsId) throws Exception {
		
		return orderService.createOrder(cityId, platformId, userId, supplierId, goodsId) ? "下单成功!" : "下单失败!";
	}
	
	public String createOrderFallbackMethod4Timeout(@RequestParam("cityId")String cityId, 
			@RequestParam("platformId")String platformId,
			@RequestParam("userId")String userId,
			@RequestParam("suppliedId")String suppliedId,
			@RequestParam("goodsId")String goodsId) throws Exception {
		System.err.println("-------超时降级策略执行------------");
		return "hysrtix timeout !";
	}
	
	public String createOrderFallbackMethod4Thread(@RequestParam("cityId")String cityId, 
			@RequestParam("platformId")String platformId,
			@RequestParam("userId")String userId,
			@RequestParam("suppliedId")String suppliedId,
			@RequestParam("goodsId")String goodsId) throws Exception {
		System.err.println("-------线程池限流降级策略执行------------");
		return "hysrtix threadpool !";
	}
	
	public String createOrderFallbackMethod4semaphore(@RequestParam("cityId")String cityId, 
			@RequestParam("platformId")String platformId,
			@RequestParam("userId")String userId,
			@RequestParam("suppliedId")String suppliedId,
			@RequestParam("goodsId")String goodsId) throws Exception {
		System.err.println("-------信号量限流降级策略执行------------");
		return "hysrtix semaphore !";
	}
}
