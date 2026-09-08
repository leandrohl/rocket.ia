package com.example.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketia.domain.model.AIChatText
import com.example.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import com.example.rocketia.domain.usecase.GetSelectedStackUseCase
import com.example.rocketia.ui.event.AIChatHistoryEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AIChatHistoryViewModel(
    getSelectedStackUseCase: GetSelectedStackUseCase,
    private val getAIChatHistoryBySelectedStachUseCase: GetAIChatBySelectedStackUseCase
): ViewModel() {
    val selectedStack: StateFlow<String?> = getSelectedStackUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), null
    )

    private val _selectedStackChipId = MutableStateFlow<Int?>(null)
    val selectedStackChipId: StateFlow<Int?> = _selectedStackChipId.asStateFlow()

    private val _aiChatHistoryBySelectedStack: MutableStateFlow<List<AIChatText>> = MutableStateFlow(emptyList())
    val aiChatHistoryBySelectedStack: StateFlow<List<AIChatText>> = _aiChatHistoryBySelectedStack.asStateFlow()

    fun onEvent(event: AIChatHistoryEvent) {
        when(event) {
            is AIChatHistoryEvent.SelectStack -> {
                getAIChatHistoryBySelectedStack(selectedStackName = event.selectedStackName, selectedStackChipId = event.selectedStackChipId)
            }
        }
    }

    private fun getAIChatHistoryBySelectedStack(selectedStackName: String, selectedStackChipId: Int) {
        viewModelScope.launch {
            val aiChatBySelectedStack = getAIChatHistoryBySelectedStachUseCase(selectedStackName)
            _aiChatHistoryBySelectedStack.update { aiChatBySelectedStack }.also {
                _selectedStackChipId.update { selectedStackChipId }
            }
        }
    }
}