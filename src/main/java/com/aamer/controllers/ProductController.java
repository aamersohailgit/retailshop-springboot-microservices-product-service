package com.aamer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aamer.dto.Coupon;
import com.aamer.model.Product;
import com.aamer.repos.ProductRepo;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${coupon-service-url}")
	private String couponServiceUrl;
	
	
	@PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Coupon coupon = restTemplate.getForObject(couponServiceUrl+product.getCouponCode(), Coupon.class);
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        Product savedProduct = productRepo.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

	
	
	
	@GetMapping("/")
	public List<Product> getProducts(){
		List<Product> products = null;
		try {
			products = productRepo.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return products;
        
    }
}
