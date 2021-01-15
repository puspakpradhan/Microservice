package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.CartNewBean;
import com.example.demo.form.CustomerForm;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CartLineInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.model.OrderDetailInfo;
import com.example.demo.model.OrderDetailsNewInfo;
import com.example.demo.model.OrderInfo;
import com.example.demo.model.ProductInfo;
import com.example.demo.model.UserOrderInfo;
import com.example.demo.pagination.PaginationResult;
import com.example.demo.service.OrderServiceFeignClient;
import com.example.demo.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Controller
public class MainController {
	
	
	@Autowired
	 private RestTemplate template;
	
	@Autowired
	private OrderServiceFeignClient orderFeignClient;	
	
//	@Autowired
//	private AccoutLoginServiceFeignClient accountLoginFeignClient;
	
	
	 @RequestMapping("/home")
	   public String login() {
	      return "login";
	   }
	 
	 @RequestMapping("/admin/logout")
	   public String logout() {
	      return "redirect:/home";
	   } 
	 
	// Auth Fallback method
//		private String authenticationFallback(String userID,String password,Model model,
//				HttpServletRequest request) {
//			String serviceUnAvailable ="Service Not available at this Time Hystrix... !!";
//			model.addAttribute("error", serviceUnAvailable);
//			//return "login";
//			return "errorPage";
//		}
	 
	  //Load Home page. After successful login
		@RequestMapping("/login")
//		@HystrixCommand(fallbackMethod = "authenticationFallback")
	  	public String home (@RequestParam("userID") String userID, 
	  			@RequestParam("password") String password, Model model,  HttpServletRequest request) {
			
			
			HttpSession session = request.getSession();
			String jwtTocken = "";
//			String url = "http://localhost:9192/"+"authenticate/"+userID+"/"+password;
			String url = "http://account-Login-ms/authenticate/"+userID+"/"+password;
			//RestTemplate rt = new RestTemplate();
	        ResponseEntity<String> responseEntity = null;
			
	        try {
				
			 responseEntity = template.getForEntity(url, String.class );
			 jwtTocken = responseEntity.getBody();

			
	        }catch(Exception rcEx) {
	        	rcEx.printStackTrace();
				model.addAttribute("error", "Invalid User ID/password");
				return "login";
			}
	        
	        model.addAttribute("UserName",userID );
	        model.addAttribute("tocken", jwtTocken);
	        session.setAttribute("USER_TOCKEN", jwtTocken);
	        session.setAttribute("USER_ID", userID);
	        
	  		return "index";
	  	}
	
	
		
		
		private String processProductFallback(HttpServletRequest request, Model model) {
			model.addAttribute("errorMessage", "Service Not available at this Time Hystrix..prod. !!");
			return "productList";
		}
		
