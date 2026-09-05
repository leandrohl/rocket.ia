package com.example.rocketia.domain.repository

import com.example.rocketia.domain.model.AIChatText
import kotlinx.coroutines.flow.Flow

interface AIChatRepository {
    val selectedStack: Flow<String?>

    val aiChatBySelectedStack: Flow<List<AIChatText>>

    suspend fun sendUserQuestion(question: String)

    suspend fun changeStack(stack: String)

}