package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartNew;
import com.example.demo.message.RabbitConfig;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.repository.CartJpaRepository;

@RestController
public class CartController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
//	@Autowired
//	private CartRepository cartRepository;
	
	@Autowired
	private CartJpaRepository cartJpaRepository;
	
	  @Autowired
	 private RabbitTemplate rabbitTemplate;
	
	
	//Added for New design starts
	
	//1. Added to new cart
	
	 // Add selected product in to cart
 	@PostMapping("/addToCartNew")
 	 public ResponseEntity<Object> addToCartNew(@RequestBody CartNew cartBean) {//throws Exception {
 		
 		logger.info("[Cart Controller ]-----[addToCart() ----calling]--------------");
 		
 		cartBean.setTotalAmout(cartBean.getPrice() * cartBean.getQuantity());
 		
 		try {
 			CartNew exist = cartJpaRepository.getCartByCodeUserID(cartBean.getProdCode(), cartBean.getUserID());
 			
// 			if(null != exist) {
// 				
 				cartJpaRepository.updateCartNew(cartBean);
// 			}else {
// 				cartJpaRepository.addCartNew(cartBean);
// 			}
 			
 		}catch(EmptyResultDataAccessException ere) {//Not existing records	
 			cartJpaRepository.addCartNew(cartBean);
 			
 		}catch(Exception ex) {
 			logger.error("[Cart Controller ]-----[addToCart() ----calling]--  Exception------------"+ex.getMessage());
 			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
 		}
      return ResponseEntity.status(HttpStatus.CREATED).build();
}
	
 	//3. get cart of the user
 		@GetMapping("/cartNew/{userID}")
 		  public List<CartNew> getAllNewCartProductsByUserID(@PathVariable String userID){
 			  
 			logger.info("[Cart Controller ]-----[getAllCartProductsByUserID() ----calling]--------------"+userID);
 			  List<CartNew> carts = new ArrayList<CartNew>();
 		    
 		    try {
 		    	carts = cartJpaRepository.findCartsByUserIDNew(userID);
 		    	System.out.println("===Carts==="+carts);
 		    }catch(Exception ex) {
 		    	
 		    	logger.info("[Cart Controller ]-----[getAllCartProductsByUserID() ----calling]--- Exception-----------"+ex.getMessage());
 		    }
 		    
 		    return carts;
 		  }
 		
 		
 		@GetMapping("/cartNew/{prodCode}/{userID}")
		  public CartNew getCarProductsNew(@PathVariable String prodCode,@PathVariable String userID){
			  
			  CartNew cart = null;
		    
		    try {
		    	cart = cartJpaRepository.getCartByCodeUserID(prodCode,userID);
		    }catch(Exception ex) {
		    	System.out.println("Cart Exception........");
		    	ex.printStackTrace();
		    	
		    }
		    
		    return cart;
		  }
 		
 		@GetMapping("/cartsNew")
 		  public List<CartNew> getAllCartProductsNew(){
 			  
 			  List<CartNew> carts = null;
 		    
 		    try {
 		    	carts = cartJpaRepository.findAllCartsNew();
 		    }catch(Exception ex) {
 		    	System.out.println("Cart Exception........");
 		    	ex.printStackTrace();
 		    	
 		    }
 		    
 		    return carts;
 		  }
 		
 	
 	
 	//2. Delete CartNew
 	
	  @DeleteMapping("/cart/{code}/{userID}")
	  public String deleteCartNew(@PathVariable String  code, @PathVariable String  userID) {
		  
		  CartNew existingCart = null;
		  String message = "";
		  
		  if(userID.isEmpty()) {
			  message = HttpStatus.BAD_REQUEST.toString();
			  return message;
		  }
		  
		  try {
			  existingCart = cartJpaRepository.getCartByCodeUserID(code, userID);
			   
			   if(null == existingCart) {
			    	message = "No record found to Delete"; 
			   } 
			  
		  }catch(EmptyResultDataAccessException emptEx) {
			  message = "No record found to Delete";
			  
		  }catch(DataAccessException dae) {
			  message = dae.getMessage();
		  }catch(Exception ex) {
			  message = ex.getMessage();
		  }
		  
		  try {
			  
			  if(null != existingCart) {
				  
					 cartJpaRepository.deleteCartByProdCodeUserId(code, userID);
					 message ="Success";
				  
			  }
		    }catch(Exception ex) {
		    	message =ex.getMessage();
		    	
		    }
		  
			return message;
		  
	  }

	
	//Added for new design end
	
	
	
	
   //1.1  Add selected product in to cart
 	@PostMapping("/addToCart")
 	 public ResponseEntity<Object> addToCart(@RequestBody Cart cartBean) throws Exception {
 		
 		logger.info("[Cart Controller ]-----[addToCart() ----calling]--------------");
 		
 		try {
 			cartJpaRepository.addCart(cartBean);
 		}catch(Exception ex) {
 			
 			logger.error("[Cart Controller ]-----[addToCart() ----calling]--  Exception------------"+ex.getMessage());
 			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
 		}
      return ResponseEntity.status(HttpStatus.CREATED).build();
}
	
	//2. Get the cart details 
	
	@GetMapping("/carts")
	  public List<Cart> getAllCartProducts(){
		  
		  List<Cart> carts = null;
	    
	    try {
	    	carts = cartJpaRepository.findAllCarts();
	    }catch(Exception ex) {
	    	
	    }
	    
	    return carts;
	  }
	
	//3. get cart of the user
	@GetMapping("/cart/{userID}")
	  public List<Cart> getAllCartProductsByUserID(@PathVariable String userID){
		  
		logger.info("[Cart Controller ]-----[getAllCartProductsByUserID() ----calling]--------------"+userID);
		  List<Cart> carts = null;
	    
	    try {
	    	carts = cartJpaRepository.findCartsByUserID(userID);
	    }catch(Exception ex) {
	    	
	    	logger.info("[Cart Controller ]-----[getAllCartProductsByUserID() ----calling]--- Exception-----------"+ex.getMessage());
	    }
	    
	    return carts;
	  }
	
	
	//3. Delete/Remove  products from cart 
	
	 //Delete by Currency
	  @DeleteMapping("/cart/{userID}")
	  public String deleteCart(@PathVariable String  userID) {
		  
		  List<Cart> existingCarts = null;
		  //String deleteByCountryCode = newConversionFactor.getCountry();
		  String message = "";
		  
		  if(userID.isEmpty()) {
			  message = HttpStatus.BAD_REQUEST.toString();
			  return message;
		  }
		  
		  try {
			  existingCarts = cartJpaRepository.findCartsByUserID(userID);
			   
			   if(null == existingCarts) {
			    	message = "No record found to Delete"; 
			   } 
			  
		  }catch(EmptyResultDataAccessException emptEx) {
			  message = "No record found to Delete";
			  
		  }catch(DataAccessException dae) {
			  message = dae.getMessage();
		  }catch(Exception ex) {
			  message = ex.getMessage();
		  }
		  
		  try {
			  
			  if(null != existingCarts) {
				  
				 for(Cart cartToBeDeleet : existingCarts) {
					 cartJpaRepository.deleteCartByUserID(userID);
					 message ="Success";
				 }
				  
			  }
		    }catch(Exception ex) {
		    	message =ex.getMessage();
		    	
		    }
		  
			return message;
		  
	  }
	  
	  
	  
	  //Rabbit Mq Publisher
	  
	  @PostMapping("/placeOrderNew/{userID}")
		 public String receiveOrderNew(@RequestBody CartInfo orderPlaceBean,
				 @PathVariable String userID,HttpServletRequest request)  {
		  
		  CustomerInfo customerInfo = orderPlaceBean.getCustomerInfo();
		  customerInfo.setUserID(userID); 
		  orderPlaceBean.setCustomerInfo(customerInfo);
		  
		  CartInfo orderMessagePushToQueue = orderPlaceBean;
		  
//	     LOG.info("**************prductCheckOut**************");
	     String msg = "Cart items sent for order check out "+ orderMessagePushToQueue;
	     sendAuditLog(orderMessagePushToQueue);
		 sendOrder(orderMessagePushToQueue); 
	     String returnMsg = customerInfo.getName()+"--Your Order Placed Successfuly !! ";
	     return returnMsg;
//	     return new ResponseEntity<Object>(returnMsg, HttpStatus.OK);
	                	
	    }
	  
	   private void sendOrder(CartInfo orderCart) {
//	        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_ORDERS, cart);
		  rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ORDER_ROUTING_KEY, orderCart);
	    	
	    }
	  
	  private void sendAuditLog(CartInfo auditCart) {
		  rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.AUDIT_ROUTING_KEY, auditCart);
	    	
	    }


}
