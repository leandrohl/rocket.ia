package com.example.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketia.domain.usecase.CheckHasSelectedStackUseCase
import com.example.rocketia.ui.event.WelcomeUiEvent
import com.example.rocketia.ui.state.WelcomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val checkHasSelectedStackUseCase: CheckHasSelectedStackUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<WelcomeUiState> = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: WelcomeUiEvent) {
        when(event) {
            is WelcomeUiEvent.CheckHasSelectedStack -> {
                checkHasSelectedStack()
            }
        }
    }

    private fun checkHasSelectedStack() {
        viewModelScope.launch {
            val hasSelectedStack = checkHasSelectedStackUseCase.invoke()
            _uiState.update { currentUiState ->
                currentUiState.copy(hasSelectedStack = hasSelectedStack)
            }
        }
    }

}