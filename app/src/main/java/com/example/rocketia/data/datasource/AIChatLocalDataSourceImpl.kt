package com.example.rocketia.data.datasource

import com.example.rocketia.data.local.database.AIChatHistoryDao
import com.example.rocketia.data.local.database.AiChatTextEntity
import com.example.rocketia.data.local.preferences.UserSettingsPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AIChatLocalDataSourceImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val aiChatHistoryDao: AIChatHistoryDao,
    private val userSettingsPreferences: UserSettingsPreferences
): AIChatLocalDataSource {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val aiCurrentChatBySelectedStack: Flow<List<AiChatTextEntity>>
        get() = userSettingsPreferences.selectedStack.flatMapLatest { selectedStack ->
            aiChatHistoryDao.getAllByStack(selectedStack.orEmpty())
        }.flowOn(ioDispatcher)

    override suspend fun insertAIChatConversation(
        question: AiChatTextEntity,
        answer: AiChatTextEntity
    ) {
        withContext(ioDispatcher) {
            aiChatHistoryDao.insertAll(question, answer)
        }
    }

    override val selectedStack: Flow<String?>
        get() = userSettingsPreferences.selectedStack.flowOn(ioDispatcher)

    override suspend fun changeSelectedStack(stack: String) {
        withContext(ioDispatcher) {
            userSettingsPreferences.changeSelectedStack(stack)
        }
    }
}