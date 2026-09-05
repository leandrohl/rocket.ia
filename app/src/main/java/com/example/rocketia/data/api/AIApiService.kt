package com.example.rocketia.data.api

interface AIApiService {
    suspend fun sendPrompt(stack: String, question: String): String?
}