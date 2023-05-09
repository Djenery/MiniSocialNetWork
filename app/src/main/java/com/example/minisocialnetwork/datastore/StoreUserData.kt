package com.example.minisocialnetwork.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserData(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userData")

    companion object {
        val EMAIL_KEY = stringPreferencesKey("email")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val CHECKBOX_KEY = booleanPreferencesKey("checkBox")
    }

    fun getEmail() = context.dataStore.data.map {
        it[EMAIL_KEY] ?: ""
    }

    fun getPassword() = context.dataStore.data.map {
        it[PASSWORD_KEY] ?: ""
    }

    fun getCheckBoxState() = context.dataStore.data.map {
        it[CHECKBOX_KEY] ?: false
    }

    suspend fun saveData(email: String, password: String, checkBox: Boolean) {
        context.dataStore.edit {
            it[EMAIL_KEY] = email
            it[PASSWORD_KEY] = password
            it[CHECKBOX_KEY] = checkBox

        }
    }


}