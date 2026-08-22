package com.example.ai_support_assistant.service.ProductService;

import com.example.ai_support_assistant.model.Product;
import com.example.ai_support_assistant.respository.ProductRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductEmbeddingService {

    private final ProductRepository productRepository;
    private final VectorStore vectorStore;

    public ProductEmbeddingService(ProductRepository productRepository, VectorStore vectorStore) {
        this.productRepository = productRepository;
        this.vectorStore = vectorStore;
    }

    public void indexAllProduct() {
        List<Product> products = productRepository.findAll();

        List<Document> documents = products.stream().map(this::createDocument).toList();

        vectorStore.add(documents);
    }

    public Document createDocument(Product product) {
        String prompt = """
                 Product Name: %s
                Category: %s
                Brand: %s
                Price: %s
                Stock: %s
                Description: %s
                """.formatted(
                product.getName(),
                product.getCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getStock(),
                product.getDescription()
        );
        return Document.builder().text(prompt).metadata("productId", product.getId()).metadata("category", product.getCategory()).metadata("brand", product.getBrand()).build();
    }
}
