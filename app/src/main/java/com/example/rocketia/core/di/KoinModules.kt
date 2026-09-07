package com.example.rocketia.core.di

import androidx.room.Room
import com.example.rocketia.data.datasource.AIChatLocalDataSource
import com.example.rocketia.data.datasource.AIChatLocalDataSourceImpl
import com.example.rocketia.data.datasource.AIChatRemoteDataSource
import com.example.rocketia.data.datasource.AIChatRemoteDataSourceImpl
import com.example.rocketia.data.local.database.AIChatHistoryDao
import com.example.rocketia.data.local.database.ROCKET_AI_DATABASE_NAME
import com.example.rocketia.data.local.database.RocketAIDatabase
import com.example.rocketia.data.local.preferences.UserSettingsDataStorePreferencesImpl
import com.example.rocketia.data.local.preferences.UserSettingsPreferences
import com.example.rocketia.data.remote.api.AIApiService
import com.example.rocketia.data.remote.api.AIGeminiAPIServiceImpl
import com.example.rocketia.data.repository.AIChatRepositoryImpl
import com.example.rocketia.domain.repository.AIChatRepository
import com.example.rocketia.domain.usecase.ChangeStackUseCase
import com.example.rocketia.domain.usecase.CheckHasSelectedStackUseCase
import com.example.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import com.example.rocketia.domain.usecase.GetSelectedStackUseCase
import com.example.rocketia.domain.usecase.SendUserQuestionUseCase
import com.example.rocketia.ui.viewmodel.WelcomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<CoroutineDispatcher>(named("IO")) { Dispatchers.IO }
    single<AIApiService> {
        AIGeminiAPIServiceImpl()
    }
    single<UserSettingsPreferences> {
        UserSettingsDataStorePreferencesImpl(context = androidApplication())
    }
    single<RocketAIDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            RocketAIDatabase::class.java,
            ROCKET_AI_DATABASE_NAME
        ).build()
    }
    single<AIChatHistoryDao> {
        get<RocketAIDatabase>().aiChatHistoryDao()
    }
    single<AIChatLocalDataSource> {
        AIChatLocalDataSourceImpl( get(named("IO")), get(), get())
    }

    single<AIChatRemoteDataSource> {
        AIChatRemoteDataSourceImpl(get(named("IO")), get())
    }

    single<AIChatRepository> { AIChatRepositoryImpl(get(), get()) }

}

val domainModule = module {
    factory {
        ChangeStackUseCase(get())
    }
    factory {
        CheckHasSelectedStackUseCase(get())
    }
    factory {
        GetAIChatBySelectedStackUseCase(get())
    }
    factory {
        GetSelectedStackUseCase(get())
    }
    factory {
        SendUserQuestionUseCase(get())
    }
}

val uiModule = module {
    viewModelOf(::WelcomeViewModel)
}