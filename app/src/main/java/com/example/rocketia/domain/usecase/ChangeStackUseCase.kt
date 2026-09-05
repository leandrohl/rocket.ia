package com.example.rocketia.domain.usecase

import com.example.rocketia.domain.model.AiChatTextType
import com.example.rocketia.domain.repository.AIChatRepository

class ChangeStackUseCase(
    private val repository: AIChatRepository
) {
    suspend operator fun invoke(stack: String) {
        repository.changeStack(stack)
    }
}