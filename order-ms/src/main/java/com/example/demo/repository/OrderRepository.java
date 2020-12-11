package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.OrderDetailsNew;
import com.example.demo.entity.OrderNew;
import com.example.demo.entity.ProductNew;
import com.example.demo.entity.UserOrder;

@Repository
public class OrderRepository {
	
	@Autowired
	JdbcTemplate template;
	
	
	
	class ConversionFactorRowMapper implements RowMapper<Order> {
		@Override
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setUserID(rs.getString("userID"));
			order.setOrderID(rs.getString("orderID"));
			order.setProductID(rs.getString("productID"));
			order.setProductName(rs.getString("product_Name"));
			order.setProductDesc(rs.getString("product_Desc"));
			order.setPrice(rs.getInt("price"));
			order.setQuantity(rs.getInt("quantity"));
			order.setStatus(rs.getString("status"));
			return order;
		}

	}
	
	class OrderNewRowMapper implements RowMapper<OrderNew> {
		@Override
		public OrderNew mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderNew orderNew = new OrderNew();
			orderNew.setId(rs.getString("ID"));
			orderNew.setAmount(rs.getDouble("AMOUNT"));
			orderNew.setCustomerAddress(rs.getString("CUSTOMER_ADDRESS"));
			orderNew.setCustomerEmail(rs.getString("CUSTOMER_EMAIL"));
			orderNew.setCustomerName(rs.getString("CUSTOMER_NAME"));
			orderNew.setCustomerPhone(rs.getString("CUSTOMER_PHONE"));
			orderNew.setOrderDate(rs.getDate("ORDER_DATE"));
			orderNew.setOrderNum(rs.getInt("ORDER_NUM"));
			return orderNew;
		}

	}
	
	
	class ProductNewRowMapper implements RowMapper<ProductNew> {
		@Override
		public ProductNew mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductNew productNew = new ProductNew();
			productNew.setCode(rs.getString("CODE"));
			productNew.setName(rs.getString("NAME"));
			productNew.setPrice(rs.getDouble("PRICE"));
			productNew.setCreateDate(rs.getDate("CREATE_DATE"));
			return productNew;
		}

	}
	
	
