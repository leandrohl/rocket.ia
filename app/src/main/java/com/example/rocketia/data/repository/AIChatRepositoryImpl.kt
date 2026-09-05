package com.example.rocketia.data.repository

import com.example.rocketia.data.datasource.AIChatLocalDataSource
import com.example.rocketia.data.datasource.AIChatRemoteDataSource
import com.example.rocketia.data.local.database.AiChatTextEntity
import com.example.rocketia.data.mapper.toDomain
import com.example.rocketia.domain.model.AIChatText
import com.example.rocketia.domain.model.AiChatTextType
import com.example.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AIChatRepositoryImpl(
    private val aiChatLocalDataSource: AIChatLocalDataSource,
    private val aiChatRemoteDataSource: AIChatRemoteDataSource
): AIChatRepository {
    override val selectedStack: Flow<String?>
        get() = aiChatLocalDataSource.selectedStack
    override val aiChatBySelectedStack: Flow<List<AIChatText>>
        get() = aiChatLocalDataSource.aiCurrentChatBySelectedStack.map {
            currentChatEntity -> currentChatEntity.toDomain()
        }

    override suspend fun sendUserQuestion(question: String) {

        val currentSelectedStack =  selectedStack.firstOrNull().orEmpty()
        val answer = aiChatRemoteDataSource.sendPrompt(question, currentSelectedStack)

        answer?.let {
            aiChatLocalDataSource.insertAIChatConversation(
                question = createUserQuestionEntity(question, currentSelectedStack),
                answer = createAIAnswerEntity(answer, currentSelectedStack))
        }
    }

    private fun createUserQuestionEntity(question: String, stack: String): AiChatTextEntity =
        AiChatTextEntity(
            text = question,
            from = AiChatTextType.USER_QUESTION.name,
            stack = stack,
            datetime = System.currentTimeMillis()
        )

    private fun createAIAnswerEntity(answer: String, stack: String): AiChatTextEntity =
        AiChatTextEntity(
            text = answer,
            from = AiChatTextType.AI_ANSWER.name,
            stack = stack,
            datetime = System.currentTimeMillis()
        )

    override suspend fun changeStack(stack: String) {
        aiChatLocalDataSource.changeSelectedStack(stack)
    }
}