package com.example.demo.consumer;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Audit;
import com.example.demo.entity.AuditNew;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CartLineInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.model.ProductInfo;
import com.example.demo.repository.AuditRepository;

@Component
public class AuditConsumer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuditRepository auditRepository;
	
	
//	@RabbitListener(queues = RabbitConfig.AUDIT_QUEUE)
	public void consumeOrderFromQueue(CartInfo orderReceivedBeans) {
		System.out.println("====Message consumed from the queue==="+orderReceivedBeans);
		///
		 logger.info("[Audit consumerr]-----[ConsumeOrder from queue() ----calling]--------------");
		 
		 
		 //1. Prrepare the Audit entity
		 String userID = orderReceivedBeans.getCustomerInfo().getUserID();
		 
		 //Audit MS
	    	Audit auditBean = new Audit();
	    	auditBean.setOrderID(generateOrderId());
	    	auditBean.setUserID(userID);
	    	//TODO 
	    	auditBean.setProductName(orderReceivedBeans.getCartLines().get(0).getProductInfo().getName());
	    	auditBean.setQuantity(orderReceivedBeans.getQuantityTotal());
	    	auditBean.setOrderDate(new Date(System.currentTimeMillis()));
	    	auditBean.setStatus("PROCESSING");
		 try {
			 auditRepository.insertAudit(auditBean);
		 }catch(Exception ex) {
			 logger.error("[Audit consumerr]-----[ConsumeOrder from queue() --Exception!!!]-------"+ex.getMessage());
		 }
		      
		   logger.info("[Audit consumerr]-----[ConsumeOrder from queue() ----SUCCESS!!!]--------------");
		 
		
	}
	
	@RabbitListener(queues = RabbitConfig.AUDIT_QUEUE)
	public void consumeAuditsFromQueue(CartInfo orderReceivedBeans) {
		System.out.println("====Message consumed from the queue==="+orderReceivedBeans);
		///
		 logger.info("[Audit consumerr]-----[ConsumeOrder from queue() ----calling]--------------");
		 
		 
		 //1. Prrepare the Audit entity
		 String userID = orderReceivedBeans.getCustomerInfo().getUserID();
		 CustomerInfo custInfo = orderReceivedBeans.getCustomerInfo();
		 List<CartLineInfo> cartlines = orderReceivedBeans.getCartLines();
		 
		 //Audit MS
		 
		 String auditID = generateOrderId();
		 
	    	//TODO 
	    	for(CartLineInfo cartlineInfo : cartlines) {
	    		AuditNew auditBean = new AuditNew();
	    		auditBean.setAuditID(auditID);
		    	auditBean.setUserID(userID);
	    		ProductInfo prdInfo = cartlineInfo.getProductInfo();
	    		auditBean.setProductName(prdInfo.getName());
	    		auditBean.setProductCode(prdInfo.getCode());
	    		auditBean.setQuantity(orderReceivedBeans.getQuantityTotal());
	    		auditBean.setTotAmount(orderReceivedBeans.getAmountTotal());
	    		auditBean.setPrice(prdInfo.getPrice());
	    		auditBean.setName(custInfo.getName());
	    		auditBean.setEmail(custInfo.getEmail());
	    		auditBean.setPhone(custInfo.getPhone());
	    		auditBean.setAddress(custInfo.getAddress());
	    		auditBean.setOrderDate(new Date(System.currentTimeMillis()));
	    		auditBean.setStatus(("PROCESSING"));
	    		
	    		try {
	   			 auditRepository.addAuditNew(auditBean);
	   		 }catch(Exception ex) {
	   			 logger.error("[Audit consumerr]-----[ConsumeOrder from queue() --Exception!!!]-------"+ex.getMessage());
	   		 }
	    	}
	    	
	    	
//	    	auditBean.setOrderDate(new Date(System.currentTimeMillis()));
		 
		      
		   logger.info("[Audit consumerr]-----[ConsumeOrder from queue() ----SUCCESS!!!]--------------");
		 
		
	}
	
	private String generateOrderId() {
		long minValue = 10001;
		long maxValue = 99999;
		String orderId = String.valueOf((long)(Math.random() * (maxValue - minValue + 1) + minValue));
		orderId ="ADT".concat(orderId);
		return orderId;
	}
	
	

}
