package com.india.kamsid.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.india.kamsid.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	List<Product> findByCategoryId(Long category_id);

	List<Product> findByNameContainingIgnoreCaseOrDesContainingIgnoreCase(String query, String query2);

}
