package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.AddCartBean;
import com.example.demo.dto.AddCartBeanNew;
import com.example.demo.dto.OrderPlaceBean;
import com.example.demo.dto.ProductCategoryConversionBean;
import com.example.demo.dto.ProductConversionBean;
import com.example.demo.entity.AuthRequest;
import com.example.demo.model.CartInfo;
import com.example.demo.model.OrderInfo;
import com.example.demo.model.ProductInfo;
import com.example.demo.model.UserOrderInfo;
import com.example.demo.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	 private RestTemplate template;
	
	@GetMapping("/homePage")
	public String welcom() {
		
		return "Welcome to case study";
	}
	
	
	//Added for New design starts
	
	@GetMapping(path = "/productsNew")
	public ResponseEntity<ProductInfo[]> getProductsNew() {
		
		
		logger.info("[account-login-ms Controller]-----[get Products()]--------------");
		
		//Add logic to interact with Prodcut MS
//		String url = "http://localhost:9193/productsNew";
	     String url = "http://product-ms/productsNew";
		
		ResponseEntity<ProductInfo[]> responseEntity = null; //Here just changed the productConversionBean to prodcuctCategoryConversionBean
		ProductInfo productCategoryConversionBeans[] = null;
		
        try {
			
			 responseEntity = template.getForEntity(url, ProductInfo[].class );
			// productCategoryConversionBeans = responseEntity.getBody();
			 
			// cartResponseBean = cartResponseEntity.getBody();
		    // List<ProductInfo> cartBeans = new ArrayList<>(Arrays.asList(productCategoryConversionBeans));

		//If Exception while accessing product-service
		}catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[Inside gerProducts()]--- RestClientException-----------"+rcEx.getMessage());
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[Inside gerProducts() ]------ Exception-----------" +ex.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
		
		
		return responseEntity ;
		
		
	}
	///////////////AAAA
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/addToCartNew")
	 public ResponseEntity<Object> addToCartNew(@RequestBody AddCartBeanNew cartBean,@RequestHeader(value="Authorization") String tocken ) throws Exception {
		
		logger.info("[account-login-ms Controller]-----[addToCart() calling]--------------");
		AddCartBeanNew cb = cartBean;		
		
//		String url = "http://localhost:9194/addToCartNew";
		String url = "http://cart-ms/addToCartNew";
		
		ResponseEntity<Object> responseEntity = null; 
		
       try {
			
		  responseEntity = template.postForEntity(url,cartBean, Object.class );

		//If Exception while accessing cart-service
       }catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[addToCart()]--- RestClientException-----------"+rcEx.getMessage());
			return new ResponseEntity<Object>("Service Not available at this time!! ", HttpStatus.GATEWAY_TIMEOUT);//Modified 18 nov
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[addToCart() ]------ Exception-----------" +ex.getMessage());
			return new ResponseEntity<Object>("Service Not available at this time!! ", HttpStatus.SERVICE_UNAVAILABLE);//Modified 18 nov
		}
	 
		return ResponseEntity.status(HttpStatus.CREATED).build();
}
	
	 @DeleteMapping("/cart/{code}/{userID}")
	  public String deleteCartNew(@PathVariable String  code, @PathVariable String  userID) {
		  
		  String message = "";
		  RestTemplate restTemplate = new RestTemplate();
		  
		  if(userID.isEmpty()) {
			  message = HttpStatus.BAD_REQUEST.toString();
			  return message;
		  }
		  try {
//			 String deletecartUrl = "http://localhost:9194/cart/"+code+"/"+userID;
			 String deletecartUrl = "http://cart-ms/cart/"+code+"/"+userID;
			 template.delete(deletecartUrl);
			 message = "success";
		  }catch(Exception ex) {
			  message ="fail";
		  }
		  
		  
			return message;
		  
	  }
	
	
	//Added for New design end
	
	
	
	@GetMapping(path = "/products")
	public ResponseEntity<ProductCategoryConversionBean[]> getProducts() {
		
		
		logger.info("[account-login-ms Controller]-----[get Products()]--------------");
		
		//Add logic to interact with Prodcut MS
		String url = "http://localhost:9193/products";
//	  String url = "http://product-ms:9193/products";
		
		ResponseEntity<ProductCategoryConversionBean[]> responseEntity = null; //Here just changed the productConversionBean to prodcuctCategoryConversionBean
		ProductCategoryConversionBean productCategoryConversionBeans[] = null;
		
        try {
			
			 responseEntity = new RestTemplate().getForEntity(url, ProductCategoryConversionBean[].class );
			 productCategoryConversionBeans = responseEntity.getBody();
			 
			// cartResponseBean = cartResponseEntity.getBody();
		     List<ProductCategoryConversionBean> cartBeans = new ArrayList<>(Arrays.asList(productCategoryConversionBeans));

		//If Exception while accessing product-service
		}catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[Inside gerProducts()]--- RestClientException-----------"+rcEx.getMessage());
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[Inside gerProducts() ]------ Exception-----------" +ex.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
		
		
		return responseEntity ;
		
		
	}
	
	@GetMapping("/product/{id}")
	public ProductConversionBean getProductById(@PathVariable String id) {
		
		logger.info("[account-login-ms Controller]-----[get Product By ID()]--------------");
		
		//Add logic to interact with Prodcut MS
		String url = "http://localhost:9193/"+"product/"+id;
//		String url = "http://product-ms:9193/"+"product/"+id;
        ResponseEntity<ProductConversionBean> responseEntity = null;
        ProductConversionBean productConversionBean = null;
		
        try {
			
			 responseEntity = new RestTemplate().getForEntity(url, ProductConversionBean.class );
			 productConversionBean = responseEntity.getBody();

        }catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[Inside gerProduct By ID()]--- RestClientException-----------"+rcEx.getMessage());
			return new ProductConversionBean();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[Inside gerProduct by ID() ]------ Exception-----------" +ex.getMessage());
			return new ProductConversionBean();
		}
		
		return productConversionBean;
	}
	
	
	//This is working fine.
	@PostMapping("/authenticate")
		 public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		
		logger.info("[account-login-ms Controller]-- authenticate() calling-----------");
		
		try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        
        String tocken = jwtUtil.generateToken(authRequest.getUserName()); 
        logger.info("[account-login-ms Controller]-- [Tocken Id after authenticate()]-----------"+tocken);
        return tocken;
    }
	
	
	//Added today .30/10
	
	
	
	@GetMapping("/authenticate/{userName}/{password}")
	 public String generateToken1(@PathVariable String userName,@PathVariable String password) throws Exception {
		
	try {
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
   } catch (Exception ex) {
       throw new Exception("inavalid username/password");
   }
   
   String tocken = jwtUtil.generateToken(userName); 
   logger.info("[account-login-ms Controller]-- [Tocken Id after authenticate()]-----------"+tocken);
   
   return tocken;
}
	
	
	@RequestMapping("/login/{userID}/{password}")
    public String generateTokenandHomepage(@PathVariable("userID") String userID,
    		@PathVariable("password") String password) throws Exception {
        
		String inputUesrID = userID;
		String inputPassword = password;
		
		try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(inputUesrID, inputPassword)
            );
        } catch (Exception ex) {
        	logger.error("[account-login-ms Controller]-- [Exception  while GenerateTocken and navigate to Home page]-----------");
            throw new Exception("inavalid username/password");
        }
        
        String tocken = jwtUtil.generateToken(inputUesrID);
