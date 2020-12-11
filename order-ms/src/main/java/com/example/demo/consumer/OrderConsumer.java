package com.example.demo.consumer;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.OrderDetailsNew;
import com.example.demo.entity.OrderNew;
import com.example.demo.entity.ProductNew;
import com.example.demo.entity.UserOrder;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CartLineInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.repository.OrderRepository;

@Component
public class OrderConsumer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
	public void consumeOrderFromQueue(CartInfo orderReceivedBeans) {
		
		System.out.println("====Message consumed from the queue==="+orderReceivedBeans);
		///
		 logger.info("[Order consumer Order received ]-----[consumeOrderFromQueue() ----calling]--------------");
		 
//		 HttpSession session = request.getSession();
		 String orderID ="";
		 int orderNumber = 0;
		 int totalQuantity = 0;
		 boolean orderPlaceSuccess  = false;
		 boolean orderDBUpdated = false;
		 OrderNew orderNew = new OrderNew();
		 
		 try {
			 
			 CustomerInfo custInfo = orderReceivedBeans.getCustomerInfo();
			 String userID = custInfo.getUserID();
			 List<CartLineInfo> cartLines = orderReceivedBeans.getCartLines();
			 //Step1. Insert in DB
			     //Orders table
//			 OrderNew orderNew = new OrderNew();
			 orderID = generateOrderId();
			 orderNumber = generateOrderNum();
			 orderNew.setId(orderID);
			 orderNew.setOrderNum(orderNumber);
			 orderNew.setAmount(orderReceivedBeans.getAmountTotal());
			 orderNew.setOrderDate(new Date(System.currentTimeMillis()));
			 orderNew.setCustomerAddress(custInfo.getAddress());
			 orderNew.setCustomerName(custInfo.getName());
			 orderNew.setCustomerEmail(custInfo.getEmail());
			 orderNew.setCustomerPhone(custInfo.getPhone());
			 
			 try {
				 orderRepository.insertOrderNew(orderNew);
				 orderDBUpdated = true;
			 }catch(Exception anyException) {
				 orderPlaceSuccess= false;
			 }
			 
	        //UserOrder table
			 
			 UserOrder userOrder = new UserOrder();
			 userOrder.setUserID(userID);
			 userOrder.setOrderID(orderNew.getId());
			 try {
				 orderRepository.insertUserOrder(userOrder);
			 }catch(Exception anyex) {
				 anyex.printStackTrace();
				 
			 }
			   //OrderDetails table
			  for(CartLineInfo cartLine : cartLines) {
				  
//				 OrderDetail orderDetail = new OrderDetail();//Modified to OrderDetailsNew
				 OrderDetailsNew orderDetail = new OrderDetailsNew();//Modified to OrderDetailsNew
				 orderDetail.setId(generateOrderDetailId());
				 orderDetail.setOrderID(orderNew.getId());
				 orderDetail.setAmount(cartLine.getAmount());
				 orderDetail.setPrice(cartLine.getProductInfo().getPrice());
				 orderDetail.setQuanity(cartLine.getQuantity());
				 String prodCode = cartLine.getProductInfo().getCode();
				 ProductNew productNew = new ProductNew();
				 try {
					  productNew = orderRepository.getProductNewByCode(prodCode);
				 }catch(Exception ex) {
					ex.printStackTrace(); 
				 }
				
				 orderDetail.setProductID(prodCode);
				 totalQuantity =  totalQuantity + cartLine.getQuantity();
				 
				 if(orderDBUpdated) {
					 
					 try {
//						 orderDetailsRepository.save(orderDetail);
						int insert = orderRepository.insertOrderDetailsNewWithoutFK(orderDetail);
						 orderPlaceSuccess = true;
						 
					 }catch(Exception anyException) {
						 orderPlaceSuccess = false;
					 }
					
				 }
				
				 
			 }
			  
			//Step2. call Rabbit MQ starts
				logger.info("[Order Controller Place Order]-----[receiveOrder() ----Rabbit MQ calling]-- USERID and  ORDER ID====:::----"+userID+":::"+orderNumber);
				 logger.info("[Order Controller Place Order]-----[receiveOrder() ----Rabbit MQ call end--]--  ORDER ID====::"+orderID);
				 
			 
		 }catch(Exception ex) {
			 logger.error("[Order Controller Place Order]-----[receiveOrder() ]----[Rabbit MQ call]------ Exception -------"+ex.getMessage());
			 
			 
		 }finally {
			 if(orderPlaceSuccess) {
//				 return new ResponseEntity<Object>(String.valueOf(orderNew.getOrderNum()), HttpStatus.OK);//Modified 18 nov 
			 }
		 }
		 
		///
		
	}
	
	private String generateOrderId() {
		long minValue = 10001;
		long maxValue = 99999;
		String orderId = String.valueOf((long)(Math.random() * (maxValue - minValue + 1) + minValue));
		orderId ="ORD".concat(orderId);
		return orderId;
	}
	
	private int generateOrderNum() {
		long minValue = 100;
		long maxValue = 999;
		int orderNumber = ((int)(Math.random() * (maxValue - minValue + 1) + minValue));
		return orderNumber;
	}
	
	private String generateOrderDetailId() {
		long minValue = 100001;
		long maxValue = 999999;
		String orderDetId = String.valueOf((long)(Math.random() * (maxValue - minValue + 1) + minValue));
		orderDetId ="ORDTL".concat(orderDetId);
		return orderDetId;
	}

}
