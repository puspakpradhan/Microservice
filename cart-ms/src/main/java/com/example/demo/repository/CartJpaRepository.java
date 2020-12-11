package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartNew;

@Repository
public class CartJpaRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class CartRowMapper implements RowMapper<Cart> {
		@Override
		public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
			Cart cart = new Cart();
			cart.setCartId(rs.getInt("CART_ID"));
			cart.setProdId(rs.getInt("PROD_ID"));
			cart.setUserId(rs.getInt("USER_ID"));
			cart.setUserName(rs.getString("USER_NAME"));
			cart.setQuantity(rs.getInt("QUANTITY"));
			cart.setPrice(rs.getBigDecimal("PRICE"));
			cart.setTotalAmount(rs.getBigDecimal("TOTAL_AMOUNT"));
			cart.setProdName(rs.getString("PROD_NAME"));
			cart.setProdDesc(rs.getString("PROD_DESC"));
			return cart;
		}

	}
	
	//Added for New Design starts
	
	class CartNewRowMapper implements RowMapper<CartNew> {
		@Override
		public CartNew mapRow(ResultSet rs, int rowNum) throws SQLException {
			CartNew cartNew = new CartNew();
			cartNew.setProdCode(rs.getString("PROD_CODE"));
			cartNew.setUserID(rs.getString("USERID"));
			cartNew.setProdName(rs.getString("PROD_NAME"));
			cartNew.setQuantity(rs.getInt("QUANTITY"));
			cartNew.setPrice(rs.getDouble("PRICE"));
			cartNew.setTotalAmout(rs.getDouble("SUB_TOT_AMOUNT"));
			return cartNew;
		}

	}
	
	//Find if cart is already exist
	
	public CartNew getCartByCodeUserID(String code,String userID) {
		return jdbcTemplate.queryForObject("select * from MTCART where PROD_CODE = ? and USERID = ? ", new Object[]{code,userID}, new CartNewRowMapper());
	}
	
	
	//Added to cart
	public int addCartNew(CartNew cart) {
		return jdbcTemplate.update(
				"insert into MTCART (PROD_CODE,USERID,PROD_NAME,QUANTITY,PRICE,SUB_TOT_AMOUNT) "
						+ "values(?,  ?, ?, ?, ?, ?)",
				new Object[] { cart.getProdCode(), cart.getUserID(), cart.getProdName(), cart.getQuantity(), cart.getPrice(), cart.getTotalAmout() });
	}
	
	
	public List<CartNew> findAllCartsNew() {
		return jdbcTemplate.query("select * from MTCART", new CartNewRowMapper());
	}
	
	//Modify/Update cart
	
      public int updateCartNew(CartNew cart) {
		
		String prodCode = cart.getProdCode();
		String userID = cart.getUserID();
		double price = cart.getPrice();
		int quantity = cart.getQuantity();
		double totalAmount = price * quantity;
		
		return jdbcTemplate.update("update MTCART " + " set QUANTITY = ?,  SUB_TOT_AMOUNT = ?" + " where PROD_CODE = "+"'"+prodCode+"'" +"and USERID= "+"'"+userID+"'",
				new Object[] { quantity, totalAmount });
	}
	
	
	//Delete cart
	public void deleteCartByProdCodeUserId(String code,String userID) {
		jdbcTemplate.execute("delete from  MTCART where PROD_CODE = " + "'" + code + "' "+"and USERID="+"'"+ userID +"'");
	}

	
	public List<CartNew> findCartsByUserIDNew(String userID) {
		return jdbcTemplate.query("select * from MTCART where USERID = ? ", new Object[] { userID },
				new CartNewRowMapper());
	}
	
	
	//Added for New design end
	
	

	public List<Cart> findAllCarts() {
		return jdbcTemplate.query("select * from CART_TBL", new CartRowMapper());
	}

	public List<Cart> findCartsByUserID(String userId) {
		return jdbcTemplate.query("select * from CART_TBL where USER_ID = ? ", new Object[] { userId },
				new CartRowMapper());
	}

	public int addCart(Cart cart) {
		return jdbcTemplate.update(
				"insert into CART_TBL (CART_ID,PROD_ID,USER_ID,USER_NAME,QUANTITY,PRICE,TOTAL_AMOUNT,PROD_NAME,PROD_DESC) "
						+ "values(?,  ?, ?, ?, ?, ?, ?,?,?)",
				new Object[] { cart.getCartId(), cart.getProdId(), cart.getUserId(), cart.getUserName(),
						cart.getQuantity(), cart.getPrice(), cart.getTotalAmount(), cart.getProdName(),
						cart.getProdDesc() });
	}

	public void deleteCartByUserID(String userID) {
		jdbcTemplate.execute("delete from  CART_TBL where USER_ID = " + "'" + userID + "' ");
	}

}
