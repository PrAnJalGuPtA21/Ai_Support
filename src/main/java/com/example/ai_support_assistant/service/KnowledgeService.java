package com.example.ai_support_assistant.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {
    private final VectorStore vectorStore;

    private final ChatClient chatClient;


    public KnowledgeService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
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

    public List<Document> searchKnowledge(String question) {
        SearchRequest request = SearchRequest.builder().query(question).topK(3).similarityThreshold(0.7).build();
        return vectorStore.similaritySearch(request);
    }

    public String askWithRAG(String question) {
        SearchRequest searchRequest = SearchRequest.builder().query(question).topK(3).similarityThreshold(0.7).build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        if (documents.isEmpty()) {
            return " I don't have the information for this question";
        }

        String context = documents.stream().map(Document::getText).collect(Collectors.joining("\n\n"));
        String prompt = """
                Answer the user's question using ONLY the information provided
                in the context below.
                
                If the answer cannot be found in the context, say:
                "I don't have enough information to answer this question."
                
                Context:
                %s
                
                Question:
                %s
                """.formatted(context, question);
        return chatClient.prompt().user(prompt).call().content();
    }
}
