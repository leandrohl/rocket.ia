package com.example.rocketia.domain.usecase

import com.example.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.firstOrNull

class CheckHasSelectedStackUseCase(
    private val repository: AIChatRepository
) {
    suspend operator fun invoke(): Boolean =
        repository.selectedStack.firstOrNull()?.isNotEmpty() == true
}