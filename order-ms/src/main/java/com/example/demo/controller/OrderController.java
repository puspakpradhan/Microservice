package com.example.demo.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.OrderReceiveBean;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.OrderDetailsNew;
import com.example.demo.entity.OrderNew;
import com.example.demo.entity.ProductNew;
import com.example.demo.entity.UserOrder;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CartLineInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.repository.OrderRepository;


@RestController
public class OrderController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailsRepository  orderDetailsRepository;
	
	@Autowired
	private RestTemplate template;
	
	
	@GetMapping("/order/{userID}")
	public List<Order> getOderById(@PathVariable String userID) {
	  List<Order> order = orderRepository.getOrderByID(userID);
    	return order;
	}
	
	@GetMapping("/userOrder/{userID}")
	public List<UserOrder> getOderByUserId(@PathVariable String userID) {
	  List<UserOrder> userOrders = orderRepository.getOrderByUserID(userID);
    	return userOrders;
	}

	
	//Place Order new design
	
	@GetMapping("/ordersNew")
	  public List<OrderNew> getAllCartProductsNew(){
		  
		  List<OrderNew> ordersNew = null;
	    
	    try {
	    	ordersNew = orderRepository.findAllOrdersNew();
	    }catch(Exception ex) {
	    	System.out.println("Cart Exception........");
	    	ex.printStackTrace();
	    	
	    }
	    
	    return ordersNew;
	  }
	
	
	
	@GetMapping("/ordersNew/{orderId}")
	  public OrderNew getOrderNewByID(@PathVariable String orderId){
		  OrderNew ordersNew = null;
	    try {
	    	ordersNew = orderRepository.getOrderNewByID(orderId);
	    }catch(Exception ex) {
	    	System.out.println("Order Exception........");
	    	ex.printStackTrace();
	    	
	    }
	    
	    return ordersNew;
	  }
	
	
	@GetMapping("/orderDetailsNew")
	  public List<OrderDetail> findAllOrdersDetails(){
		  
		  List<OrderDetail> ordersdetailsNew = null;
	    
	    try {
	    	ordersdetailsNew = orderRepository.findAllOrdersDetails();
	    }catch(Exception ex) {
	    	System.out.println("Cart Exception........");
	    	ex.printStackTrace();
	    	
	    }
	    
	    return ordersdetailsNew;
	  }
	
	//Without-fk
	@GetMapping("/orderDetailsNewFK")
	  public List<OrderDetailsNew> findAllOrdersDetailsFK(){
		  
		  List<OrderDetailsNew> ordersdetailsNewFK = null;
	    
	    try {
	    	ordersdetailsNewFK = orderRepository.findAllOrdersDetailsWithOutFK();
	    }catch(Exception ex) {
	    	System.out.println("Cart Exception........");
	    	ex.printStackTrace();
	    	
	    }
	    
	    return ordersdetailsNewFK;
	  }
	
	@GetMapping("/orderDetailsNewFK/{orderId}")
	  public List<OrderDetailsNew> findAllOrdersDetailsFKByOrderId(@PathVariable String orderId){
		  
		  List<OrderDetailsNew> ordersdetailsNewFK = null;
	    
	    try {
	    	ordersdetailsNewFK = orderRepository.findAllOrdersDetailsWithOutFKById(orderId);
	    }catch(Exception ex) {
	    	System.out.println("Cart Exception........");
	    	ex.printStackTrace();
	    	
	    }
	    
	    return ordersdetailsNewFK;
	  }
	
	
	@GetMapping("/product/{productCode}")
	  public ProductNew getProductNewByCode(@PathVariable String productCode){
		  
		 ProductNew productNew = null;
	    
	    try {
	    	productNew = orderRepository.getProductNewByCode(productCode);
	    }catch(Exception ex) {
	    	System.out.println("Cart Exception........");
	    	ex.printStackTrace();
	    	
	    }
	    
	    return productNew;
	  }

	
