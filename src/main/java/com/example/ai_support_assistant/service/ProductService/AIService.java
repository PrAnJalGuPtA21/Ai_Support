package com.example.ai_support_assistant.service.ProductService;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    private final VectorStore vectorStore;

    public AIService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> searchQuery(String query) {
        SearchRequest searchRequest = SearchRequest.builder().query(query).topK(5).build();
        return vectorStore.similaritySearch(searchRequest);
    }
}
