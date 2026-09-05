package com.example.rocketia.domain.usecase

import com.example.rocketia.domain.model.AIChatText
import com.example.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow

class GetAIChatBySelectedStackUseCase(
    private val repository: AIChatRepository
) {
    operator fun invoke(): Flow<List<AIChatText>> =
        repository.aiChatBySelectedStack

}