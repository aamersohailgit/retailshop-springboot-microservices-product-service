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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aamer.dto.Coupon;
import com.aamer.model.Product;
import com.aamer.repos.ProductRepo;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController extends ResponseEntityExceptionHandler {

	@Autowired
	ProductRepo productRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${coupon-service-url}")
	private String couponServiceUrl;

	@GetMapping("/")
	public List<Product> getProducts() {
		List<Product> products = null;
		try {
			products = productRepo.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return products;
	}

	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product savedProduct = null;
		if (product.getPrice() == null || product.getName() == null) {
			return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
		}
		try {
			Coupon coupon = restTemplate.getForObject(couponServiceUrl + product.getCouponCode(), Coupon.class);
			product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
			savedProduct = productRepo.save(product);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

}
