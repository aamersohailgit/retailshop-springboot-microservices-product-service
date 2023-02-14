package com.aamer.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aamer.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
