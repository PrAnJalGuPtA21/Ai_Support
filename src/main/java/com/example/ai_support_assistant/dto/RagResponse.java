package com.example.ai_support_assistant.dto;

import java.util.List;

public record RagResponse(String answer, List<ProductResponse> products) {
}
