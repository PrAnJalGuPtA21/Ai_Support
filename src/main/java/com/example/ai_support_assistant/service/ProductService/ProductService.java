package com.example.ai_support_assistant.service.ProductService;

import com.example.ai_support_assistant.dto.ProductRequest;
import com.example.ai_support_assistant.dto.ProductResponse;
import com.example.ai_support_assistant.model.Product;
import com.example.ai_support_assistant.respository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());

        Product saveProduct = productRepository.save(product);

        return mapToResponse(saveProduct);
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getStock(),
                product.getDescription()
        );
    }
}
