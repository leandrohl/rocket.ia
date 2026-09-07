package com.example.rocketia.ui.event

sealed interface WelcomeUiEvent {
    object CheckHasSelectedStack: WelcomeUiEvent
}