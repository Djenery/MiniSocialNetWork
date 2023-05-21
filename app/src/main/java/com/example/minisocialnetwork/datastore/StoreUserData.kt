package com.example.minisocialnetwork.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.minisocialnetwork.util.Constants.DATA_STORE_NAME
import com.example.minisocialnetwork.util.Constants.EMAIL
import com.example.minisocialnetwork.util.Constants.NO_CHAR
import com.example.minisocialnetwork.util.Constants.PASSWORD
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StoreUserData(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            DATA_STORE_NAME
        )
        val EMAIL_KEY = stringPreferencesKey(EMAIL)
        val PASSWORD_KEY = stringPreferencesKey(PASSWORD)
    }

    suspend fun getEmail(): String {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY] ?: NO_CHAR
        }.first()
    }

    suspend fun getPassword(): String {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL_KEY] ?: NO_CHAR
        }.first()
    }


    suspend fun saveLoginToDataStore(email: String, password: String) {
        context.dataStore.edit {
            it[EMAIL_KEY] = email
            it[PASSWORD_KEY] = password
        }
    }

}