////////////////	
	
	
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
	
	
	//Place order...
	
	
	 @PostMapping("/placeOrderNew/{userID}")
	 public ResponseEntity<Object> receiveOrderNew(@RequestBody CartInfo orderReceivedBeans,
			 @PathVariable String userID,HttpServletRequest request)  {
		 
	 
	 logger.info("[Order Controller Place Order]-----[getOrderById() ----calling]--------------"+userID);
	 
	 HttpSession session = request.getSession();
	 String orderID ="";
	 int orderNumber = 0;
	 int totalQuantity = 0;
	 boolean orderPlaceSuccess  = false;
	 boolean orderDBUpdated = false;
	 OrderNew orderNew = new OrderNew();
	 
	 try {
		 
		 CartInfo cartTObplaced = orderReceivedBeans;
		 String uid = userID;
		 
		 System.out.println("UserID==="+uid);
		 
		 CustomerInfo custInfo = orderReceivedBeans.getCustomerInfo();
		 List<CartLineInfo> cartLines = orderReceivedBeans.getCartLines();
		 //Step1. Insert in DB
		     //Orders table
//		 OrderNew orderNew = new OrderNew();
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
			  
//			 OrderDetail orderDetail = new OrderDetail();//Modified to OrderDetailsNew
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
//					 orderDetailsRepository.save(orderDetail);
					int insert = orderRepository.insertOrderDetailsNewWithoutFK(orderDetail);
					 orderPlaceSuccess = true;
					 session.setAttribute("ORDER_NUMBER", String.valueOf(orderNew.getOrderNum()));
					 //return new ResponseEntity<Object>(String.valueOf(orderNew.getOrderNum()), HttpStatus.OK);//Modified 18 nov
					 
				 }catch(Exception anyException) {
					 orderPlaceSuccess = false;
					 session.removeAttribute("ORDER_NUMBER");
					 return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
				 }
				
			 }//if orderdDB opdated end
			
			 
		 }
		  
		//Step2. call Rabbit MQ starts
			logger.info("[Order Controller Place Order]-----[receiveOrder() ----Rabbit MQ calling]-- USERID and  ORDER ID====:::----"+userID+":::"+orderNumber);
			 String urlString = "http://localhost:9195/order/"+orderNumber+"/"+userID; ///Just replaced the order id by Number
