package com.example.rocketia.data.mapper

import com.example.rocketia.data.local.database.AiChatTextEntity
import com.example.rocketia.domain.model.AIChatText
import com.example.rocketia.domain.model.AiChatTextType

fun AiChatTextEntity.toDomain(): AIChatText =
    when(this.from) {
        AiChatTextType.USER_QUESTION.name -> AIChatText.UserQuestion(question = this.text)
        AiChatTextType.AI_ANSWER.name -> AIChatText.AiAnswer(answer = this.text)
        else -> throw IllegalArgumentException("Invalid from value: ${this.from}")
    }

fun List<AiChatTextEntity>.toDomain() : List<AIChatText> = this.map { entity -> entity.toDomain() }