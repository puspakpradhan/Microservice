package com.example.demo.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.OrderDetailsNewInfo;
import com.example.demo.model.OrderInfo;
import com.example.demo.model.ProductInfo;

//@FeignClient(name="order-ms", url ="http://order-ms:9196")
@FeignClient(name="order-ms")
public interface OrderServiceFeignClient {
	
	
	@GetMapping("/ordersNew/{orderId}")
	  public OrderInfo getOrderNewByID(@PathVariable String orderId);
	
	
	@GetMapping("/orderDetailsNewFK/{orderId}")
	  public List<OrderDetailsNewInfo> findAllOrdersDetailsFKByOrderId(@PathVariable String orderId);
	
	@GetMapping("/product/{productCode}")
	  public ProductInfo getProductNewByCode(@PathVariable String productCode);

}
