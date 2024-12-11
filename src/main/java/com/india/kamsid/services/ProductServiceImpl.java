
package com.india.kamsid.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.india.kamsid.entities.Category;
import com.india.kamsid.entities.NewsletterSubscription;
import com.india.kamsid.entities.Product;
import com.india.kamsid.repo.CategoryRepo;
import com.india.kamsid.repo.NewslatterRepo;
import com.india.kamsid.repo.ProductRepo;

import jakarta.mail.internet.MimeMessage;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public List<Category> getAllProducts() {
		try {
			return categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Product> getProductByCategory(String categoryName) {
		Optional<Category> category = categoryRepo.findByCatgName(categoryName);
		return category.map(cat -> {
			List<Product> products = cat.getProducts();
			products.sort(Comparator.comparing(Product::getId)); // Sort by product name
			return products;
		}).orElse(null);
	}

	@Override
	public Product saveData(Product product, MultipartFile image) {
		try {
			// Create a unique name for the new image
			String uniqueImageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			String imagePath = "E://products-images/" + uniqueImageName;

			// Create directories if they don't exist
			Files.createDirectories(Paths.get(imagePath).getParent());
			// Save the new image
			Files.write(Paths.get(imagePath), image.getBytes());

			// Set the unique image name in the product
			product.setImage(uniqueImageName);

			return productRepo.save(product);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Product updateProduct(Product newProductData, MultipartFile image) throws IOException {
		// Fetching the existing product
		Product existingProduct = productRepo.findById(newProductData.getId())
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// Update only when the image if the new one is provided
		if (image != null) {
			// Delete the old image only if it exists
			String oldImagePath = "E://products-images/" + existingProduct.getImage();
			Files.deleteIfExists(Paths.get(oldImagePath)); // Delete existing image

			// Creating a unique name for the new image
			String uniqueImageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			String newImagePath = "E://products-images/" + uniqueImageName;

			// Saving the new image
			Path path = Paths.get(newImagePath);
			Files.createDirectories(path.getParent());
			Files.write(path, image.getBytes());
			existingProduct.setImage(uniqueImageName); // Update the image name
		}

		// Update other fields only if new values are provided
		if (newProductData.getName() != null) {
			existingProduct.setName(newProductData.getName());
		}
		if (newProductData.getDes() != null) {
			existingProduct.setDes(newProductData.getDes());
		}
		if (newProductData.getCategoryId() != null) {
			existingProduct.setCategoryId(newProductData.getCategoryId());
		}

		return productRepo.save(existingProduct);
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		try {
			return productRepo.findById(id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteProductById(Long id) {
		try {
			productRepo.deleteById(id);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			;
		}
	}

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private NewslatterRepo newsLatterRepo;

	public String subscribe(String email) {
		if (newsLatterRepo.existsByEmail(email)) {
			return "Email is already subscribed.";
		}

		NewsletterSubscription subscription = new NewsletterSubscription();
		subscription.setEmail(email);
		newsLatterRepo.save(subscription);

		// Send a confirmation email
		sendConfirmationEmail(email);

		return "Subscription successful!";
	}

	private void sendConfirmationEmail(String email) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for multipart

			helper.setTo(email);
			helper.setFrom("neerajkumarroy805@gmail.com");
			helper.setSubject("Thank You for Subscribing!");

			// Updated HTML content with consistent social icons
			String emailContent = "<html>"
				    + "<body style='margin: 0; padding: 0; background-color: #f9f9f9; font-family: Arial, sans-serif;'>"
				    + "<div style='max-width: 600px; margin: 30px auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);'>"
				    + "   <div style='background-color: #4A90E2; color: white; padding: 20px; text-align: center;'>"
				    + "       <h1 style='margin: 0; font-size: 24px;'>Thank You for Subscribing!</h1>"
				    + "   </div>"
				    + "   <div style='padding: 20px; color: #333;'>"
				    + "       <p style='font-size: 16px; line-height: 1.6;'>Dear Subscriber,</p>"
				    + "       <p style='font-size: 16px; line-height: 1.6;'>We're thrilled to have you on board! You will now receive updates regarding Complete Packaging solutions directly to your inbox.</p>"
				    + "       <p style='font-size: 16px; line-height: 1.6;'>If you have any questions, feel free to <a href='mailto:neerajkumarroy805@gmail.com' style='color: #4A90E2; text-decoration: none;'>reach out to us</a>.</p>"
				    + "       <div style='margin: 20px 0; border-top: 1px solid #eaeaea;'></div>"
				    + "       <p style='font-size: 14px; color: #666;'>We look forward to keeping you informed and engaged!</p>"
				    + "   </div>"
				    + "   <div style='background-color: #f4f4f4; padding: 20px; text-align: center;'>"
				    + "       <p style='font-size: 14px; margin: 0; font-weight: bold;'>Follow us on:</p>"
				    + "       <div style='margin: 20px 0;'>"
				    + "           <a href='https://www.facebook.com' style='text-decoration: none; margin: 0 15px; font-size: 16px; color: #3b5998; font-weight: bold;'>Facebook</a>"
				    + "           <a href='https://www.twitter.com' style='text-decoration: none; margin: 0 15px; font-size: 16px; color: #1DA1F2; font-weight: bold;'>Twitter</a>"
				    + "           <a href='https://www.instagram.com' style='text-decoration: none; margin: 0 15px; font-size: 16px; color: #E4405F; font-weight: bold;'>Instagram</a>"
				    + "       </div>"
				    + "       <p style='font-size: 12px; color: #888; margin: 10px 0;'>© 2024 Kamsid India. All rights reserved.</p>"
				    + "   </div>"
				    + "</div>"
				    + "</body>"
				    + "</html>";


			helper.setText(emailContent, true); // true indicates that the text is HTML

			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			// Handle the exception (e.g., log the error, rethrow it, etc.)
		}
	}

	// Method to search products
	public List<Product> searchProducts(String query) {
		// If the query is empty, return an empty list
		if (query == null || query.trim().isEmpty()) {
			return List.of();
		}

		return productRepo.findByNameContainingIgnoreCaseOrDesContainingIgnoreCase(query, query);
	}
}
