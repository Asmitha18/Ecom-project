package com.ecomproject.ecom_project.service;

import com.ecomproject.ecom_project.model.Product;
import com.ecomproject.ecom_project.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }
    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product updatedProduct, MultipartFile imageFile) throws IOException {
        Product existingProduct = repo.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setBrand(updatedProduct.getBrand());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setReleaseDate(updatedProduct.getReleaseDate());
            existingProduct.setProductAvailable(updatedProduct.isProductAvailable());
            existingProduct.setStockQuantity(updatedProduct.getStockQuantity());

            // If an image is uploaded, update the image fields
            if (imageFile != null && !imageFile.isEmpty()) {
                existingProduct.setImageName(imageFile.getOriginalFilename());
                existingProduct.setImageType(imageFile.getContentType());
                existingProduct.setImageData(imageFile.getBytes());
            }
            return repo.save(existingProduct);
        } else {
            return null; // Product not found
        }
    }


    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

}
