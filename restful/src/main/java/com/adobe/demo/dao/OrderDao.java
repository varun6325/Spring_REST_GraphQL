package com.adobe.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.demo.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer>{
	
}
