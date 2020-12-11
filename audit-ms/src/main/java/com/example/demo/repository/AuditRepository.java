package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Audit;
import com.example.demo.entity.AuditNew;

@Repository
public class AuditRepository {

	
	@Autowired
	JdbcTemplate template;
	
	class AuditRowMapper implements RowMapper<Audit> {
		@Override
		public Audit mapRow(ResultSet rs, int rowNum) throws SQLException {
			Audit audit = new Audit();
			audit.setOrderID(rs.getString("orderID"));
			audit.setUserID(rs.getString("userID"));
			audit.setProductName(rs.getString("product_name"));
			audit.setQuantity(rs.getInt("quantity"));
			audit.setOrderDate(rs.getDate("order_date"));
			audit.setStatus(rs.getString("status"));
			return audit;
		}

	}
	

	
	class AuditNewRowMapper implements RowMapper<AuditNew> {
		@Override
		public AuditNew mapRow(ResultSet rs, int rowNum) throws SQLException {
			AuditNew auditnew = new AuditNew();
			auditnew.setAuditID(rs.getString("auditID"));
			auditnew.setUserID(rs.getString("userID"));
			auditnew.setProductCode(rs.getString("product_code"));
			auditnew.setProductName(rs.getString("productName"));
			auditnew.setTotAmount(rs.getDouble("totAmount"));
			auditnew.setPrice(rs.getDouble("price"));
			auditnew.setQuantity(rs.getInt("quantity"));
			auditnew.setName(rs.getString("name"));
			auditnew.setEmail(rs.getString("email"));
			auditnew.setPhone(rs.getString("phone"));
			auditnew.setAddress(rs.getString("address"));
			auditnew.setOrderDate(rs.getDate("order_date"));
			auditnew.setStatus(rs.getString("status"));
			return auditnew;
		}

	}
	
	public List<Audit> findAllAudit() {
		return template.query("select * from AUDIT_TBL", new AuditRowMapper());
	}
	
	
	public List<Audit> getAuditsByID(String userID) {
//		return template.queryForObject("select * from AUDIT_TBL where userID = ?", new Object[]{userID}, new AuditRowMapper());
		return template.query("select * from AUDIT_TBL where userID = " +"'"+userID+"' ", new AuditRowMapper());
	}
	
	public Audit getAuditByID(String userID) {
		return template.queryForObject("select * from AUDIT_TBL where userID = ?", new Object[]{userID}, new AuditRowMapper());
	}
	
	
	public int insertAudit(Audit audit) {
		
		try {
			return template.update("insert into AUDIT_TBL (orderID,userID,product_name,quantity, order_date, status) " + "values(?,  ?, ?, ?, ?, ?)",
					new Object[] { audit.getOrderID(), audit.getUserID(),audit.getProductName(),audit.getQuantity(),audit.getOrderDate(),audit.getStatus() });
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		
	}
	
	public void deleteAuditByUserID( String userID) {
		template.execute("delete from  AUDIT_TBL where userID = " +"'"+userID+"' " );
	}
	
	
	//AuditNew
	
	public AuditNew getAuditByCodeUserID(String code,String userID) {
		return template.queryForObject("select * from ADT_TABLE where product_code = ? and userID = ? ", new Object[]{code,userID}, new AuditNewRowMapper());
	}
	
	
	//Added to Audit new
	public int addAuditNew(AuditNew cart) {
		return template.update(
				"insert into ADT_TABLE (auditID,userID,product_Code,productName,totAmount,price,quantity,name,email,phone,address,order_date,status) "
						+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] { cart.getAuditID(), cart.getUserID(), cart.getProductCode(), cart.getProductName(), 
						cart.getTotAmount(), cart.getPrice(),cart.getQuantity(),cart.getName(),cart.getEmail(),cart.getPhone(),
						 cart.getAddress(),cart.getOrderDate(),cart.getStatus() });
	}
	
	
	public List<AuditNew> findAllAuditsNew() {
		return template.query("select * from ADT_TABLE", new AuditNewRowMapper());
	}
	

	
}
