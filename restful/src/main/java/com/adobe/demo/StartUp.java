package com.adobe.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adobe.demo.dto.ReportDTO;
import com.adobe.demo.entity.Customer;
import com.adobe.demo.entity.Item;
import com.adobe.demo.entity.Order;
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
//			updateProduct();
//			newOrder();
//			listOrders();
			reports();
		};
	}

	private void reports() {
		List<ReportDTO> reports = service.getReport();
		for(ReportDTO report : reports) {
			System.out.println(report);
		}
	}

	private void listOrders() {
		List<Order> orders = service.getOrders();
		for(Order o : orders) {
			System.out.println(o.getTotal() + "," + o.getCustomer().getEmail());
			List<Item> items = o.getItems();
			for(Item item : items) {
				System.out.println(item.getProduct().getName() + " : " + item.getQty());
			}
			System.out.println(o.getTotal());
		}
	}

	private void newOrder() {
		Customer c = new Customer();
		c.setEmail("sam@adobe.com");
		
		Order order = new Order();
		order.setCustomer(c);
		
		Item i1 = new Item();
		Item i2 = new Item();
		
		Product p1 = new Product();
		p1.setId(3);
		
		Product p2 = new Product();
		p2.setId(1);
		
		i1.setProduct(p1);
		i1.setQty(3);
		
		i2.setProduct(p2);
		i2.setQty(1);
		
		order.getItems().add(i1);
		order.getItems().add(i2);
		
		service.placeOrder(order);
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
