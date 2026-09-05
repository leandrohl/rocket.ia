package com.example.rocketia.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AIChatHistoryDao {
    @Query("SELECT * FROM aichattextentity WHERE stack = :stack ORDER BY datetime DESC")
    fun getAllByStack(stack: String): Flow<List<AiChatTextEntity>>

    @Insert
    suspend fun insertAll(vararg aiChatText: AiChatTextEntity)


}