	  //Load Product page
		@RequestMapping("/productList")
		@HystrixCommand(fallbackMethod = "processProductFallback",commandProperties = {
			      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "300") })
	  	public String getProducts(HttpServletRequest request, Model model)  {
			
			HttpSession session = request.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			
			try {
				 getProductDetails(jwtTocken, model);
			}catch(Exception ex) {
			   throw ex;
			}
			
	  	 return "productList";
	  	}
		
	//Add to Cart	
		@RequestMapping({ "/buyProduct" })
		   public String listProductHandler(HttpServletRequest request, Model model, //
		         @RequestParam(value = "code", defaultValue = "") String code) {
			
			HttpSession session = request.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			String userID = (String)session.getAttribute("USER_ID");
		 
		      ProductInfo product = null;
		      if (code != null && code.length() > 0) {
		    	  //TODO Call prod MS.Getprod by code
//		         ProductInfo product = null;
//		  		String url = "http://localhost:9193/"+"productCode/"+code;
		  		String url = "http://product-ms/"+"productCode/"+code;
		  		RestTemplate rt = new RestTemplate();
		          ResponseEntity<ProductInfo> responseEntity = null;
		  		
		          try {
		  			
		  		 responseEntity = template.getForEntity(url, ProductInfo.class );
		  		 product = responseEntity.getBody();
		  		 
		  		 if(null ==  product ||( product.getCode() == null && product.getName() == null ) ) {
		  			 model.addAttribute("error", "This Product is currently Unavailable !!");
		  			 return "productList";
		  		 }

		  		}catch(Exception rcEx) {
		  			rcEx.printStackTrace();
		  		}

		    	  
		      }
		      if (product != null) {
		    	  
		    	  CartInfo cartInfo = Utils.getCartInSession(request);
		    	  
//		          ProductInfo productInfo = new ProductInfo(product);
		  
		          cartInfo.addProduct(product, 1);
		    	  
		 
		      }
		 
		      return "redirect:/shoppingCart";
		   }
		
		
		
		// Cart Fallback method
		private String processCartFallback(HttpServletRequest request, Model model) {
			model.addAttribute("errorMessage", "Service Not available at this Time Hystrix..Cart... !!");
			return "shoppingCart";
		}
		
		
		// GET: Show cart.
		   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
		   @HystrixCommand(fallbackMethod = "processCartFallback")
		   public String shoppingCartHandler(HttpServletRequest request, Model model) {
		      CartInfo myCart = Utils.getCartInSession(request);
		      
		      
		        HttpSession session = request.getSession();
				String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
				String userID = (String)session.getAttribute("USER_ID");
		      
		      List<CartLineInfo> cartLines = myCart.getCartLines();
		      for(CartLineInfo cartLine : cartLines) {
		    	  CartNewBean cartToAdd = new CartNewBean();
		    	  ProductInfo  prodInfo = cartLine.getProductInfo();
		    	  int quantity = cartLine.getQuantity();
		    	  
		    	  cartToAdd.setProdCode(prodInfo.getCode());
		    	  cartToAdd.setUserID(userID);
		    	  cartToAdd.setProdName(prodInfo.getName());
		    	  cartToAdd.setPrice(prodInfo.getPrice());
		    	  cartToAdd.setQuantity(quantity);
		    	  cartToAdd.setTotalAmount(quantity * prodInfo.getPrice());
		    	  
		    	  //Convert to json
		    	  
		    	//Creating the ObjectMapper object
		          ObjectMapper mapper = new ObjectMapper();
		          //Converting the Object to JSONString
		          String productTobeAddInCartJson ="";
		  		try {
		  			productTobeAddInCartJson = mapper.writeValueAsString(cartToAdd);
		  		} catch (JsonProcessingException e) {
		  			e.printStackTrace();
		  		}
		          System.out.println("====Cart to json New========="+productTobeAddInCartJson);
		          
						ResponseEntity<Object[]> responseEntity = null;
//						ProductInfo productInfo[] = null;
						String url = "http://account-Login-ms/addToCartNew";
						HttpHeaders headers = getHeaders();
						headers.set("Authorization", "Bearer "+jwtTocken);
//						HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
						HttpEntity<Object> requestEntity =  new HttpEntity<>(cartToAdd, headers);
						// Use Token to get Response
						try {
						 responseEntity = template.exchange(url, HttpMethod.POST, requestEntity,Object[].class);
						
						}catch(Exception ex) {
							//model.addAttribute("error", " Service not available at this time !!!");
							//return "errorPage";
							throw ex;
						}
					
		      }//end of for loop
		      
		      
		 
		      model.addAttribute("cartForm", myCart);
		      return "shoppingCart";
		   }	

		
	// POST: Update quantity for product in cart
	   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	   public String shoppingCartUpdateQty(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("cartForm") CartInfo cartForm) {
		   
		   HttpSession session = request.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			String userID = (String)session.getAttribute("USER_ID");
		   
	      CartInfo cartInfo = Utils.getCartInSession(request);
	      cartInfo.updateQuantity(cartForm);
	      
	      
	      //Update Cart/Delete based on quantity
	      RestTemplate rt = new RestTemplate();
	      List<CartLineInfo> cartLines = cartForm.getCartLines();
	      
        for(CartLineInfo cartLineInfo : cartLines) {
        	ProductInfo product = cartLineInfo.getProductInfo();
        	int updatedQuantity = cartLineInfo.getQuantity();
        	if(updatedQuantity == 0) {
        		
        		//Below 2 lines working fine
//        	 String deleteUrl = "http://localhost:9194/cart/"+product.getCode()+"/"+userID;
//        	 rt.delete(deleteUrl);
        	 
        	 //Added delete using tocken starts
        	 
        	 // 3.1 validate tocken
        		
        		try {
					
					ResponseEntity<Object[]> responseEntity = null;
					String deleteUrlusingTocken = "http://account-Login-ms/cart/"+product.getCode()+"/"+userID;
					HttpHeaders headers = getHeaders();
					headers.set("Authorization", "Bearer "+jwtTocken);
					HttpEntity<Object> requestEntity =  new HttpEntity<>(headers);
					// Use Token to get Response
					 responseEntity = template.exchange(deleteUrlusingTocken, HttpMethod.DELETE, requestEntity,Object[].class);
					System.out.println("Response======"+responseEntity);
					
				}catch(Exception ex) {
					model.addAttribute("error", " Service not available at this time !!!");
				}
        		
   	         
        	}
        }	      
	 
	      return "redirect:/shoppingCart";
	   }		
		
	   
	  //Remove product on click of delete from cart page..
	   
	   @RequestMapping({ "/shoppingCartRemoveProduct" })
	   public String removeProductHandler(HttpServletRequest request, Model model, //
	         @RequestParam(value = "code", defaultValue = "") String code) {
	      //Product product = null;
		   HttpSession session = request.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			String userID = (String)session.getAttribute("USER_ID");
	      ProductInfo product = null;
	      RestTemplate rt = new RestTemplate();
	      if (code != null && code.length() > 0) {
//	    	    String url = "http://localhost:9193/"+"productCode/"+code;
		  		String url = "http://product-ms/"+"productCode/"+code;
//		  		RestTemplate rt = new RestTemplate();
		          ResponseEntity<ProductInfo> responseEntity = null;
		  		
		          try {
		  			
		  		 responseEntity = template.getForEntity(url, ProductInfo.class );
		  		 product = responseEntity.getBody();
		  		 
		  		 if(null ==  product ||( product.getCode() == null && product.getName() == null ) ) {
		  			 model.addAttribute("error", "This Product is currently Unavailable !!");
//		  			 getProductDetails(jwtTocken, model);
		  			 return "productList";
		  		 }

		  		}catch(Exception rcEx) {
		  			rcEx.printStackTrace();
		  		}

	    	  
	    	  
	    	  
	      }
	      if (product != null) {
	    	  
	         CartInfo cartInfo = Utils.getCartInSession(request);
	         cartInfo.removeProduct(product);
	         
     		try {
					
					ResponseEntity<Object[]> responseEntity = null;
					String deleteUrlusingTocken = "http://account-Login-ms/cart/"+product.getCode()+"/"+userID;
					HttpHeaders headers = getHeaders();
					headers.set("Authorization", "Bearer "+jwtTocken);
					HttpEntity<Object> requestEntity =  new HttpEntity<>(headers);
					// Use Token to get Response
					 responseEntity = template.exchange(deleteUrlusingTocken, HttpMethod.DELETE, requestEntity,Object[].class);
					System.out.println("Response======"+responseEntity);
					
				}catch(Exception ex) {
					model.addAttribute("error", " Service not available at this time !!!");
				}
	         
	         
	         
	         
	 
	      }
	 
	      return "redirect:/shoppingCart";
	   }
	   
	   
	   
	   // GET: Enter customer information.
	   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
	   public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
	 
	      CartInfo cartInfo = Utils.getCartInSession(request);
	 
	      if (cartInfo.isEmpty()) {
	         return "redirect:/shoppingCart";
	      }
	      CustomerInfo customerInfo = cartInfo.getCustomerInfo();
	      CustomerForm customerForm = new CustomerForm(customerInfo);
	      model.addAttribute("customerForm", customerForm);
	      return "shoppingCartCustomer";
	   }
	   
	   
	// POST: Save customer information.
	   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
	   public String shoppingCartCustomerSave(HttpServletRequest request, //
	         Model model, //
	         @ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
	         BindingResult result, //
	         final RedirectAttributes redirectAttributes) {
	 
	      if (result.hasErrors()) {
	         customerForm.setValid(false);
	         // Forward to reenter customer info.
	         return "shoppingCartCustomer";
	      }
	 
	      customerForm.setValid(true);
	      CartInfo cartInfo = Utils.getCartInSession(request);
	      CustomerInfo customerInfo = new CustomerInfo(customerForm);
	      cartInfo.setCustomerInfo(customerInfo);
	 
	      return "redirect:/shoppingCartConfirmation";
	   }
	   
	   // GET: Show information to confirm.
	   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
	   public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
	      CartInfo cartInfo = Utils.getCartInSession(request);
	 
	      if (cartInfo == null || cartInfo.isEmpty()) {
	 
	         return "redirect:/shoppingCart";
	      } else if (!cartInfo.isValidCustomer()) {
	 
	         return "redirect:/shoppingCartCustomer";
	      }
	      model.addAttribute("myCart", cartInfo);
	 
	      return "shoppingCartConfirmation";
	   }

	   
	   
	   
	// Order fallback method
