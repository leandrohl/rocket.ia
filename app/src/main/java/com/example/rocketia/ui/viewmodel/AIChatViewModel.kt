package com.example.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketia.domain.model.AIChatText
import com.example.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import com.example.rocketia.domain.usecase.GetSelectedStackUseCase
import com.example.rocketia.domain.usecase.SendUserQuestionUseCase
import com.example.rocketia.ui.event.AIChatEvent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AIChatViewModel(
    val getSelectedStackUseCase: GetSelectedStackUseCase,
    val getAIChatBySelectedStackUseCase: GetAIChatBySelectedStackUseCase,
    private val sendUserQuestionUseCase: SendUserQuestionUseCase

): ViewModel() {
    val selectedStack: StateFlow<String?> = getSelectedStackUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), null
    )

    val aiChatBySelectedStack: StateFlow<List<AIChatText>> = getAIChatBySelectedStackUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )

    fun onEvent(event: AIChatEvent) {
        when(event) {
            is AIChatEvent.SendUserQuestionToAI -> {
                sendUserQuestionToAI(event.question)
            }
        }
    }

    private fun sendUserQuestionToAI(question: String) {
        viewModelScope.launch {
            sendUserQuestionUseCase(question)
        }
    }
}