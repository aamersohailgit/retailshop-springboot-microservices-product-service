package com.aamer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aamer.model.Product;
import com.aamer.repos.ProductRepo;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

	@Autowired
	ProductRepo productRepo;
	
	
	@PostMapping("/")
	public Product create(@RequestParam Product product) {
		return productRepo.save(product);
	}
	
	@GetMapping("/")
	public Product get() {
		List<Product> products = productRepo.findAll();
		return (Product) products;
	}
}
