package com.example.ai_support_assistant.controller;

import com.example.ai_support_assistant.dto.RagResponse;
import com.example.ai_support_assistant.service.DummyAIRagService.AiService;
import com.example.ai_support_assistant.service.DummyAIRagService.EmbeddingService;
import com.example.ai_support_assistant.service.DummyAIRagService.KnowledgeService;
import com.example.ai_support_assistant.service.ProductService.AIService;
import com.example.ai_support_assistant.service.ProductService.RagService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;
    private final EmbeddingService embeddingService;
    private final KnowledgeService knowledgeService;
    private final AIService productAiService;
    private final RagService productRagService;

    public AiController(
            AiService aiService,
            EmbeddingService embeddingService,
            KnowledgeService knowledgeService,
            AIService productAiService,
            RagService productRagService
        ) {
        this.aiService = aiService;
        this.embeddingService = embeddingService;
        this.knowledgeService = knowledgeService;
        this.productAiService = productAiService;
        this.productRagService = productRagService;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody String question) {
        return aiService.ask(question);
    }

    @GetMapping("/embedding")
    public float[] embedding(@RequestParam String text) {
        return embeddingService.generateEmbeddings(text);
    }

    @GetMapping("/knowledge")
    public String addKnowledge(){
        knowledgeService.addKnowledge();
        return "Knowledge Added Successfully";
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String question){
        return knowledgeService.searchKnowledge(question);
    }

    @GetMapping("/rag/context")
    public String getRagContext(@RequestParam String question){
        return knowledgeService.askWithRAG(question);
    }

    @GetMapping("/searchProduct")
    public List<Document> searchProduct(@RequestParam String query){
        return productAiService.searchQuery(query);
    }

    @GetMapping("/askProduct")
    public RagResponse askProduct(@RequestParam String ask){
        return productRagService.askQuestion(ask);
    }

}
