package com.example.rocketia.data.remote.api

interface AIApiService {
    suspend fun sendPrompt(stack: String, question: String): String?
}