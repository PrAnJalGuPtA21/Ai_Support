package com.example.ai_support_assistant.controller;

import com.example.ai_support_assistant.dto.ProductRequest;
import com.example.ai_support_assistant.dto.ProductResponse;
import com.example.ai_support_assistant.service.ProductService.ProductEmbeddingService;
import com.example.ai_support_assistant.service.ProductService.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ProductEmbeddingService productEmbeddingService;

    public ProductController(ProductService productService, ProductEmbeddingService productEmbeddingService) {
        this.productService = productService;
        this.productEmbeddingService = productEmbeddingService;
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductRequest request){
        productService.createProduct(request);
        return "Product Added Successfully";
    }

    @GetMapping("/getProductList")
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProduct();
    }

    @GetMapping("/indexProducts")
    public String indexAllProducts(){
        productEmbeddingService.indexAllProduct();

        return "Indexing Created Successfully";
    }
}
