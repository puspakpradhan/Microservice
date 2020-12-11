package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductNew;

@Repository
public class ProductRepository {
	
	
	@Autowired
	JdbcTemplate jdbcTemplate; 
	
	
	
	class ProductRowMapper implements RowMapper<Product> {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			product.setId(rs.getInt("id"));
			product.setProductName(rs.getString("PRODUCT_NAME"));
			product.setProductDesc(rs.getString("PRODUCT_DESC"));
			product.setPrice(rs.getInt("PRICE"));
			product.setCatId(rs.getInt("CAT_ID"));
			return product;
		}

	}
	
	
	
	class ProductNewRowMapper implements RowMapper<ProductNew> {
		@Override
		public ProductNew mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProductNew productNew = new ProductNew();
			productNew.setCode(rs.getString("CODE"));
			productNew.setName(rs.getString("NAME"));
			productNew.setPrice(rs.getDouble("PRICE"));
			return productNew;
		}

	}
	
	//New design starts
	public List<ProductNew> getAllProductsNew() {
		return jdbcTemplate.query("select * from PRODUCTS", new ProductNewRowMapper());
	}
	
	public ProductNew getProductByCode(String code) {
		return jdbcTemplate.queryForObject("select * from PRODUCTS where CODE = ?", new Object[]{code}, new ProductNewRowMapper());
	}
	
	//New Design end
	
	
	public List<Product> getAllProducts() {
		return jdbcTemplate.query("select * from PRODUCT_TBL", new ProductRowMapper());
	}

	
	public Product getProductById(int id) {
		return jdbcTemplate.queryForObject("select * from PRODUCT_TBL where id = ?", new Object[]{id}, new ProductRowMapper());
	}
}
