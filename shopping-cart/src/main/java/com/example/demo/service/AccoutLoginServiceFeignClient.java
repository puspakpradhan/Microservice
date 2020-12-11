/*
 * package com.example.demo.service;
 * 
 * import org.springframework.cloud.openfeign.FeignClient; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable;
 * 
 * import com.example.demo.model.UserOrderInfo;
 * 
 * @FeignClient(name="account-Login-ms", url ="http://account-Login-ms:9192")
 * public interface AccoutLoginServiceFeignClient {
 * 
 * 
 * 
 * @GetMapping(path = "/order/orderList/{userID}") public
 * ResponseEntity<UserOrderInfo[]> getUserOrderList(@PathVariable String
 * userID);
 * 
 * 
 * }
 */