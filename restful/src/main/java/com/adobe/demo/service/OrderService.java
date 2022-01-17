package com.adobe.demo.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.demo.dao.ProductDao;
import com.adobe.demo.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;
	
	public Product addProduct(Product p) {
		return productDao.save(p);
	}
	
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	public Product getProductById(int id) {
		return productDao.findById(id).get(); // EAGER fetching
		
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
