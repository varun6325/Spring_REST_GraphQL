package com.adobe.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.demo.dao.OrderDao;
import com.adobe.demo.dao.ProductDao;
import com.adobe.demo.dto.ReportDTO;
import com.adobe.demo.entity.Item;
import com.adobe.demo.entity.Order;
import com.adobe.demo.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDao orderDao;
	
	public List<ReportDTO> getReport() {
		return orderDao.getReport();
	}
	
	@Transactional
	public void placeOrder(Order order) {
		double total = 0.0;
		List<Item> items = order.getItems();
		for(Item item : items) {
			Product p = productDao.findById(item.getProduct().getId()).get();
			if(p.getQuantity() < item.getQty()) {
				throw new RuntimeException("product " + p.getName() + " not in stock");
			}
			p.setQuantity(p.getQuantity() - item.getQty()); // Dirty checking ==> update
			item.setAmount(p.getPrice() * item.getQty());
			total += item.getAmount();
			
		}
		order.setTotal(total);
		orderDao.save(order); // cascade takes care of saving items also
	}
	
	public List<Order> getOrders() {
		return orderDao.findAll();
	}
	
	public Product addProduct(Product p) {
		return productDao.save(p);
	}
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public Product getProductById(int id) {
		Optional<Product> opt =  productDao.findById(id); // EAGER fetching
		if(opt.isPresent()) {
			return opt.get();
		} else 
			throw new EntityNotFoundException("Product " + id +" doesn't exist!!!");
//		return productDao.getById(id); // Lazy loading ==> returns a Proxy Object
	}
	
	public List<Product> getByRange(double lower, double higher) {
		return productDao.getByRange(lower, higher);
	}
	
	@Transactional 
	public void updateProduct(int id, double price) {
		productDao.updateProductPrice(id, price);
	}
}
