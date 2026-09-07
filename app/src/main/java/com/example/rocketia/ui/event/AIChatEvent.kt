package com.example.rocketia.ui.event

interface AIChatEvent {
    data class SendUserQuestionToAI(val question: String): AIChatEvent
}