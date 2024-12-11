
package com.india.kamsid.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.india.kamsid.entities.Category;
import com.india.kamsid.entities.Product;
import com.india.kamsid.services.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	private ResponseEntity<?> getAllProducts() {

		List<Category> list = productService.getAllProducts();
		if (list != null && !list.isEmpty())
			return new ResponseEntity<>(list, HttpStatus.OK);

		return new ResponseEntity<>("Product list is empty...", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/product/{id}")
	private ResponseEntity<?> getProductById(@PathVariable Long id) {

		Optional<Product> product = productService.getProductById(id);

		if (product.isPresent())
			return new ResponseEntity<>(product, HttpStatus.FOUND);

		return new ResponseEntity<>("Product not found with id : " + id, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/products/{category}")
	private ResponseEntity<?> getProductByCategory(@PathVariable String category) {

		List<Product> list = productService.getProductByCategory(category);
		if (list != null && !list.isEmpty())
			return new ResponseEntity<>(list, HttpStatus.OK);

		return new ResponseEntity<>("Product list is empty in category : " + category, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/save")
	private ResponseEntity<?> saveData(@RequestPart("product") Product product,
	        @RequestPart("image") MultipartFile image) {
	    Product savedProduct = productService.saveData(product, image);
	    
	    if (savedProduct != null) {
	        return new ResponseEntity<>("Data Saved Successfully, with id : " + savedProduct.getId(), HttpStatus.CREATED);
	    } else {
	        return new ResponseEntity<>("Error saving product data", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@PutMapping(value = "/update")
	public ResponseEntity<?> updateProductById(@ModelAttribute Product product,
	        @RequestPart(value = "image1", required = false) MultipartFile image) {
	    try {
	        Product updatedProduct = productService.updateProduct(product, image);
	        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	    } catch (RuntimeException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Error updating product: " + e.getMessage());
	    }
	}


//	@PutMapping("/update")
//	public ResponseEntity<?> updateProductById(@RequestPart("product") Product product,
//	                                           @RequestPart(value = "image", required = false) MultipartFile image,
//	                                           @RequestPart("category") String categoryName) {
//	    try {
//	        // Check if a new image is provided
//	        if (image != null && !image.isEmpty()) {
//	            // Delete old image if it exists
//	            if (product.getImage() != null && !product.getImage().isEmpty()) {
//	                String oldImagePath = "E://products-images/" + product.getImage();
//	                Files.deleteIfExists(Paths.get(oldImagePath));
//	            }
//	            // Save the new image
//	            String newImageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
//	            String imagePath = "E://products-images/" + newImageName;
//	            Path path = Paths.get(imagePath);
//	            Files.createDirectories(path.getParent());
//	            Files.write(path, image.getBytes());
//	            
//	            // Set the new image name in the product object
//	            product.setImage(newImageName);
//	        }
//
//	        // Save product details along with category
//	        Product updatedProduct = productService.saveData(product, categoryName);
//	        if (updatedProduct != null) {
//	            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//	        } else {
//	            return new ResponseEntity<>("Something went wrong!!!", HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                             .body("Error updating product: " + e.getMessage());
//	    }
//	}

	@DeleteMapping("/products/{id}")
	private ResponseEntity<?> deleteProductById(@PathVariable Long id) {

	    Optional<Product> product = productService.getProductById(id);

	    if (product.isPresent()) {
	        productService.deleteProductById(id);
	        return new ResponseEntity<>("Product deleted successfully with id : " + id, HttpStatus.OK);
	    }

	    return new ResponseEntity<>("Product not found with id : " + id, HttpStatus.NOT_FOUND);
	}


	
    @GetMapping("/search")
    public List<?> searchProducts(@RequestParam(required = false) String query) {
        if (query == null || query.trim().isEmpty()) {
            // If the query is empty, return all products (or an empty list if you prefer)
            return productService.getAllProducts();
        }
        return productService.searchProducts(query);
    }
    
}
