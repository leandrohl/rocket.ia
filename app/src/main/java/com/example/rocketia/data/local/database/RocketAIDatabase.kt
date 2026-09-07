package com.example.rocketia.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val ROCKET_AI_DATABASE_NAME = "rocket_ai_db"
@Database(entities = [AiChatTextEntity::class], version = 1)
abstract class RocketAIDatabase: RoomDatabase() {
    abstract fun aiChatHistoryDao(): AIChatHistoryDao
}