package com.example.rocketia.data.datasource

import com.example.rocketia.data.local.database.AIChatHistoryDao
import com.example.rocketia.data.local.database.AiChatTextEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AIChatLocalDataSourceImpl(
    private val aiChatHistoryDao: AIChatHistoryDao
): AIChatLocalDataSource {
    override val aiCurrentChatBySelectedStack: Flow<List<AiChatTextEntity>>
        get() = flow {
            val selectedStack = ""
            aiChatHistoryDao.getAllByStack(selectedStack)
        }

    override suspend fun insertAIChatConversation(
        question: AiChatTextEntity,
        answer: AiChatTextEntity
    ) {
        aiChatHistoryDao.insertAll(question, answer)
    }

    override val selectedStack: Flow<String>
        get() = TODO("Not yet implemented")

    override suspend fun changeSelectedStack(stack: String) {
        TODO("Not yet implemented")
    }

    override val firstLaunch: Flow<Boolean>
        get() = TODO("Not yet implemented")

    override suspend fun changeFirstLaunch() {
        TODO("Not yet implemented")
    }
}