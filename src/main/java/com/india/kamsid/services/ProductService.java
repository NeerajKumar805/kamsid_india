package com.india.kamsid.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.india.kamsid.entities.Category;
import com.india.kamsid.entities.Product;

public interface ProductService {

	List<Category> getAllProducts();

	Product saveData(Product product, MultipartFile image);

	Optional<Product> getProductById(Long id);

	void deleteProductById(Long id);

	String subscribe(String email);

	List<Product> getProductByCategory(String category);

	Product updateProduct(Product newProductData, MultipartFile image) throws IOException;

	List<Product> searchProducts(String query);
}