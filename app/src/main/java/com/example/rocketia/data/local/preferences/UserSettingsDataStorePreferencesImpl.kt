package com.example.rocketia.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_SETTINGS_DATASTORE_NAME = "user_settings"
private const val SELECTED_STACK_KEY = "selected_stack"
private val SELECTED_STACK_PREFERENCES_KEY = stringPreferencesKey(SELECTED_STACK_KEY)

private const val FIRST_LAUCH_KEY = "first_lauch"
private val FIRST_LAUCH_PREFERENCES_KEY = booleanPreferencesKey(FIRST_LAUCH_KEY)


class UserSettingsDataStorePreferencesImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): UserSettingsPreferences {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS_DATASTORE_NAME)

    override val selectedStack: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[SELECTED_STACK_PREFERENCES_KEY].orEmpty()
        }

    override suspend fun changeSelectedStack(stack: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_STACK_PREFERENCES_KEY] = stack
        }
    }

    override val firstLaunch: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[FIRST_LAUCH_PREFERENCES_KEY] != false
        }

    override suspend fun changeFirstLaunch() {
        context.dataStore.edit { preferences ->
            val currentFirstLauch = preferences[FIRST_LAUCH_PREFERENCES_KEY] != false
            preferences[FIRST_LAUCH_PREFERENCES_KEY] = !currentFirstLauch
        }
    }
}