//	ID         VARCHAR2(50) not null,
//	--  AMOUNT     DOUBLE not null,
//	--  PRICE      DOUBLE not null,
//	--  QUANITY    NUMBER(10) not null,
//	--  ORDER_ID   VARCHAR2(50) not null,
//	--  PRODUCT_ID VARCHAR2(20) not null
//	
	
	class OrderDetailsRowMapper implements RowMapper<OrderDetail> {
		@Override
		public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setId(rs.getString("ID"));
			orderDetail.setAmount(rs.getDouble("AMOUNT"));
			orderDetail.setPrice(rs.getDouble("PRICE"));
			orderDetail.setQuanity(rs.getInt("QUANITY"));
			return orderDetail;
		}

	}
	
	//Added without F-key
	class OrderDetailsNewRowMapper implements RowMapper<OrderDetailsNew> {
		@Override
		public OrderDetailsNew mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderDetailsNew orderDetailsNew = new OrderDetailsNew();
			orderDetailsNew.setId(rs.getString("ID"));
			orderDetailsNew.setAmount(rs.getDouble("AMOUNT"));
			orderDetailsNew.setPrice(rs.getDouble("PRICE"));
			orderDetailsNew.setQuanity(rs.getInt("QUANITY"));
			orderDetailsNew.setProductID(rs.getString("PRODUCT_ID"));
			orderDetailsNew.setOrderID(rs.getString("ORDER_ID"));
			return orderDetailsNew;
		}

	}
	
	class UserOrderRowMapper implements RowMapper<UserOrder> {
		@Override
		public UserOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserOrder userOrder = new UserOrder();
			userOrder.setUserID(rs.getString("USERID"));
			userOrder.setOrderID(rs.getString("ORDER_ID"));
			return userOrder;
		}

	}
	
	
	public ProductNew getProductNewByCode(String code) {
		return template.queryForObject("select * from PRODUCTS where CODE = ?  ", new Object[]{code}, new ProductNewRowMapper());
		
	}
	
	public int insertOrderNew(OrderNew orderNew) {
		
		try {
			return template.update("insert into ORDERS (ID,AMOUNT,CUSTOMER_ADDRESS,"
					+ "CUSTOMER_EMAIL,CUSTOMER_NAME,CUSTOMER_PHONE,ORDER_DATE,ORDER_NUM) " + "values(?,  ?, ?, ?, ?, ?, ?, ?)",
					new Object[] { orderNew.getId(), orderNew.getAmount(),orderNew.getCustomerAddress(),orderNew.getCustomerEmail(),orderNew.getCustomerName(), 
							orderNew.getCustomerPhone(),orderNew.getOrderDate(),orderNew.getOrderNum() });
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
//	ID         VARCHAR2(50) not null,
//	--  AMOUNT     DOUBLE not null,
//	--  PRICE      DOUBLE not null,
//	--  QUANITY    NUMBER(10) not null,
//	--  ORDER_ID   VARCHAR2(50) not null,
//	--  PRODUCT_ID VARCHAR2(20) not null
	
   public int insertOrderDetailNew(OrderDetail orderDetailNew) {
		
		try {
			return template.update("insert into Order_Details (ID,AMOUNT,PRICE,"
					+ "QUANITY,ORDER_ID,PRODUCT_ID) " + "values(?,  ?, ?, ?, ?, ?)",
					new Object[] { orderDetailNew.getId(), orderDetailNew.getAmount(),orderDetailNew.getPrice(),orderDetailNew.getQuanity(), 
							orderDetailNew.getOrder().getId(),orderDetailNew.getProduct().getCode() });
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
   
   
   //Withoyt F_key
   
   public int insertOrderDetailsNewWithoutFK(OrderDetailsNew orderDetailNew) {
		
		try {
			return template.update("insert into ORDER_DETAILS_NEW (ID,AMOUNT,PRICE,"
					+ "QUANITY,ORDER_ID,PRODUCT_ID) " + "values(?,  ?, ?, ?, ?, ?)",
					new Object[] { orderDetailNew.getId(), orderDetailNew.getAmount(),orderDetailNew.getPrice(),orderDetailNew.getQuanity(), 
							orderDetailNew.getOrderID(),orderDetailNew.getProductID() });
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
   
   public int insertUserOrder(UserOrder userOrder) {
		
		try {
			return template.update("insert into USER_ORDER (USERID,"
					+ "ORDER_ID) " + "values(?,  ?)",
					new Object[] { userOrder.getUserID(), userOrder.getOrderID() });
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	
	public List<OrderNew> findAllOrdersNew() {
		return template.query("select * from ORDERS", new OrderNewRowMapper());
	}
	
	public OrderNew getOrderNewByID(String orderID) {
		return template.queryForObject("select * from ORDERS where ID = ?",new Object[]{orderID}, new OrderNewRowMapper());
	}
	
	
	public List<OrderDetail> findAllOrdersDetails() {
		return template.query("select * from Order_Details", new OrderDetailsRowMapper());
	}
	
	public List<OrderDetailsNew> findAllOrdersDetailsWithOutFK() {
		return template.query("select * from ORDER_DETAILS_NEW", new OrderDetailsNewRowMapper());
	}
	
	public List<OrderDetailsNew> findAllOrdersDetailsWithOutFKById(String orderId) {
		return template.query("select * from ORDER_DETAILS_NEW where ORDER_ID = ?", new Object[]{orderId},new OrderDetailsNewRowMapper());
	}
	
	
	
	public List<UserOrder> getOrderByUserID(String userID) {
		return template.query("select * from USER_ORDER where USERID = ?", new Object[]{userID}, new UserOrderRowMapper());
	}
	
	
	
	
	//new design end
	
	
//	public List<ConversionFactor> findAllFactors() {
//		return jdbcTemplate.query("select * from CONVERSION_FACTOR", new ConversionFactorRowMapper());
//	}
	
	
	public List<Order> getOrderByID(String orderID) {
		return template.query("select * from ORDER_TABLE where userID = ?",
				new Object[]{orderID}, new ConversionFactorRowMapper());
	}
	
	
	public int insertOrder(Order order) {
		
		try {
			return template.update("insert into ORDER_TABLE (userID,orderID,productID,"
					+ "product_Name,product_Desc,price,quantity,status) " + "values(?,  ?, ?, ?, ?, ?, ?, ?)",
					new Object[] { order.getUserID(), order.getOrderID(),order.getProductID(),order.getProductName(),order.getProductDesc(), 
							order.getPrice(),order.getQuantity(),order.getStatus() });
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		
	}
	
	public void deleteOrderByUserID( String userID) {
		template.execute("delete from  ORDER_TABLE where userID = " +"'"+userID+"' " );
	}

	
	

}
