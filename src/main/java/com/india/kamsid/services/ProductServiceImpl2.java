//package com.india.kamsid.services;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.india.kamsid.entities.Product;
//import com.india.kamsid.exceptions.ProductNotFoundException;
//import com.india.kamsid.exceptions.ServiceException;
//import com.india.kamsid.repo.ProductRepo;
//
//@Service
//public class ProductServiceImpl2 implements ProductService {
//
//    @Autowired
//    private ProductRepo productRepo;
//
//    @Override
//    public List<Product> getAllProducts() {
//        try {
//            return productRepo.findAll();
//        } catch (Exception e) {
//            throw new ServiceException("Error retrieving product list", e);
//        }
//    }
//
//    @Override
//    public Product saveData(Product product) {
//        try {
//            return productRepo.save(product);
//        } catch (Exception e) {
//            throw new ServiceException("Error saving product", e);
//        }
//    }
//
//    @Override
//    public Product getProductById(Long id) {
//        return productRepo.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
//    }
//
//    @Override
//    public void deleteProductById(Long id) {
//        if (!productRepo.existsById(id)) {
//            throw new ProductNotFoundException("Product not found with id: " + id);
//        }
//        productRepo.deleteById(id);
//    }
//}


//package com.india.kamsid.services;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.india.kamsid.entities.Product;
//import com.india.kamsid.repo.ProductRepo;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//	@Autowired
//	private ProductRepo productRepo;
//
//	@Override
//	public List<Product> getAllproducts() {
//		try {
//			return productRepo.findAll();
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		return null;
//	}
//
//	@Override
//	public Product saveData(Product product) {
//		try {
//			Product savedProduct = productRepo.save(product);
//			return savedProduct;
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		return null;
//	}
//
//	@Override
//	public Optional<Product> getProductbyId(Long id) {
//		try {
//			return productRepo.findById(id);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public void deleteProductbyId(Long id) {
//		try {
//			productRepo.deleteById(id);
//		} catch (Exception e) {
//			System.err.println(e.getMessage());;
//		}
//	}
//
//}
