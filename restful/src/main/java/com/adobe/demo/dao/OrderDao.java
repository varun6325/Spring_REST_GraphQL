package com.adobe.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adobe.demo.dto.ReportDTO;
import com.adobe.demo.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer>{
	
//	@Query("from Order o inner join on o.customer")
//	List<Object[]> getReport(); // Object[0] will be Order Object[1] will be Customer
//	@Query("select c.email, c,firstName, o.orderDate, o.total from Order o inner join on o.customer")
//	List<Object[]> getReport(); // Object[0] ==> email, Object[1] ==> firstName ,....
	
	@Query("select new com.adobe.demo.dto.ReportDTO(c.email, c.firstName, o.orderDate, o.total) from Order o join  o.customer c")
	List<ReportDTO> getReport();
	
}
