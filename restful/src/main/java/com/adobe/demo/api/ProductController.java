package com.adobe.demo.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.demo.entity.Product;
import com.adobe.demo.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/products")
@Api(value = "my product controller")
public class ProductController {
	
	@Autowired
	private OrderService service;
	
	 // http://localhost:8080/api/products/
	//  http://localhost:8080/api/products?low=1000&high=50000
	
	@ApiOperation(value = "get all products")
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low, 
			@RequestParam(name="high", defaultValue = "0.0") double high) {
		if(low == 0.0 && high == 0.0) {
			return service.getProducts();
		} else {
			return service.getByRange(low, high);
		}
	}
	
	// http://localhost:8080/api/products/4
	
	@GetMapping("/{pid}")
	public @ResponseBody Product getProduct(@PathVariable("pid") int id) {
		return service.getProductById(id);
	}
	
	@PostMapping()
	public  ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {
		Product prd = service.addProduct(p);
		return new ResponseEntity<Product>(prd, HttpStatus.CREATED); // 201
	}
	
	@PutMapping("/{pid}")
	public @ResponseBody Product updateProduct(@PathVariable("pid") int id, @RequestBody Product p) {
		service.updateProduct(id, p.getPrice());
		return service.getProductById(id);
	}
}
