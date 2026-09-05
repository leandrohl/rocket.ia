package com.example.rocketia.domain.model

sealed class AIChatText {
    data class UserQuestion(val question: String): AIChatText()
    data class AiAnswer(val answer: String): AIChatText()
}