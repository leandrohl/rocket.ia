package com.example.rocketia.ui.event

interface AIChatHistoryEvent {
    data class SelectStack(val selectedStackName: String, val selectedStackChipId: Int): AIChatHistoryEvent
}