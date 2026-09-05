package com.example.rocketia.domain.usecase

import com.example.rocketia.domain.repository.AIChatRepository

class SendUserQuestionUseCase (
    private val repository: AIChatRepository
) {
    suspend operator fun invoke(question: String) {
        repository.sendUserQuestion(question)
    }
}