package com.example.rocketia.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AiChatTextEntity::class], version = 1)
abstract class RocketAIDatabase: RoomDatabase() {
    abstract fun aiChatHistoryDao(): AIChatHistoryDao
}