//			 String urlString = "http://messaging-ms:9195/order/"+orderID+"/"+userID;
			// create request body
			 
			 JSONObject mqRequest = new JSONObject();
			 mqRequest.put("orderId", orderID);
			 mqRequest.put("name", custInfo.getName());
			 mqRequest.put("qty", totalQuantity);
			// mqRequest.put("price", orderReceivedBeans.get(0).getPrice());
			 mqRequest.put("totalAmount", orderReceivedBeans.getAmountTotal());
			 // set headers
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<String> entity = new HttpEntity<String>(mqRequest.toString(), headers);
	
			 // send request and parse result
			 ResponseEntity<String> loginResponse = new RestTemplate()
			   .exchange(urlString, HttpMethod.POST, entity, String.class);
			 
			 logger.info("[Order Controller Place Order]-----[receiveOrder() ----Rabbit MQ call end--]--  ORDER ID====::"+orderID);
			 
			 if (loginResponse.getStatusCode() == HttpStatus.OK) {
				String str = loginResponse.getBody();
				logger.info("[Order Controller Place Order]-----[receiveOrder() ]----[Rabbit MQ call end status]-----");
				return new ResponseEntity<Object>(String.valueOf(orderNew.getOrderNum()), HttpStatus.OK);//Modified 18 nov
			 } 
			 return new ResponseEntity<Object>(String.valueOf(orderNew.getOrderNum()), HttpStatus.OK);//Modified 18 nov
		 
	 }catch(Exception ex) {
		 logger.error("[Order Controller Place Order]-----[receiveOrder() ]----[Rabbit MQ call]------ Exception -------"+ex.getMessage());
//		 return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		 return new ResponseEntity<Object>(String.valueOf(orderNew.getOrderNum()), HttpStatus.SERVICE_UNAVAILABLE);//Modified 18 nov
		 
		 
	 }finally {
		 if(orderPlaceSuccess) {
			 return new ResponseEntity<Object>(String.valueOf(orderNew.getOrderNum()), HttpStatus.OK);//Modified 18 nov 
		 }
	 }
	 
	 
 }
	
	 @PostMapping("/placeOrder/{userID}")
		 public ResponseEntity<Object> receiveOrder(@RequestBody List<OrderReceiveBean> orderReceivedBeans,
				 @PathVariable String userID)  {
		 
		 logger.info("[Order Controller Place Order]-----[getOrderById() ----calling]--------------"+userID);
		 
		 try {
			 
			 
			 String orderID = orderReceivedBeans.get(0).getOrderID();
			 
			 logger.info("[Order Controller Place Order]-----[receiveOrder() ----calling]-- USERID====:::------------"+orderID);
			 //String prodID = orderReceivedBeans.get(0).getProductID();
			 
			 //Insert In Order Table. Working
			    
			    for(OrderReceiveBean orderReceivedBean : orderReceivedBeans) {
			    	Order pacedOrder = new Order(userID, orderID, orderReceivedBean.getProductID(), orderReceivedBean.getProductName(), orderReceivedBean.getProductDesc(),
							 orderReceivedBean.getPrice(),orderReceivedBean.getQuantity(), orderReceivedBean.getStatus());
			    	try {
			    		logger.info("[Order Controller Place Order]-----[receiveOrder() ----Insert in ORDER TABLE]--------------"+orderID);
			    		 orderRepository.insertOrder(pacedOrder);
			    	}catch(Exception ex) {
			    		logger.error("[Order Controller Place Order]-----[receiveOrder() ---[Exception while Insert in ORDER TABLE]----Insert in ORDER TABLE]------"+ex.getMessage());
			    		ex.printStackTrace();
			    	}
					
			    }
			 //Step2. call Rabbit MQ starts
			 
			logger.info("[Order Controller Place Order]-----[receiveOrder() ----Rabbit MQ calling]-- USERID and  ORDER ID====:::----"+userID+":::"+orderID);
			 String urlString = "http://localhost:9195/order/"+orderID+"/"+userID;
//			 String urlString = "http://messaging-ms:9195/order/"+orderID+"/"+userID;
			// create request body
			 
			 JSONObject request = new JSONObject();
			 request.put("orderId", orderID);
			 request.put("name", orderReceivedBeans.get(0).getProductName());
			 request.put("qty", orderReceivedBeans.get(0).getQuantity());
			 request.put("price", orderReceivedBeans.get(0).getPrice());
			 request.put("totalAmount", orderReceivedBeans.get(0).getPrice() * orderReceivedBeans.get(0).getQuantity());
			 // set headers
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

			 // send request and parse result
			 ResponseEntity<String> loginResponse = new RestTemplate()
			   .exchange(urlString, HttpMethod.POST, entity, String.class);
			 
			 logger.info("[Order Controller Place Order]-----[receiveOrder() ----Rabbit MQ call end--]--  ORDER ID====::"+orderID);
			 
			 if (loginResponse.getStatusCode() == HttpStatus.OK) {
				String str = loginResponse.getBody();
				logger.info("[Order Controller Place Order]-----[receiveOrder() ]----[Rabbit MQ call end status]-----");
				return ResponseEntity.status(HttpStatus.CREATED).build();
			 } 
			 
			 //Rabbit Mq Call end
			
			 return ResponseEntity.status(HttpStatus.CREATED).build();
		 }catch(Exception ex) {
			 logger.error("[Order Controller Place Order]-----[receiveOrder() ]----[Rabbit MQ call]------ Exception -------"+ex.getMessage());
			 return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		 }
		 
		 
	 }
	 
	 
	//3. Delete/Remove  products from cart 
		
		  @DeleteMapping("/order/{userID}")
		  public String deleteCart(@PathVariable String  userID) {
			  
			  List<Order> existingOrders = null;
			  //String deleteByCountryCode = newConversionFactor.getCountry();
			  String message = "";
			  
			  if(userID.isEmpty()) {
				  message = HttpStatus.BAD_REQUEST.toString();
				  return message;
			  }
			  
			  try {
				  existingOrders = orderRepository.getOrderByID(userID);
				   
				   if(null == existingOrders) {
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
				  
				  if(null != existingOrders) {
					  
					 for(Order order : existingOrders) {
						 orderRepository.deleteOrderByUserID(userID);
						 message ="Success";
					 }
					  
				  }
			    }catch(Exception ex) {
			    	message =ex.getMessage();
			    	
			    }
			  
				return message;
			  
		  }
	
	
	
}