//		private String processOrderFallback(HttpServletRequest request, Model model) {
//			String serviceUnAvailable ="Order Service Not available at this Time Hystrix... !!";
//			model.addAttribute("error", serviceUnAvailable);
//			return "errorPage";
//		}
	   
	   // POST: Submit Cart (Save)//Place Order...
	   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
//	   @HystrixCommand(fallbackMethod = "processOrderFallback")
	   public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
	      CartInfo cartInfo = Utils.getCartInSession(request);
	      
	      //CartInfo myCart = Utils.getCartInSession(request);
	      
	        HttpSession session = request.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			String userID = (String)session.getAttribute("USER_ID");
			String orderNumber = "";
	 
	      if (cartInfo.isEmpty()) {
	 
	         return "redirect:/shoppingCart";
	      } else if (!cartInfo.isValidCustomer()) {
	 
	         return "redirect:/shoppingCartCustomer";
	      }
	      try {
	    	  
	    	  //TODO Store in DB
//	         orderDAO.saveOrder(cartInfo);
	    	  
	    	  //1. place order...
	    	  
	    	//Convert to json string
		      //Creating the ObjectMapper object
		        ObjectMapper mapper = new ObjectMapper();
		        //Converting the Object to JSONString
		        String orderListJson ="";
				try {
					orderListJson = mapper.writeValueAsString(cartInfo);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		        System.out.println("From Cart to Place order json Inside Main conroller==="+orderListJson);
		        
		        // 3.1 validate tocken and Place the Order.
		        
		        
		        try {
					
					ResponseEntity<String> responseEntity = null;
//					ProductInfo productInfo[] = null;
					String url = "http://account-Login-ms/placeOrderNew/"+userID;
					HttpHeaders headers = getHeaders();
					headers.set("Authorization", "Bearer "+jwtTocken);
//					HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
					HttpEntity<Object> requestEntity =  new HttpEntity<>(cartInfo, headers);
					// Use Token to get Response
					 responseEntity = template.exchange(url, HttpMethod.POST, requestEntity,String.class);
					System.out.println("=======AAAAA  Response======"+responseEntity.getBody());
//					 list = new ArrayList<>(Arrays.asList(productInfo));
					orderNumber = responseEntity.getBody();
					session.setAttribute("ORDER_NUMBER",orderNumber);
					
				}catch(Exception ex) {
					model.addAttribute("error", " Service not available at this time !!!");
					session.removeAttribute("ORDER_NUMBER");
					return "errorPage";
				}
		        
	    	  
	    	  
	    	  
	      } catch (Exception e) {
	 
	         return "shoppingCartConfirmation";
	      }
	 
	      // Remove Cart from Session.
	      Utils.removeCartInSession(request);
	      
	      
	      
	      try {
	    	  List<CartLineInfo> cartLines = cartInfo.getCartLines();
	 	     
	 	     for(CartLineInfo cartLineInfo : cartLines) {
	 	    	ProductInfo product = cartLineInfo.getProductInfo();
	 	    	ResponseEntity<Object[]> responseEntity = null;
				String deleteUrlusingTocken = "http://account-Login-ms/cart/"+product.getCode()+"/"+userID;
				HttpHeaders headers = getHeaders();
				headers.set("Authorization", "Bearer "+jwtTocken);
				HttpEntity<Object> requestEntity =  new HttpEntity<>(headers);
				// Use Token to get Response
				 template.exchange(deleteUrlusingTocken, HttpMethod.DELETE, requestEntity,Object[].class);
	 	     }
				
			}catch(Exception ex) {
				model.addAttribute("error", " Service not available at this time !!!");
			}
	      
	      
	      // Store last cart.
	      Utils.storeLastOrderedCartInSession(request, cartInfo);
	 
	      return "redirect:/shoppingCartFinalize";
	   }
	 
	   @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
	   public String shoppingCartFinalize(HttpServletRequest request, Model model) {
		   
		   HttpSession session = request.getSession();
		   String orderNum = (String)session.getAttribute("ORDER_NUMBER");
//		   session.setAttribute("ORDER_NUMBER", String.valueOf(orderNew.getOrderNum()));
	 
	      CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
	     
//	      if(null != orderNum && !orderNum.isEmpty()) {
//	    	  lastOrderedCart.setOrderNum(Integer.valueOf(orderNum));
//	      }else {
//	    	  lastOrderedCart.setOrderNum(0);
//	      }
//	     
	 
	      if (lastOrderedCart == null) {
	         return "redirect:/shoppingCart";
	      }
//	      model.addAttribute("lastOrderedCart", lastOrderedCart);
	      model.addAttribute("message", orderNum);
	      return "shoppingCartFinalize";
	      
	   }
	   
	   
	 
