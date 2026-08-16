package com.example.ai_support_assistant.controller;

import com.example.ai_support_assistant.service.AiService;
import com.example.ai_support_assistant.service.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;
    private final EmbeddingService embeddingService;

    public AiController(
            AiService aiService,
            EmbeddingService embeddingService) {
        this.aiService = aiService;
        this.embeddingService = embeddingService;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody String question) {
        return aiService.ask(question);
    }

    @GetMapping("/embedding")
    public float[] embedding(@RequestParam String text) {
        return embeddingService.generateEmbeddings(text);
    }
}
