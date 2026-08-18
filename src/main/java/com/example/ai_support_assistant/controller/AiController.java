package com.example.ai_support_assistant.controller;

import com.example.ai_support_assistant.service.AiService;
import com.example.ai_support_assistant.service.EmbeddingService;
import com.example.ai_support_assistant.service.KnowledgeService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;
    private final EmbeddingService embeddingService;
    private final KnowledgeService knowledgeService;

    public AiController(
            AiService aiService,
            EmbeddingService embeddingService,
            KnowledgeService knowledgeService
        ) {
        this.aiService = aiService;
        this.embeddingService = embeddingService;
        this.knowledgeService = knowledgeService;
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
}