//	   @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
//	   public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
//	         @RequestParam("code") String code) throws IOException {
		   
//	      ProductNew product = null;
//	      if (code != null) {
//	    	  
//	    	  //TODO call prd service to get images..... .
////	         product = findProduct(code);
//	    	  
//	    	  String url = "http://localhost:9193/"+"productCode/"+code;
//		  		RestTemplate rt = new RestTemplate();
//		          ResponseEntity<ProductNew> responseEntity = null;
//		  		
//		          try {
//		  			
//		  		 responseEntity = rt.getForEntity(url, ProductNew.class );
//		  		 product = responseEntity.getBody();
//	      }catch(Exception ex){
//	    	  }
//	      }
//	      if (product != null && product.getImage() != null) {
//	         response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//	         response.getOutputStream().write(product.getImage());
//	      }
//	      response.getOutputStream().close();
//	   }  
	   
	   
	   
  private String orderFallback(Model model,HttpServletRequest request) {
	String serviceUnAvailable ="Service Not available at this Time.  Hystrix..order. !!";
	model.addAttribute("errorMessage", serviceUnAvailable);
	return "orderList";
}

	   @RequestMapping(value = { "/order/orderList" }, method = RequestMethod.GET)
       @HystrixCommand(fallbackMethod = "orderFallback")
	   public String orderList(Model model, HttpServletRequest request1) {
		   
//		   public String orderList(Model model, //
//			         @RequestParam(value = "page", defaultValue = "1") String pageStr,HttpServletRequest request1) {
//	      
		   
		    HttpSession session = request1.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			String userID = (String)session.getAttribute("USER_ID");
		   
	      PaginationResult<OrderInfo> paginationResult = new PaginationResult<OrderInfo>();
		  List<OrderInfo> list = new ArrayList<OrderInfo>();
		  String  userOrdersString = "";
			 
//		  OkHttpClient client = new OkHttpClient().newBuilder().build();
//		  OkHttpClient.Builder builder = new OkHttpClient.Builder();
//		  builder.connectTimeout(60, TimeUnit.SECONDS); 
//		  builder.readTimeout(60, TimeUnit.SECONDS); 
//		  builder.writeTimeout(30, TimeUnit.SECONDS); 
//		  client = builder.build();		      
		  
//			Request request = new Request.Builder()
//			  .url("http://localhost:9192/order/orderList/"+userID)
//			  .method("GET", null)
//			  .addHeader("Content-Type", "application/json")
//			  .addHeader("Authorization", "Bearer"+" "+jwtTocken)
//			  .build();
			
			
			List<UserOrderInfo> userOrderList = new ArrayList<>();
			try {
				
				
				
				//Original
				//Response response = client.newCall(request).execute();
				//ResponseBody responseBody = response.body();
				//userOrdersString = responseBody.string();
				//ObjectMapper mapper = new ObjectMapper();
				//userOrderList = Arrays.asList(mapper.readValue(userOrdersString, UserOrderInfo[].class));
				
	////////////////
				ResponseEntity<UserOrderInfo[]> orderResponseEntity = null;
				UserOrderInfo orderInfoAray[] = null;
				String url = "http://account-Login-ms/order/orderList/"+userID;
				HttpHeaders headers = getHeaders();
				headers.set("Authorization", "Bearer "+jwtTocken);
				HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
				// Use Token to get Response
			try {
				orderResponseEntity = template.exchange(url, HttpMethod.GET, jwtEntity,UserOrderInfo[].class);
			}catch(Exception ex) {
				throw ex; 
			}
				
				 orderInfoAray = orderResponseEntity.getBody();
				 userOrderList = new ArrayList<>(Arrays.asList(orderInfoAray));
				
				//////////////
				
				
				
				for(UserOrderInfo userOrderInfo : userOrderList){
					String  orderUrl = "http://order-ms/ordersNew/"+userOrderInfo.getOrderID(); //Fetch all the Orders by Ord1 for that user
		        	 ResponseEntity<OrderInfo> responseEntity = null;
		        	 OrderInfo orderInfo = null;
		        	 
		        	 //Below 3 are woking fine with Rest Template
	     			 //responseEntity = new RestTemplate().getForEntity(orderUrl, OrderInfo.class );
		        	 // orderInfo = responseEntity.getBody();
		        	 //list.add(orderInfo);
		        	 
		        	//This is with feign client
		        	 orderInfo = orderFeignClient.getOrderNewByID(userOrderInfo.getOrderID());
	     			 list.add(orderInfo);
		        	 
		        	 
					
				}
				
				paginationResult.setList(list);
				
			} catch (Exception ex) {
				model.addAttribute("errorMessage", " Service not available at this time !!!");
				throw ex;
			} 
		      
	 
	      model.addAttribute("paginationResult", paginationResult);
	      return "orderList";
	   }
	   
	   
	   
	   
	   //TODO remove later
	  //Working fine... 
	   @RequestMapping(value = { "/admin1/orderList" }, method = RequestMethod.GET)
	   public String orderList1(Model model, //
	         @RequestParam(value = "page", defaultValue = "1") String pageStr,HttpServletRequest request1) {
	      
		   
		    HttpSession session = request1.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			String userID = (String)session.getAttribute("USER_ID");
		   
		   int page = 1;
	      try {
	         page = Integer.parseInt(pageStr);
	      } catch (Exception e) {
	      }
	      final int MAX_RESULT = 5;
	      final int MAX_NAVIGATION_PAGE = 10;
	      
	      
	      
	      PaginationResult<OrderInfo> paginationResult = new PaginationResult<OrderInfo>();
		  List<OrderInfo> list = new ArrayList<OrderInfo>();
			 
			 
			 RestTemplate rt = new RestTemplate();
			//1. 
			 String userOrderUrl = "http://localhost:9196/userOrder/"+userID; // Fetch all the orders of User
			 
//			 OrderBean orderResponseBean[] = null;   
			 UserOrderInfo userOrderResponseBean[] = null;
				//String orderUrl = "http://localhost:9196/"+"order/"+userID;
		     ResponseEntity<UserOrderInfo[]> orderResponseEntity = null;
		      try {
		        	orderResponseEntity = rt.getForEntity(userOrderUrl, UserOrderInfo[].class );
		        	userOrderResponseBean = orderResponseEntity.getBody();
			        List<UserOrderInfo> userOrderInfoList = new ArrayList<>(Arrays.asList(userOrderResponseBean));
			        
			        for(UserOrderInfo userOrderInfo : userOrderInfoList) {
			        	String  orderUrl = "http://localhost:9196/ordersNew/"+userOrderInfo.getOrderID(); //Fetch all the Orders by Ord1 for that user
			        	
			        	 ResponseEntity<OrderInfo> responseEntity = null;
			        	 OrderInfo orderInfo = null;
			     			
		     			 responseEntity = new RestTemplate().getForEntity(orderUrl, OrderInfo.class );
		     			 orderInfo = responseEntity.getBody();
		     			 list.add(orderInfo);
			        	
			        }
			        paginationResult.setList(list);
			    
		         }catch(Exception ex) {
			    	 
			     }
	 
	      model.addAttribute("paginationResult", paginationResult);
	      return "orderList";
	   }
	
	   
	   
	   
	   @RequestMapping(value = { "/order/orderDetail" }, method = RequestMethod.GET)
	   public String orderView(Model model, @RequestParam("orderId") String orderId,HttpServletRequest request) 
	   
	   {
		   
		   
		    HttpSession session = request.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			String userID = (String)session.getAttribute("USER_ID");
	      
			
		  OrderInfo orderInfo = null;
		  
		  //Fetch from orders
	      if (orderId != null) {
	          String  orderUrl = "http://order-ms/ordersNew/"+orderId; //Fetch all the Orders by Ord1 for that user
     	      
	          
	         //Below 3 lines are working fine. Using Rest Template
	         // ResponseEntity<OrderInfo> responseEntity = null;
		     // responseEntity = new RestTemplate().getForEntity(orderUrl, OrderInfo.class );
		     // orderInfo = responseEntity.getBody();
		      
		      //Using feign client..
		      orderInfo = orderFeignClient.getOrderNewByID(orderId);
		      
	      }
	      
	      if (orderInfo == null) {
		         return "redirect:/admin/orderList";
		      }
	      
	      //Fetch from  order Details for the order
	      
	      //Below 6 line are working fine with Resttemplate...
//	         String userOrderUrl = "http://order-ms/orderDetailsNewFK/"+orderId; // Fetch all the orders of User
//	         OrderDetailsNewInfo userOrderResponseBean[] = null;
//		     ResponseEntity<OrderDetailsNewInfo[]> orderResponseEntity = null;
//		     orderResponseEntity = new RestTemplate().getForEntity(userOrderUrl, OrderDetailsNewInfo[].class );
//		     userOrderResponseBean = orderResponseEntity.getBody();
//			 List<OrderDetailsNewInfo> detailsNew = new ArrayList<>(Arrays.asList(userOrderResponseBean));
			 
			//Using Feign client 
			 List<OrderDetailsNewInfo> detailsNew = orderFeignClient.findAllOrdersDetailsFKByOrderId(orderId);
			
			 
			 
			 
			 
			 List<OrderDetailInfo> detailsTodisplay = new ArrayList<OrderDetailInfo>();
			 for(OrderDetailsNewInfo orderDetailsNewInfoFromDB : detailsNew) 
			 {
				 OrderDetailInfo orderInfoToDispaly = new OrderDetailInfo();
				 ProductInfo product = new ProductInfo();
				 orderInfoToDispaly.setAmount(orderDetailsNewInfoFromDB.getAmount());
				 orderInfoToDispaly.setPrice(orderDetailsNewInfoFromDB.getPrice());
				 orderInfoToDispaly.setQuanity(orderDetailsNewInfoFromDB.getQuanity());
				 orderInfoToDispaly.setProductCode(orderDetailsNewInfoFromDB.getProductID());
                
				 //Below 5 lines are working fine with RestTemplate...
				 //String url = "http://order-ms/product/"+orderDetailsNewInfoFromDB.getProductID();
			  	 //RestTemplate rt = new RestTemplate();
			     //ResponseEntity<ProductInfo> responseEntity = null;
			  	 //responseEntity = rt.getForEntity(url, ProductInfo.class );
			  	 //product = responseEntity.getBody();
				 
				 //Using feign..
				  product = orderFeignClient.getProductNewByCode(orderDetailsNewInfoFromDB.getProductID());
			  		
			  	 orderInfoToDispaly.setProductName(product.getName());	
			  	 detailsTodisplay.add(orderInfoToDispaly);
			  		
			 }
	     
	      orderInfo.setDetails(detailsTodisplay);
	      model.addAttribute("orderInfo", orderInfo);
	      return "order";
	   }
	   
	   
	   
	   
	   
	 
	// Product List
	   @RequestMapping({ "/productList1" })
	   public String listProductHandler(Model model,HttpServletRequest request ) {
	 
//	      PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
//	            maxResult, maxNavigationPage, likeName);
		   HttpSession session = request.getSession();
			String jwtTocken = (String)session.getAttribute("USER_TOCKEN");
			
	      try {
			getProductDetails(jwtTocken, model);
		} catch (Exception  ex) {
			ex.printStackTrace();
		} 
	      
//	      PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
//		            maxResult, maxNavigationPage, likeName);
//	      
	 
//	      model.addAttribute("paginationProducts", result);
	      return "productList";
	   }
	 
	   
	   private HttpHeaders getHeaders() {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Accept", "application/json");
			return headers;
		}
	   
	   private void getProductDetails(String jwtTocken, Model model) {
			String productDetailsString = "";
			
			
			///TODO This is also working......
			List<ProductInfo> list = new ArrayList<>();
			ResponseEntity<ProductInfo[]> responseEntity = null;
			ProductInfo productInfo[] = null;
			String url = "http://account-Login-ms/productsNew";
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", "Bearer "+jwtTocken);
			HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
			try {
				responseEntity = template.exchange(url, HttpMethod.GET, jwtEntity,ProductInfo[].class);
			} catch(Exception ex) {
				throw ex;
			 }
			
			 productInfo = responseEntity.getBody();
			 list = new ArrayList<>(Arrays.asList(productInfo));
			
				 PaginationResult<ProductInfo> result = new PaginationResult<ProductInfo>();
				 
				 result.setList(list);
				 
				model.addAttribute("paginationProducts", result);
				model.addAttribute("productDetails", productDetailsString);
				model.addAttribute("tocken", jwtTocken);
		}

 

}