//        session.setAttribute("USER_ID", authRequest.getUserName());
//        session.setAttribute("JWT_TOCKEN", tocken);
        return tocken;
    }
	
	
	//Cart Service
	
	@PostMapping("/addToCart")
	 public ResponseEntity<Object> addToCart(@RequestBody AddCartBean cartBean,@RequestHeader(value="Authorization") String tocken ) throws Exception {
		
		logger.info("[account-login-ms Controller]-----[addToCart() calling]--------------");
		AddCartBean cb = cartBean;		
		
		String tokn = tocken;
		tokn = tokn.substring(7);
		String url = "http://localhost:9194/addToCart";
//		String url = "http://cart-ms:9194/addToCart";
		
		ResponseEntity<Object> responseEntity = null; 
		
        try {
			
		  responseEntity = new RestTemplate().postForEntity(url,cartBean, Object.class );

		//If Exception while accessing cart-service
        }catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[addToCart()]--- RestClientException-----------"+rcEx.getMessage());
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[addToCart() ]------ Exception-----------" +ex.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	 
		return ResponseEntity.status(HttpStatus.CREATED).build();
}
	
	
	
	//Order Service
	
	@PostMapping("/placeOrder/{userID}")
	 public ResponseEntity<Object> placeOrder(@RequestBody List<OrderPlaceBean> orderPlaceBean,
			       @RequestHeader(value="Authorization") String tocken,@PathVariable String userID ) throws Exception {
		
		logger.info("[account-login-ms Controller]-----[placeOrder() calling]--------------"+userID);
		List<OrderPlaceBean> ob = orderPlaceBean;		
		
		String tokn = tocken;
		tokn = tokn.substring(7);
		
		String url = "http://localhost:9196/placeOrder/"+userID;
//		String url = "http://order-ms:9196/placeOrder/"+userID;
		
		ResponseEntity<Object> responseEntity = null; 
		
       try {
			
    	   logger.info("[account-login-ms Controller]-----[placeOrder() calling]----URL::::======="+url);
		  responseEntity = new RestTemplate().postForEntity(url,orderPlaceBean, Object.class );

		//If Exception while accessing product-service
       }catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[placeOrder()]--- RestClientException-----------"+rcEx.getMessage());
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[placeOrder()]--- Exception-----------"+ex.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	 
		return ResponseEntity.status(HttpStatus.OK).build();
}

	
	
	//Place order nw design
	
	@PostMapping("/placeOrderNew/{userID}")
	 public ResponseEntity<String> placeOrderNew(@RequestBody CartInfo orderPlaceBean,
			       @RequestHeader(value="Authorization") String tocken,@PathVariable String userID ) throws Exception {
		
		logger.info("[account-login-ms Controller]-----[placeOrder() calling]--------------"+userID);
		String orderNumber = "";
		
		CartInfo cartinfo = orderPlaceBean;		
		
		String tokn = tocken;
		tokn = tokn.substring(7);
		
		  //Convert to json string
	      //Creating the ObjectMapper object
	        ObjectMapper mapper = new ObjectMapper();
	        //Converting the Object to JSONString
	        String orderListJson ="";
			try {
				orderListJson = mapper.writeValueAsString(orderPlaceBean);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	        System.out.println("From Cart to Place order json Inside Login controller==="+orderListJson);
		
		
		
		
//		String url = "http://localhost:9196/placeOrderNew/"+userID;
//		String url = "http://order-ms/placeOrderNew/"+userID;//Original
		//TODO direct to cart
		String url = "http://cart-ms/placeOrderNew/"+userID;
		
		ResponseEntity<String> responseEntity = null; 
		String responseString = "";
		
      try {
			
   	     logger.info("[account-login-ms Controller]-----[placeOrder() calling]----URL::::======="+url);
		 responseEntity = template.postForEntity(url,orderPlaceBean, String.class );
		 responseString = responseEntity.getBody().toString();//Added 18 Nov
		 System.out.println("AAABBB==="+responseEntity.getBody());
		 System.out.println("String===="+responseString);

		//If Exception while accessing product-service
      }catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[placeOrder()]--- RestClientException-----------"+rcEx.getMessage());
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[placeOrder()]--- Exception-----------"+ex.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
	 
//		return ResponseEntity.status(HttpStatus.OK).build();
		return new ResponseEntity<String>(responseString, HttpStatus.OK);//Modified 18 nov
}
	
	
	
	//Order List New..Added on 21 Nov
	
	@GetMapping(path = "/order/orderList/{userID}")
	public ResponseEntity<UserOrderInfo[]> getUserOrderList(@PathVariable String userID) {
		
		
		logger.info("[account-login-ms Controller]-----[get Products()]--------------");
		
		//Add logic to interact with Prodcut MS
//		String url = "http://localhost:9196/userOrder/"+userID;
		String url = "http://order-ms/userOrder/"+userID;
		
		ResponseEntity<UserOrderInfo[]> responseEntity = null; //Here just changed the productConversionBean to prodcuctCategoryConversionBean
		UserOrderInfo uesrOrders[] = null;
		
        try {
			
			 responseEntity = template.getForEntity(url, UserOrderInfo[].class );
			 uesrOrders = responseEntity.getBody();
			 
			// cartResponseBean = cartResponseEntity.getBody();
		     List<UserOrderInfo> userOrderInfoList = new ArrayList<>(Arrays.asList(uesrOrders));

		//If Exception while accessing order-service
		     
		     
		}catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[Inside gerProducts()]--- RestClientException-----------"+rcEx.getMessage());
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[Inside gerProducts() ]------ Exception-----------" +ex.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		}
		
		
		return responseEntity ;
		
		
	}
	
	//Order Details New 21 Nov
	
	@GetMapping("/order/ordrDetail/{orderID}")
	public OrderInfo getOrderDetailById(@PathVariable String orderID) {
		
		logger.info("[account-login-ms Controller]-----[get Product By ID()]--------------");
		
		//Add logic to interact with Prodcut MS
//		String url = "http://localhost:9196/"+"ordersNew/"+orderID;
		String url = "http://order-ms/"+"ordersNew/"+orderID;
        ResponseEntity<OrderInfo> responseEntity = null;
        OrderInfo orderInfo = null;
		
        try {
			
			 responseEntity = template.getForEntity(url, OrderInfo.class );
			 orderInfo = responseEntity.getBody();

        }catch(RestClientException rcEx) {
			logger.error("[account-login-ms Controller]----[Inside gerProduct By ID()]--- RestClientException-----------"+rcEx.getMessage());
			return new OrderInfo();
		}catch(Exception ex) {
			logger.error("[account-login-ms Controller]----[Inside gerProduct by ID() ]------ Exception-----------" +ex.getMessage());
			return new OrderInfo();
		}
		
		return orderInfo;
	}
	
	
}
	
	
