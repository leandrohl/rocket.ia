package com.example.rocketia.domain.usecase

import com.example.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedStackUseCase (
    private val repository: AIChatRepository
) {
    operator fun invoke(): Flow<String?> = repository.selectedStack
}