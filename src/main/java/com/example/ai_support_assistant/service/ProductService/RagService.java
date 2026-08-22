package com.example.ai_support_assistant.service.ProductService;

import com.example.ai_support_assistant.dto.RagResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final AIService aiService;

    public RagService(ChatClient.Builder chatClientBuilder, AIService aiService) {
        this.chatClient = chatClientBuilder.build();
        this.aiService = aiService;
    }

    public RagResponse askQuestion(String question) {

//        R -> Retrieval
        // get the relevant product from the DB
        List<Document> documents = aiService.searchQuery(question);

//        A -> Augmentation
        //convert the doc into the context
        String context = documents.stream().map(Document::getText).reduce("", (result, document) -> result + "\n" + document);

//        G -> Generation
        //give the doc + context to gemini.
        return chatClient.prompt()
                .system("""
                        You are an e-commerce product assistant.
                        
                        Answer the user's question using ONLY the
                        product information provided in the context.
                        
                        If the answer cannot be found in the context,
                        say that the requested information is not available.
                        
                        Do not invent products, prices, brands, or features.
                        """)
                .user("""
                        Context:
                        %s
                        
                        User Question:
                        %s
                        """.formatted(context, question))
                .call()
                .entity(RagResponse.class);
    }
}
