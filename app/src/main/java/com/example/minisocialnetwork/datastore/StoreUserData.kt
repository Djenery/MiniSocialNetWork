package com.example.minisocialnetwork.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StoreUserData(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userData")
        val EMAIL_KEY = stringPreferencesKey("email")
        val PASSWORD_KEY = stringPreferencesKey("password")
    }

    suspend fun getEmail(): String {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY] ?: ""
        }.first()
    }

    suspend fun getPassword(): String {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY] ?: ""
        }.first()
    }


    suspend fun saveLoginToDataStore(email: String, password: String) {
        context.dataStore.edit {
            it[EMAIL_KEY] = email
            it[PASSWORD_KEY] = password
        }
    }


}