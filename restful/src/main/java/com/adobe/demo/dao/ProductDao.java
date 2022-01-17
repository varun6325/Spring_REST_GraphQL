package com.adobe.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.demo.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{
	List<Product> findByName(String name); // select * from products where name = ?
	List<Product> findByQuantity(int quantity); // select * from products where quantity = ?
	List<Product> findByQuantityOrPrice(int quantity, double price); // select * from products where quantity = ? or price = ?
	
	@Query("from Product where price >= :low and price <= :high")
	List<Product> getByRange( @Param("low") double lower, @Param("high") double higher);
	
	@Modifying
	@Query("update Product set price = :pr where id = :id")
	void updateProductPrice(@Param("id") int id, @Param("pr") double price);
}


// @Repository class for this interface