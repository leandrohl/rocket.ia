package com.example.rocketia.data.datasource

import com.example.rocketia.data.api.AIApiService

class AIChatRemoteDataSourceImpl(
    private val aiApiService: AIApiService
): AIChatRemoteDataSource {
    override suspend fun sendPrompt(stack: String, question: String): String? =
        aiApiService.sendPrompt(stack, question)
}