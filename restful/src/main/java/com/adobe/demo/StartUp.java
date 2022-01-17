package com.adobe.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adobe.demo.entity.Product;
import com.adobe.demo.service.OrderService;

@Configuration
public class StartUp {
	@Autowired
	private OrderService service;
	
	@Bean
	public CommandLineRunner runner() {
		return (args) -> {
//			addProducts();
//			listProduct();
//			getById();
//			rangeSelection();
			updateProduct();
		};
	}

	private void updateProduct() {
		service.updateProduct(3, 890.50);
	}

	private void rangeSelection() {
		List<Product> products = service.getByRange(100, 50_000);
		for(Product p : products) {
			System.out.println(p);
		}
	}

	private void getById() {
		Product p = service.getProductById(1); // Proxy
		System.out.println(p);
	}

	private void listProduct() {
		List<Product> products = service.getProducts();
		for(Product p : products) {
			System.out.println(p);
		}
	}

	private void addProducts() {
		service.addProduct(new Product(0, "iPhone 13", 120000.00, 100));
		service.addProduct(new Product(0, "Sony Bravia", 135000.00, 100));
		service.addProduct(new Product(0, "Logitech Mouse", 800.00, 100));
		service.addProduct(new Product(0, "Samsung Fridge", 35000.00, 100));
	}
}
