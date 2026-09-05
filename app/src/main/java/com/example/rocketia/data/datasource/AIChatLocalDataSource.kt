package com.example.rocketia.data.datasource

import com.example.rocketia.data.local.database.AiChatTextEntity
import kotlinx.coroutines.flow.Flow

interface AIChatLocalDataSource {
    val aiCurrentChatBySelectedStack: Flow<List<AiChatTextEntity>>

    suspend fun insertAIChatConversation(question: AiChatTextEntity, answer: AiChatTextEntity)

    val selectedStack: Flow<String?>

    suspend fun changeSelectedStack(stack: String)

}