package com.adobe.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.demo.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{

}


// @Repository class for this interface