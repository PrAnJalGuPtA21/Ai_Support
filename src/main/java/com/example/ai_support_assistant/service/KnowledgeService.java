package com.example.ai_support_assistant.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KnowledgeService {
    private final VectorStore vectorStore;

    public KnowledgeService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void addKnowledge() {

        List<Document> documents = List.of(

                new Document(
                        "Spring Boot is a Java framework used to build production-ready backend applications. " +
                                "It simplifies configuration and provides embedded servers and auto-configuration.",
                        Map.of("topic", "spring-boot")
                ),

                new Document(
                        "Spring AI is a framework that makes it easier to integrate AI models into Spring Boot applications. " +
                                "It provides abstractions such as ChatClient, EmbeddingModel and VectorStore.",
                        Map.of("topic", "spring-ai")
                ),

                new Document(
                        "RAG stands for Retrieval Augmented Generation. " +
                                "It retrieves relevant information from a knowledge source and provides that information to an AI model as context.",
                        Map.of("topic", "rag")
                ),

                new Document(
                        "A vector database stores vector embeddings and allows applications to perform similarity searches based on semantic meaning.",
                        Map.of("topic", "vector-database")
                )
        );

        vectorStore.add(documents);
    }

    public List<Document> searchKnowledge(String question){
        SearchRequest request = SearchRequest.builder().query(question).topK(3).similarityThreshold(0.7).build();
        return vectorStore.similaritySearch(request);
    }
}
